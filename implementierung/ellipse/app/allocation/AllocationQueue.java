// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.security.auth.login.Configuration;

import exception.AllocationException;

/************************************************************/
/**
 * Die AllocationQueue dient dazu, die Berechnung von Einteilungen als
 * FIFO-Warteschlange zu realisieren. Wenn eine berechnung fertig ist wird die
 * nächste Berechnung angestoßen. Die Queue ist als Singelton implementiert.
 */
public class AllocationQueue {

    /**
     * Calculator ist der Thread der die berechnung anstößt er verwendet das
     * Runnable runnable
     */
    private Thread                         calculator;
    private Runnable                       runnable;

    private ThreadState                    threadState;
    /**
     * Die intern verwendete queue.
     */
    private List<allocation.Configuration> configurationQueue;
    /**
     * Der Singelton der Allocation queue.
     */
    private static AllocationQueue         instance;
    /**
     * Der Einteilungsberechner, der zur Berechnung verwendet wird.
     */
    private AbstractAllocator              allocator;
    /**
     * Die Konfiguration, die aktuell zur Berechnung verwendet wird.
     */
    private allocation.Configuration       currentlyCalculatedConfiguration;

    /**
     * Privater Konstruktor, der zur Instanziierung des Singletons verwendet
     * wird.
     */
    private AllocationQueue() {
        this.configurationQueue = new CopyOnWriteArrayList<>();
        setAllocator(new GurobiAllocator());

        runnable = new Runnable() {

            public void run() {
                try{
                    allocator.calculate(currentlyCalculatedConfiguration);
              
                currentlyCalculatedConfiguration = null;
                threadState = ThreadState.IDLE;
                calculate();
                }catch(AllocationException e) {
                                        
                }
            }
        };
        threadState = ThreadState.IDLE;
    }

    /**
     * Gibt die eine existierende Instanz der AllocationQueue (Singleton)
     * zurück. *
     * 
     * @return Die Instanz der AllocationQueue.
     */
    public static AllocationQueue getInstance() {
        if (instance == null) {
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
    public void addToQueue(allocation.Configuration configuration) throws AllocationException {
        configurationQueue.add(configuration);
        calculate();

    }

    /**
     * Nimmt eine Konfiguration aus der Berechnungsqueue heraus. Falls diese
     * Konfiguration bereits berechnet wird, wird die Berechnung abgebrochen.
     * 
     * @param configuration
     *            Die Konfiguration, die entfernt werden soll.
     */
    public void cancelAllocation(allocation.Configuration configuration) {
        synchronized (this) {
            if (configuration == currentlyCalculatedConfiguration) {
                calculator.interrupt();
            } else {
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
        return configurationQueue;
    }

    private void setAllocator(AbstractAllocator allocator) {
        this.allocator = allocator;
    }

    private void calculate() throws AllocationException {
        synchronized (this) {
            if (!configurationQueue.isEmpty()) {
                if (threadState == ThreadState.IDLE) {
                    calculator = new Thread(runnable);
                    currentlyCalculatedConfiguration = configurationQueue.get(configurationQueue.size() - 1);
                    configurationQueue.remove(configurationQueue.size() - 1);
                    threadState = ThreadState.RUNNING;
                    calculator.start();
                }
            }
        }

    }
}
