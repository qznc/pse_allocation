// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package allocation;

/************************************************************/
/**
 * Das Kriterium sorgt dafür, dass Studierende höheren Semesters bevorzugt
 * werden.
 */
public class CriterionPreferHigherSemester implements Criterion {
    private String name;

    /**
     * Standard-Konstruktor, der den Namen eindeutig setzt
     */
    public CriterionPreferHigherSemester() {
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void useCriteria(int weight, GurobiAllocator allocator) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
}