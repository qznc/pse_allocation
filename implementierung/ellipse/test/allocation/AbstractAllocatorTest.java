package allocation;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class AbstractAllocatorTest {

    static AbstractAllocator abstAllocator;

    @BeforeClass
    public static void init() {
        abstAllocator = new AbstractAllocator() {

            @Override
            public void cancel() {
                // empty
            }

            @Override
            public void calculate(Configuration configuration) {
                // empty
            }

            @Override
            public List<? extends Criterion> getAllCriteria() {
                // empty
                return null;
            }
        };
    }

    /**
     * test der überprüft ob der Serviceloader eine liste mit mehr als einem
     * element zurückgibt
     */
    @Test
    @Ignore
    public void testServiceLoader() {
        assertTrue(abstAllocator.getAllCriteria().size() > 0);
    }

}
