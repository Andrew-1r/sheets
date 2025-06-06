package sheep.fun;

import sheep.core.SheetUpdate;
import sheep.core.UpdateResponse;

/**
 * fibonacci constants
 * @provided
 */
public class FibonacciConstants implements Fun {
    /**
     * rows
     */
    private final int rows;

    /**
     * fibonacci constants
     * @param rows for the fibonacci constants to go in
     */
    public FibonacciConstants(int rows) {
        this.rows = rows;
    }

    private long fib(int n) {
        if (n <= 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    @Override
    public void draw(SheetUpdate sheet) throws FunException {
        for (int row = 0; row < rows; row++) {
            UpdateResponse response = sheet.update(row, 0, "" + fib(row));
            if (!response.isSuccess()) {
                throw new FunException(response.getMessage());
            }
        }
    }
}
