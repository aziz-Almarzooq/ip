package adrian;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Displays one chat message beside the sender's avatar.
 */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 55.0;
    private static final double MESSAGE_WIDTH = 280.0;

    private final Label message;
    private final ImageView avatar;

    /**
     * Creates a dialog box containing a message and avatar.
     *
     * @param text message to display.
     * @param image avatar belonging to the sender.
     */
    private DialogBox(String text, Image image) {
        message = new Label(text);
        message.setMaxWidth(MESSAGE_WIDTH);
        message.setWrapText(true);

        avatar = new ImageView(image);
        avatar.setFitWidth(AVATAR_SIZE);
        avatar.setFitHeight(AVATAR_SIZE);
        avatar.setPreserveRatio(true);

        setMaxWidth(Double.MAX_VALUE);
        setPadding(new Insets(5.0));
        setSpacing(10.0);
    }

    /**
     * Creates a right-aligned dialog box for a message from the user.
     *
     * @param text user message to display.
     * @param image Rocky avatar representing the user.
     * @return dialog box for the user message.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.message.getStyleClass().addAll("dialog-label", "user-dialog");
        dialogBox.setAlignment(Pos.TOP_RIGHT);
        dialogBox.getChildren().addAll(dialogBox.message, dialogBox.avatar);
        return dialogBox;
    }

    /**
     * Creates a left-aligned dialog box for a message from Adrian.
     *
     * @param text Adrian message to display.
     * @param image planet avatar representing Adrian.
     * @return dialog box for the Adrian message.
     */
    public static DialogBox getAdrianDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.message.getStyleClass().addAll("dialog-label", "adrian-dialog");
        dialogBox.setAlignment(Pos.TOP_LEFT);
        dialogBox.getChildren().addAll(dialogBox.avatar, dialogBox.message);
        return dialogBox;
    }
}
