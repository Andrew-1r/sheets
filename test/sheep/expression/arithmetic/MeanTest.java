package sheep.expression.arithmetic;

import org.junit.Before;
import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.basic.Constant;

import static org.junit.Assert.*;

public class MeanTest {
    private Mean mean;

    @Before
    public void setUp() {
        mean = new Mean(new Expression[]{new Constant(1)});
    }

    @Test
    public void oddTest() {
        long expectedOutcome = 6;

        assertEquals(expectedOutcome, mean.perform(new long[]{11, 2, 5}));
    }

    @Test
    public void evenTest() {
        long expectedOutcome = (long) 5.5;

        assertEquals(expectedOutcome, mean.perform(new long[]{11, 2, 5, 4}));
    }

    @Test
    public void singleNumberTest() {
        long expectedOutcome = 1;

        assertEquals(expectedOutcome, mean.perform(new long[]{1}));
    }

    @Test
    public void singleNumberTest2() {
        long expectedOutcome = 5;

        assertEquals(expectedOutcome, mean.perform(new long[]{5}));
    }

    @Test
    public void zeroTest() {
        long expectedOutcome = 0;

        assertEquals(expectedOutcome, mean.perform(new long[]{0}));
    }

}
