package dice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Paweł Taborowski on 21.01.17.
 *
 * Controller for scene of Main Menu.
 */

public class Menu {

    @FXML
    private TextInputControl player1Name;

    @FXML
    private TextInputControl player2Name;

    @FXML
    private Button loadButton;

    private Stage stage;

    private boolean savegameAvailable = false;

    public void initialize(){
        player1Name.setText(Settings.player1);
        player2Name.setText(Settings.player2);

        if(new File("Savegame.sav").isFile()){
            loadButton.setOpacity(1.0);
            loadButton.setCursor(Cursor.HAND);
            savegameAvailable = true;
        }
    }

    @FXML
    private void pokerALaWitcher(){
        launchGame(Genre.witcher);
    }

    @FXML
    private void pokerModified(){
        launchGame(Genre.poker);
    }

    @FXML
    private void five(){
        launchGame(Genre.five);
    }

    private void launchGame(Genre genre) {
        stage = (Stage) player1Name.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dice.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            Errors errors = new Errors(player1Name);
            errors.exitAfterError();
        }
        Controller controller = fxmlLoader.getController();
        controller.startGame(genre);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void showManual(){
        stage = (Stage) player1Name.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("manual.fxml"));
        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Errors errors = new Errors(player1Name);
            errors.exitAfterError();
        }
    }

    @FXML
    private void applyName(){
        Settings.player1 = player1Name.getText();
        Settings.player2 = player2Name.getText();
    }

    @FXML
    private void exit(){
        stage = (Stage) player1Name.getScene().getWindow();
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("Settings.sav"));
            (new Settings()).writeExternal(file);
            file.close();
        } catch (IOException e) {
            Errors errors = new Errors(player1Name);
            errors.writeError();
        }
        stage.close();
    }

    @FXML
    private void loadGame(){
        if (!savegameAvailable) return;
        Game loaded;
        try (ObjectInputStream file = new ObjectInputStream(new FileInputStream("Savegame.sav"))) {
            loaded = (Game) file.readObject();
        } catch (IOException|ClassNotFoundException e) {
            Errors errors = new Errors(player1Name);
            errors.loadError();
            return;
        }
        stage = (Stage) player1Name.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dice.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            Errors errors = new Errors(player1Name);
            errors.exitAfterError();
        }
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        controller.loadGame(loaded);
        stage.setScene(scene);
    }
}
