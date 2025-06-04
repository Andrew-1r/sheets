package sheep.fun;

import sheep.core.SheetUpdate;

/**
 * fun class
 * @provided
 */
public interface Fun {
    /**
     * update sheet
     * @param sheet to update
     * @throws FunException to throw
     */
    void draw(SheetUpdate sheet) throws FunException;
}
