package sheep.ui.graphical.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.ui.UI;

import java.util.Map;

/**
 * The SheeP JavaFX application.
 * Construct a new SheeP Application with a sheet preloaded. The application has a
 * menu bar with each feature available.
 *
 * Parameters:
 *     view - A view of the primary sheet.
 *     updater - An updater for the primary sheet.
 *     features - A mapping of all the menu bar features.
 */
public class SheepApplication extends Application {

    /**
     * sheet view
     */
    private final SheetView view;
    /**
     * updater
     */
    private final SheetUpdate updater;
    /**
     * features
     */
    private final Map<String, Map<String, UI.Feature>> features;

    /**
     * this classes gridpane
     */
    private final GridPane gridPane = new GridPane();

    /**
     * Construct a new SheeP Application with a sheet preloaded.
     * The application has a menu bar with each feature available.
     *
     * @param view A view of the primary sheet.
     * @param updater An updater for the primary sheet.
     * @param features A mapping of all the menu bar features.
     */
    public SheepApplication(SheetView view, SheetUpdate updater, Map<String,
            Map<String, UI.Feature>> features) {
        this.view = view;
        this.updater = updater;
        this.features = features;
    }

    /**
     * Start the SheeP Application.
     * Creates a new window to display and modify the sheet.
     * The scene has a menu bar with all the features.
     *
     * [3] Helped with gridpane setup
     * [4] Helped with typesetting for letters in top row
     *
     * @param stage the primary stage.
     * @throws Exception if the application fails to run.
     */
    @Override
    public void start(Stage stage) throws Exception {

        // menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefSize(1000, 20);
        menuBar.setStyle("-fx-padding: 4px; -fx-border-color: grey;");

        // file menu setup
        Menu fileMenu = new Menu("File");
        fileMenu.setStyle("-fx-font-weight: bold;");
        MenuItem saveAsItem = new MenuItem("Save As...");
        MenuItem openAsItem = new MenuItem("Open As...");

        // Add menu items to file menu and add file menu to menu bar
        fileMenu.getItems().addAll(saveAsItem, openAsItem);
        menuBar.getMenus().add(fileMenu);

        //create gridpane size constraints
        //TODO fix max width of cells, might have to do with my page not updating
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setMinWidth(50);
        columnConstraints.setPrefWidth(100);
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setMaxWidth(Double.MAX_VALUE);

        RowConstraints rowConstraints = new RowConstraints(20);

        //setup gridPane column and row size
        int columns = view.getColumns() + 1;
        int rows = view.getRows() + 1;

        // Set column size constraints on rows and cols
        for (int i = 0; i < columns; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rows; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // setup top element which displays values from the cell you have clicked on
        Label headerLabel = new Label();
        headerLabel.setPrefSize(100 * columns, 20);
        headerLabel.setStyle("-fx-padding: 4px; -fx-background-color: LightGrey; "
                + "-fx-border-color: grey; -fx-border-width: 1px;");
        headerLabel.maxWidth(Long.MAX_VALUE);
        headerLabel.setText("");

        // TODO max width of boxes
        // add top row of letters and left column of index numbers to gridpane
        Label label = null;
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {

                if (row == 0 && column != 0) {
                    label = createLabel(String.valueOf(((char) (column + 64))));
                    gridPane.add(label, column, row);

                    //left hand column
                } else if (column == 0 && row != 0) {
                    label = createLabel(String.valueOf(row - 1));
                    gridPane.add(label, column, row);
                }
            }
        }
        
        // Add textbox elements to the gridpane
        for (int column = 1; column < columns; column++) {
            for (int row = 1; row < rows; row++) {

                //cells with values
                ViewElement element = view.valueAt(row - 1, column - 1);
                TextField textField = createTextField(element.getContent());

                int finalRow = row - 1;
                int finalColumn = column - 1;

                final boolean[] doubleClick = new boolean[1];

                textField.setOnMouseClicked(e -> {
                    if (e.getClickCount() == 2) {

                        //boolean so that spreadsheet doesn't refresh values on a single click
                        doubleClick[0] = true;

                        //update header label and text box with formula on double click
                        headerLabel.setText(view.formulaAt(finalRow, finalColumn).getContent());
                        textField.setText(view.formulaAt(finalRow, finalColumn).getContent());
                        textField.setStyle("-fx-border-color: CornflowerBlue; -fx-border-width: 2");

                    } else if (e.getClickCount() == 1) {
                        //update only header with formula on one click
                        headerLabel.setText(view.formulaAt(finalRow, finalColumn).getContent());
                    }
                });

                // if enter is pressed or cell loses focus after being double-clicked on
                // refresh the cell and the spreadsheet with updated values
                textField.setOnAction(e -> {
                    if (doubleClick[0]) {
                        updateCellAndRefreshGrid(textField, finalRow, finalColumn);
                        doubleClick[0] = false;
                        textField.getParent().requestFocus();
                    }
                });
                textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused && doubleClick[0]) {
                        updateCellAndRefreshGrid(textField, finalRow, finalColumn);
                        doubleClick[0] = false;
                    }
                });
                gridPane.add(textField, column, row);
            }
        }

        // put all elements inside vbox, then put vbox inside borderpane
        // [8] Help with vbox setup
        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, headerLabel, gridPane);
        root.setLeft(vbox);

        // side up window and set title
        Scene scene = new Scene(root, 1150, 550);
        stage.setScene(scene);
        stage.setTitle("SheeP (Sheet Processing)");
        stage.show();
    }

    /**
     * Updates the cell which has been modified and refreshes grid
     *
     * [7] Help with figuring out how to loop through my gridbox textboxes
     *
     * @param textField textfield being edited
     * @param row position of box
     * @param column position of box
     */
    private void updateCellAndRefreshGrid(TextField textField, int row, int column) {
        // update the cell and check if a valid input was entered
        UpdateResponse update = updater.update(row, column, textField.getText());
        textField.setStyle("-fx-border-color: grey; -fx-border-width: 1px;");

        // if the input was valid update the rest of the spreadsheet textbox cells
        if (update.isSuccess()) {
            for (Node node : gridPane.getChildren()) {
                if (node instanceof TextField) {
                    Integer colIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);
                    if (colIndex != null && rowIndex != null && colIndex > 0 && rowIndex > 0) {
                        ViewElement element = view.valueAt(rowIndex - 1, colIndex - 1);
                        ((TextField) node).setText(element.getContent());
                    }
                }
            }
        } else {
            System.out.println("Update failed: " + update.getMessage());
        }
    }

    /**
     * method to create text boxes for the grid
     *<p>
     * [5] Helped with styling the elements with css
     * [6] Helped with sizing and setting up text fields
     *
     * @param text text to put into labels
     * @return 100x20 text field with specified text inside of it
     */
    private TextField createTextField(String text) {
        TextField textField = new TextField();
        textField.setPrefSize(100, 20);

        //TODO max width of boxes
        textField.maxWidth(Long.MAX_VALUE);

        textField.setStyle("-fx-border-color: grey; -fx-border-width: 1px;");
        textField.setText(text);

        return textField;
    }

    /**
     * creates labels for the top row and left hand column
     * <p>
     * @param text to put into labels
     * @return 100x20 label with specified text inside of it
     */
    private Label createLabel(String text) {
        Label label = new Label();
        label.setPrefSize(100, 20);
        label.setAlignment(Pos.CENTER);

        label.setText(text);
        label.setStyle("-fx-padding: 4px; -fx-background-color: Gainsboro; -fx-border-color: grey; -fx-border-width: 1px;");

        return label;
    }

    /**
     * Create a new window, with a new sheet attached.
     *
     * @param view a view of the new sheet.
     * @param updater an updater for the new sheet.
     */
    public void createWindow(SheetView view, SheetUpdate updater) {

        Stage newStage = new Stage();
        SheepApplication newApp = new SheepApplication(view, updater, features);
        try {
            newApp.start(newStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}