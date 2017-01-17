// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

import java.security.cert.CRLSelector;

/************************************************************/
/**
 * Ein Kriterium ist dazu da den Optimierungsterm des ILP-Modells zu erweitern und gegebenenfalls zusätzliche Constraints hinzuzufügen.
 */
public interface GurobiCriterion extends Criterion{

    /**
     * Bildet den Optimierungsterm und fügt ihn dem GurobiAllocator hinzu.
     * 
     * @param weight
     *            Der vom Admin eingestellte Parameter dieses Kriteriums.
     * @param allocator
     *            Die Allocator-Instanz welche dieses Kriterium verwenden soll.
     */
    public void useCriteria(int weight, GurobiAllocator allocator);

    /**
     * Getter für den Namen des Kriteriums.
     * 
     * @return Der Name des Kriteriums.
     */
    public String getName();
}
