package sheep.core;

/**
 * Display information for a cell.
 * Stores the content, background colour name, and foreground colour name.
 * <p>
 * A colour is defined by the {@link java.awt.Color} class,
 * i.e. "black" is {@link java.awt.Color#black}.
 * @provided
 */
public class ViewElement {
    /**
     * content - value after cell is calculated
     */
    private final String content;
    /**
     * background colour
     */
    private final String background;
    /**
     * foreground colour
     */
    private final String foreground;

    /**
     * Construct a new view element.
     *
     * @param content    A string to render as the value for a cell.
     * @param background A name of a colour to render as the cell background.
     * @param foreground A name of a colour to render as the cell foreground (text colour).
     * @requires content != null &amp;&amp; background != null &amp;&amp; foreground != null
     */
    public ViewElement(String content, String background, String foreground) {
        this.content = content;
        this.background = background;
        this.foreground = foreground;
    }

    /**
     * The content to render.
     *
     * @return The content to render.
     */
    public String getContent() {
        return content;
    }

    /**
     * The name of a colour to render as the cell background.
     *
     * @return The name of a colour to render as the cell background.
     */
    public String getBackground() {
        return background;
    }

    /**
     * The name of a colour to render as the cell foreground.
     *
     * @return The name of a colour to render as the cell foreground.
     */
    public String getForeground() {
        return foreground;
    }
}
