package resources;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

public final class StylingLayout {
        // left menu coloring
        public static final String BACKGROUND_DARK_GREY = "#1F2020";
        public static final String LOGGED_IN_USER_HEADER_TEXT_FILL = "#696C6C";
        public static final String ITEMS_IN_LEFT_MENU_TEXT_FILL = "#CCCFCF";
        public static final String ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL = "#D28A0C";
        public static final String ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND = "#272828";

        public static void stylingLeftMenu(String sceneName, Label lblUserLoggedInHeader,
                                           AnchorPane anchorPaneLeftmenu, Button btnPatients, Button btnJournals,
                                           Button btnProgram, Button btnStart, Button btnExercises) {
                switch (sceneName) {
                        case "start":
                                break;
                        case "patients":
                                anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY);
                                lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLayout.LOGGED_IN_USER_HEADER_TEXT_FILL);

                                // styling left menu - buttons
                                btnPatients.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                                        + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                                btnJournals.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnProgram.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnStart.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnExercises.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnPatients.setAlignment(Pos.BASELINE_LEFT);
                                btnProgram.setAlignment(Pos.BASELINE_LEFT);
                                btnJournals.setAlignment(Pos.BASELINE_LEFT);
                                btnStart.setAlignment(Pos.BASELINE_LEFT);
                                btnExercises.setAlignment(Pos.BASELINE_LEFT);
                                btnPatients.setPadding(new Insets(0, 0, 0, 20));
                                btnProgram.setPadding(new Insets(0, 0, 0, 20));
                                btnJournals.setPadding(new Insets(0, 0, 0, 20));
                                btnStart.setPadding(new Insets(0, 0, 0, 20));
                                btnExercises.setPadding(new Insets(0, 0, 0, 20));
                                break;
                        case "journal":
                                // styling left menu
                                anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY);
                                lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLayout.LOGGED_IN_USER_HEADER_TEXT_FILL);

                                // styling left menu - buttons
                                btnPatients.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnJournals.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                                        + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                                btnProgram.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnStart.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnExercises.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnPatients.setAlignment(Pos.BASELINE_LEFT);
                                btnProgram.setAlignment(Pos.BASELINE_LEFT);
                                btnJournals.setAlignment(Pos.BASELINE_LEFT);
                                btnStart.setAlignment(Pos.BASELINE_LEFT);
                                btnExercises.setAlignment(Pos.BASELINE_LEFT);
                                btnPatients.setPadding(new Insets(0, 0, 0, 20));
                                btnProgram.setPadding(new Insets(0, 0, 0, 20));
                                btnJournals.setPadding(new Insets(0, 0, 0, 20));
                                btnStart.setPadding(new Insets(0, 0, 0, 20));
                                btnExercises.setPadding(new Insets(0, 0, 0, 20));
                                break;
                        case "program":
                                anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY);
                                lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLayout.LOGGED_IN_USER_HEADER_TEXT_FILL);

                                // styling left menu - buttons
                                btnPatients.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnJournals.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                                        + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                                btnStart.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnExercises.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnPatients.setAlignment(Pos.BASELINE_LEFT);
                                btnProgram.setAlignment(Pos.BASELINE_LEFT);
                                btnJournals.setAlignment(Pos.BASELINE_LEFT);
                                btnStart.setAlignment(Pos.BASELINE_LEFT);
                                btnExercises.setAlignment(Pos.BASELINE_LEFT);
                                btnPatients.setPadding(new Insets(0, 0, 0, 20));
                                btnProgram.setPadding(new Insets(0, 0, 0, 20));
                                btnJournals.setPadding(new Insets(0, 0, 0, 20));
                                btnStart.setPadding(new Insets(0, 0, 0, 20));
                                btnExercises.setPadding(new Insets(0, 0, 0, 20));
                                break;
                        case "exercises":
                                anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY);
                                lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLayout.LOGGED_IN_USER_HEADER_TEXT_FILL);

                                // styling left menu - buttons
                                btnPatients.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnJournals.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnProgram.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                                        + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnStart.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                                        "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                                btnExercises.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND +
                                        "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                                btnPatients.setAlignment(Pos.BASELINE_LEFT);
                                btnProgram.setAlignment(Pos.BASELINE_LEFT);
                                btnJournals.setAlignment(Pos.BASELINE_LEFT);
                                btnStart.setAlignment(Pos.BASELINE_LEFT);
                                btnExercises.setAlignment(Pos.BASELINE_LEFT);
                                btnPatients.setPadding(new Insets(0, 0, 0, 20));
                                btnProgram.setPadding(new Insets(0, 0, 0, 20));
                                btnJournals.setPadding(new Insets(0, 0, 0, 20));
                                btnStart.setPadding(new Insets(0, 0, 0, 20));
                                btnExercises.setPadding(new Insets(0, 0, 0, 20));
                                break;
                }
        }
}

