// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/************************************************************/
/**
 * Die AllocationQueue dient dazu, die Berechnung von Einteilungen als
 * FIFO-Warteschlange zu realisieren. Wenn eine Berechnung fertig ist wird die
 * nächste Berechnung angestoßen. Die Queue ist als Singelton implementiert.
 */
public class AllocationQueue {

    private static final int                QUEUE_SIZE = 10;
    /**
     * Der Worker-Thread, der die Berechnungen durchführt
     */
    private Thread                          worker;
    /**
     * Die intern verwendete queue.
     */
    private Queue<allocation.Configuration> configurationQueue;
    /**
     * Der Singelton der Allocation queue.
     */
    private static AllocationQueue          instance;
    /**
     * Der Einteilungsberechner, der zur Berechnung verwendet wird.
     */
    private AbstractAllocator               allocator;
    /**
     * Die Konfiguration, die aktuell zur Berechnung verwendet wird.
     */
    private allocation.Configuration        currentlyCalculatedConfiguration;

    /**
     * Privater Konstruktor, der zur Instanziierung des Singletons verwendet
     * wird.
     */
    private AllocationQueue() {
        this.configurationQueue = new ArrayDeque<>(QUEUE_SIZE);
        this.allocator = new GurobiAllocator();
        worker = new QueueWorker();
        worker.start();
        // hier wird der thread gestartet, der überpüft ob die liste
        // leer ist und sie gegebenenfalls abarbeitet
    }

    /**
     * Gibt die eine existierende Instanz der AllocationQueue (Singleton)
     * zurück. *
     * 
     * @return Die Instanz der AllocationQueue.
     */
    public static AllocationQueue getInstance() {
        if (instance == null) { // ganz normaler Singelton
            instance = new AllocationQueue();
        }
        return instance;
    }

    /**
     * Fügt der Berechnungsqueue eine Konfiguration hinzu, die zur Berechnung
     * verwendet werden soll. Es können maximal 10 Berechnungen in die Queue
     * aufgenommen werden.
     * 
     * @param configuration
     *            Die Konfiguration, die zur Berechnungsqueue hinzugefügt wird.
     */
    public void addToQueue(allocation.Configuration configuration) {
        synchronized (this.configurationQueue) {
            configurationQueue.add(configuration);
            this.configurationQueue.notifyAll(); // der calculator thread wird
            // geweckt
        }

    }

    /**
     * Nimmt eine Konfiguration aus der Berechnungsqueue heraus. Falls diese
     * Konfiguration bereits berechnet wird, wird die Berechnung abgebrochen.
     * 
     * @param configuration
     *            Der Name der Konfiguration, die entfernt werden soll.
     */
    public void cancelAllocation(String name) {
        synchronized (this.configurationQueue) {
            if (null != currentlyCalculatedConfiguration && name
                    .equals(currentlyCalculatedConfiguration.getName())) {
                currentlyCalculatedConfiguration = null;
                allocator.cancel();
            } else {
                Configuration configuration = configurationQueue.stream()
                        .filter(conf -> conf.getName().equals(name)).findFirst()
                        .orElse(null);
                configurationQueue.remove(configuration);
            }
        }

    }

    /**
     * Gibt die Queue der Berechnungen zurück, inklusive der Konfiguration die
     * aktuell berechnet wird.
     * 
     * @return Liste der Konfigurationen als FIFO-Queue angeordnet.
     */
    public List<allocation.Configuration> getQueue() {
        // hier könnte QUEUE_SIZE verwendet werden da die queue jedoch meist
        // nicht voll sein wird hier nur 4
        ArrayList<allocation.Configuration> list = new ArrayList<>(4);
        synchronized (this.configurationQueue) {
            if (currentlyCalculatedConfiguration != null) {
                list.add(currentlyCalculatedConfiguration);
            }
            for (allocation.Configuration i : configurationQueue) {
                list.add(i);
            }
        }
        return list;
    }

    public void setAllocator(AbstractAllocator allocator) {
        this.allocator = allocator;
    }

    public AbstractAllocator getAllocator() {
        return allocator;
    }

    /**
     * Entfernt alle Elemente aus der Queue.
     */
    public void clear() {
        synchronized (this.configurationQueue) {
            configurationQueue.clear();
            if (currentlyCalculatedConfiguration != null) {
                cancelAllocation(currentlyCalculatedConfiguration.getName());
            }
            currentlyCalculatedConfiguration = null;
        }
    }

    private class QueueWorker extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (AllocationQueue.this.configurationQueue) {
                    // syncronized da ansonsten probleme mit dem cancel() kommen
                    // könnten
                    while (configurationQueue.isEmpty()) {
                        try {
                            AllocationQueue.this.configurationQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            assert false;
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    currentlyCalculatedConfiguration = configurationQueue
                            .poll();
                    allocator.init(currentlyCalculatedConfiguration);
                }
                allocator.calculate();
                currentlyCalculatedConfiguration = null;
            }
        }
    }

}
