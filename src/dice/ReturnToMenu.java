package dice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Paweł Taborowski on 23.01.17.
 *
 * Base class created to avoid code repeating among sub-menus.
 */
public class ReturnToMenu {
    protected void toMenu(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            Errors errors = new Errors(node);
            errors.exitAfterError();
        }
        stage.setScene(new Scene(root));
    }
}
