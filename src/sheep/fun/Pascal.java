package sheep.fun;

import sheep.core.SheetUpdate;
import sheep.core.UpdateResponse;

/**
 * a fun pascal class
 * @provided
 */
public class Pascal implements Fun {
    /**
     * start of row
     */
    private final int startRow;
    /**
     * start of column
     */
    private final int startColumn;

    /**
     * pascal method
     * @param startRow start of row
     * @param startColumn start of column
     */
    public Pascal(int startRow, int startColumn) {
        this.startRow = startRow;
        this.startColumn = startColumn;
    }

    /**
     * draw method
     * @param sheet to update
     * @throws FunException is thrown
     */
    @Override
    public void draw(SheetUpdate sheet) throws FunException {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 6; x++) {
                int row = y + startRow;
                int column = x + startColumn;
                UpdateResponse response;
                if (x < 1) {
                    response = sheet.update(row, column, "1");
                } else if (x > y) {
                    response = sheet.update(row, column, "1");
                } else {
                    String leftColumn = Character.toString(column - 1 + 65);
                    String rightColumn = Character.toString(column + 65);
                    response = sheet.update(row, column,
                            "" + leftColumn + (row - 1) + " + "
                                    + rightColumn + (row - 1));
                }
                if (!response.isSuccess()) {
                    throw new FunException(response.getMessage());
                }
            }
        }
    }
}
