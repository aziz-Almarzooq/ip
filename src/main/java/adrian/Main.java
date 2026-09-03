package adrian;

import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Displays the JavaFX user interface for Adrian.
 */
public class Main extends Application {
    private static final double WINDOW_WIDTH = 400.0;
    private static final double WINDOW_HEIGHT = 600.0;

    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox dialogContainer = new VBox();
    private final TextField userInput = new TextField();
    private final Button sendButton = new Button("Send");
    private final Adrian adrian = new Adrian();
    private final Image userImage = loadImage("/images/rocky.jpeg");
    private final Image adrianImage = loadImage("/images/adrian.jpeg");

    /**
     * Creates the Adrian JavaFX application.
     */
    public Main() {
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage primary window supplied by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        dialogContainer.setPadding(new Insets(10.0));
        dialogContainer.setSpacing(10.0);
        dialogContainer.getStyleClass().add("dialog-container");
        dialogContainer.getChildren().add(
                DialogBox.getAdrianDialog(adrian.getWelcomeMessage(), adrianImage));

        scrollPane.setContent(dialogContainer);
        scrollPane.getStyleClass().add("dialog-scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        userInput.setPromptText("Enter a command...");
        sendButton.setPrefWidth(60.0);
        userInput.setOnAction(event -> handleUserInput());
        sendButton.setOnAction(event -> handleUserInput());

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainLayout.getStyleClass().add("main-layout");
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 5.0);
        AnchorPane.setRightAnchor(scrollPane, 5.0);
        AnchorPane.setBottomAnchor(scrollPane, 45.0);
        AnchorPane.setLeftAnchor(scrollPane, 5.0);

        AnchorPane.setRightAnchor(userInput, 70.0);
        AnchorPane.setBottomAnchor(userInput, 5.0);
        AnchorPane.setLeftAnchor(userInput, 5.0);

        AnchorPane.setRightAnchor(sendButton, 5.0);
        AnchorPane.setBottomAnchor(sendButton, 5.0);

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add(getResource("/styles/dark-theme.css").toExternalForm());
        stage.setTitle("Adrian");
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Processes the user's input and displays the command and Adrian's response.
     */
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        DialogBox userMessage = DialogBox.getUserDialog(input, userImage);
        DialogBox adrianMessage = DialogBox.getAdrianDialog(adrian.getResponse(input), adrianImage);

        dialogContainer.getChildren().addAll(userMessage, adrianMessage);
        userInput.clear();

        if (adrian.isExitRequested()) {
            Platform.exit();
        }
    }

    /**
     * Loads an image bundled with the application.
     *
     * @param resourcePath classpath location of the image.
     * @return image loaded from the resource.
     * @throws IllegalStateException if the resource does not exist.
     */
    private static Image loadImage(String resourcePath) {
        return new Image(getResource(resourcePath).toExternalForm());
    }

    /**
     * Locates a resource bundled with the application.
     *
     * @param resourcePath classpath location of the resource.
     * @return URL identifying the resource.
     * @throws IllegalStateException if the resource does not exist.
     */
    private static URL getResource(String resourcePath) {
        URL resource = Main.class.getResource(resourcePath);

        if (resource == null) {
            throw new IllegalStateException("Missing application resource: " + resourcePath);
        }

        return resource;
    }
}
