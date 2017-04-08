package dice;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Created by Paweł Taborowski on 21.01.17.
 *
 * Methods for showing to player alerts about errors.
 */
public class Errors {

    private Stage stage;

    Errors(Node node){
        stage = (Stage) node.getScene().getWindow();
    }

    public void exitAfterError(){
        Alert resourceNotAvailable = new Alert(Alert.AlertType.ERROR, "Critical error while accessing game files. The game will now shut.", ButtonType.CLOSE);
        resourceNotAvailable.showAndWait();
        stage.close();
    }

    public void writeError(){
        Alert writeToFileError = new Alert(Alert.AlertType.ERROR, "Failed to save data to the disk.", ButtonType.CLOSE);
        writeToFileError.showAndWait();
    }

    public void invalidGame() {
        Alert invalidGenre = new Alert(Alert.AlertType.ERROR, "Invalid game mode. That shouldn't have happend.", ButtonType.CLOSE);
        invalidGenre.showAndWait();
        stage.close();
    }

    public void loadError() {
        Alert loadGameError = new Alert(Alert.AlertType.ERROR, "Error while loading savegame. Sorry.", ButtonType.CLOSE);
        loadGameError.showAndWait();
    }
}
