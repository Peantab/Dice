package dice;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweł Taborowski
 *
 * Controller for the main screen of a game – game board.
 */

public class Controller extends ReturnToMenu {

    @FXML
    private Text status;

    static Text gameStatus;

    @FXML
    private ImageView pl1d1;
    @FXML
    private ImageView pl1d2;
    @FXML
    private ImageView pl1d3;
    @FXML
    private ImageView pl1d4;
    @FXML
    private ImageView pl1d5;
    @FXML
    public ImageView pl2d1;
    @FXML
    private ImageView pl2d2;
    @FXML
    private ImageView pl2d3;
    @FXML
    private ImageView pl2d4;
    @FXML
    private ImageView pl2d5;

    static ImageView plDice[];

    @FXML
    public ImageView pl1s1;
    @FXML
    private ImageView pl1s2;
    @FXML
    private ImageView pl1s3;
    @FXML
    private ImageView pl1s4;
    @FXML
    private ImageView pl1s5;
    @FXML
    private ImageView pl2s1;
    @FXML
    private ImageView pl2s2;
    @FXML
    private ImageView pl2s3;
    @FXML
    private ImageView pl2s4;
    @FXML
    private ImageView pl2s5;

    static ImageView selections[];

    //Arrows indicating player's turn
    @FXML
    private ImageView arrow1;
    @FXML
    private ImageView arrow2;

    static ImageView arrows[];

    //Results
    @FXML
    private Pane resultBar;
    @FXML
    private Text player1Name;
    @FXML
    private Text player2Name;
    @FXML
    private Text player1Score;
    @FXML
    private Text player2Score;
    @FXML
    private Text player1Description;
    @FXML
    private Text player2Description;
    @FXML
    private Button hideResults;

    static Map<String, Node> resultWindow;

    static FadeTransition resultsFadeIn = new FadeTransition(Duration.millis(1500)); //Fade-in animation
    static FadeTransition hideFadeIn = new FadeTransition(Duration.millis(1500)); //Fade-in animation

    @FXML
    private Button temporaryHide;

    Game game;


    public void initialize(){
        //Game game = new Game();
        plDice = new ImageView[]{pl1d1, pl1d2, pl1d3, pl1d4, pl1d5, pl2d1, pl2d2, pl2d3, pl2d4, pl2d5};
        selections = new ImageView[]{pl1s1, pl1s2, pl1s3, pl1s4, pl1s5, pl2s1, pl2s2, pl2s3, pl2s4, pl2s5};
        arrows = new ImageView[]{arrow1, arrow2};
        gameStatus = status;

        resultWindow = new HashMap<>();
        resultWindow.put("resultBar",resultBar);
        resultWindow.put("player1Name",player1Name);
        resultWindow.put("player2Name",player2Name);
        resultWindow.put("player1Score",player1Score);
        resultWindow.put("player2Score",player2Score);
        resultWindow.put("player1Description",player1Description);
        resultWindow.put("player2Description",player2Description);
        resultWindow.put("hideResults",hideResults);
        resultWindow.put("temporaryHide",temporaryHide);

        resultsFadeIn.setNode(temporaryHide);
        resultsFadeIn.setFromValue(0.0);
        resultsFadeIn.setToValue(0.98);
        resultsFadeIn.setCycleCount(1);
        resultsFadeIn.setAutoReverse(false);

        hideFadeIn.setNode(resultBar);
        hideFadeIn.setFromValue(0.0);
        hideFadeIn.setToValue(0.98);
        hideFadeIn.setCycleCount(1);
        hideFadeIn.setAutoReverse(false);

        resultBar.setVisible(false);
        resultBar.setDisable(true);
        temporaryHide.setVisible(false);

        //Should get overwritten by the game content ASAP. Else: game starting has failed.
        gameStatus.setText("Something went horribly wrong... Please, restart the game.");
    }

    void startGame(Genre genre){
        game = new Game(genre);
    }

    void loadGame(Game game){
        this.game = game;
        this.game.revertFromSave();
    }

    //10 identical functions for clicking more-or-less on die
    @FXML
    private void d11(){
        game.diceClicked(0);
    }

    @FXML
    private void d12(){
        game.diceClicked(1);
    }

    @FXML
    private void d13(){
        game.diceClicked(2);
    }

    @FXML
    private void d14(){
        game.diceClicked(3);
    }

    @FXML
    private void d15(){
        game.diceClicked(4);
    }

    @FXML
    private void d21(){
        game.diceClicked(5);
    }

    @FXML
    private void d22(){
        game.diceClicked(6);
    }

    @FXML
    private void d23(){
        game.diceClicked(7);
    }

    @FXML
    private void d24(){
        game.diceClicked(8);
    }

    @FXML
    private void d25(){
        game.diceClicked(9);
    }

    //"Roll" buttons:

    @FXML
    private void roll1(){
        if (resultBar.isVisible()) return;
        game.gameRoll(1);
    }

    @FXML
    private void roll2(){
        if (resultBar.isVisible()) return;
        game.gameRoll(2);
    }

    @FXML
    private void closeResults() {
        temporaryHide.setVisible(false);
        resultBar.setVisible(false);
        resultBar.setDisable(true);
        game.startRound();
    }

    @FXML
    private void saveAndExit(){
        if (resultBar.isVisible()) return;
        try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("Savegame.sav"))){
            file.writeObject(game);
            toMenu(player1Name);
        } catch (IOException e) {
            Errors errors = new Errors(player1Name);
            errors.writeError();
        }
    }

    @FXML
    private void hideResults(){
        if(resultBar.isVisible()) {
            resultBar.setVisible(false);
        }
    }

    @FXML
    private void showResults(){
        resultBar.setVisible(true);
    }
}
