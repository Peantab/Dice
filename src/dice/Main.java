package dice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            ObjectInputStream in1 = new ObjectInputStream (new FileInputStream("Settings.sav"));
            (new Settings()).readExternal(in1);
            in1.close();
        } catch (IOException e) {
            System.out.println("No saved data present.");
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid saved data present.");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Dice by Paweł Taborowski");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
