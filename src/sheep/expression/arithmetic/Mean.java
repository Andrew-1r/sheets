package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A mean operation. Mean operations must have the operator name "MEAN".
 */
public class Mean extends Function {

    /**
     * Construct a new mean expression.
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires aruments length > 0
     */
    protected Mean(Expression[] arguments) {
        super("MEAN", arguments);
    }

    /**
     * Perform a mean operation over the list of arguments.
     * <p>
     *  Function mean = new Mean(new Expression[]{new Constant(11), new Constant(2),
     *  new Constant(5)});
     *  mean.perform(new long[]{11, 2, 5}); // 6
     * <p>
     * Specified by:
     *     perform in class Operation
     * @param arguments - A list of numbers to perform the operation upon.
     * @return the mean of the arguments.
     */
    protected long perform(long[] arguments) {
        long sum = 0;
        int length = arguments.length;

        for (long i : arguments) {
            sum += i;
        }

        return sum / length;
    }

}
