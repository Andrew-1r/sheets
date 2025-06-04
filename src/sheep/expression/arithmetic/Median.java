package sheep.expression.arithmetic;

import sheep.expression.Expression;

import java.util.Arrays;

/**
 * A median operation. Median operations must have the operator name "MEDIAN".
 */
public class Median extends Function {

    /**
     * construct a median
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments length > 0
     */
    protected Median(Expression[] arguments) {
        super("MEDIAN", arguments);
    }

    /**
     * protected long perform(long[] arguments)
     * Perform a median operation over the list of arguments.
     * <p>
     * [1] Help with how to sort an array.
     * [2] Integer division example.
     * <p>
     *  Function median = new Median(new Expression[]{new Constant(11), new Constant(2),
     *  new Constant(5)});
     *  median.perform(new long[]{11, 2, 5}); // 5
     * <p>
     * Specified by:
     *     perform in class Operation
     * @param arguments - A list of numbers to perform the operation upon.
     * @return the median of the arguments.
     */
    protected long perform(long[] arguments) {

        int length = arguments.length;

        //sort list
        Arrays.sort(arguments);

        if (length % 2 == 0) {
            //return average of 2 middle numbers
            return (arguments[(length / 2) - 1] + arguments[length / 2]) / 2;
        } else {
            length /= 2;
            return arguments[length];
        }
    }
}
