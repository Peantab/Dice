package dice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static dice.Controller.*;

/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * The core class governing the play of any supported game.
 */

public class Game implements Serializable{
    final static long serialVersionUID = 1;
    private Genre genre; //What genre of dice are we playing now?
    private AbstractRulebook rules; //How is this game to be played?
    private IResult result; //What are results for the game in its variant?
    private boolean isLoaded = false; //If game is loaded from file, savegame may be deleted when finished.

    //Players
    private Player player1;
    private Player player2;

    public Game(Genre genre){
        this.genre = genre;
        player1 = new Player(0);
        player2 = new Player(1);

        player1.setName(Settings.player1);
        player2.setName(Settings.player2);

        switch(genre){
            case witcher:
                rules = new Witcher();
                result = new PokerResult();
                break;
            case poker:
                rules = new Poker();
                result = new PokerResult();
                break;
            case five:
                rules = new Five();
                result = new FiveResult();
                break;
            default:
                Errors errors = new Errors(resultWindow.get("player1Score"));
                errors.invalidGame();
        }

        startRound();
    }

    void startRound() {
        if (result.getGameFinished()){
            try {
                Files.deleteIfExists(FileSystems.getDefault().getPath("Savegame.sav"));
            } catch (IOException e) {
                //
            }
            Stage stage = (Stage) resultWindow.get("player1Score").getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Errors errors = new Errors(resultWindow.get("player1Score"));
                errors.exitAfterError();
            }
            stage.setScene(new Scene(root));
        }
        rules.roundEnded = false;
        player1.prepareDice(rules.diceDefaultValues);
        player2.prepareDice(rules.diceDefaultValues);

        player1.readyAllDice();
        player2.readyAllDice();

        rules.startRound();

        updateStatus();
    }

    private void showResults(){
        result.makeRoundResults(player1, player2);
        ((Text)resultWindow.get("player1Name")).setText(player1.getName());
        ((Text)resultWindow.get("player2Name")).setText(player2.getName());
        ((Text)resultWindow.get("player1Score")).setText(Integer.toString(result.getPlayerResult(0)));
        ((Text)resultWindow.get("player2Score")).setText(Integer.toString(result.getPlayerResult(1)));
        ((Text)resultWindow.get("player1Description")).setText(result.getPlayerDescription(0));
        ((Text)resultWindow.get("player2Description")).setText(result.getPlayerDescription(1));
        if (!result.getGameFinished()) ((Button)resultWindow.get("hideResults")).setText("Next round");
        else ((Button)resultWindow.get("hideResults")).setText("Finish game");
        resultWindow.get("resultBar").setVisible(true);
        resultWindow.get("resultBar").setDisable(false);
        resultsFadeIn.playFromStart();
        resultWindow.get("temporaryHide").setVisible(true);
        hideFadeIn.playFromStart();
    }

    void diceClicked(int which) {
        if(rules.permittedSwitch(which)){
            if(which < 5){
                player1.switchRoll(which);
            }else{
                player2.switchRoll(which-5);
            }
        }
    }

    void gameRoll(int player) { //Player 'player' pressed "roll" button.
        if(rules.permittedRoll(player)){
            if (player == 1){
                player1.playerRoll();
            }else{
                player2.playerRoll();
            }
            updateStatus();
            if (rules.roundEnded){
                showResults();
            }
        }
    }

    private void updateStatus(){ //Let's change the message for player(s)
        String player;
        if(rules.player1active && !rules.player2active) player = player1.getName();
        else if(!rules.player1active && rules.player2active) player = player2.getName();
        else player = "Players";
        gameStatus.setText(player + ": " + rules.rulesStatus);
    }

    void revertFromSave() {
        rules.revertFromSave();
        player1.revertFromSave();
        player2.revertFromSave();
        updateStatus();
        isLoaded = true;
    }
}
