package helpers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import resources.StylingLayout;

public class JavaFxHelper {
    public void resetGridPaneLabelsTextFill(GridPane gridPane){
        int gridPaneHeight = gridPane.getChildren().size();

        for (int row = 0; row < gridPaneHeight; row++){
            // Get Node from gridPane
            Node n = gridPane.getChildren().get(row);
            // Cast to Label
            Label l = (Label) n;
            // Set default style
            l.setStyle("-fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        }
    }
}
