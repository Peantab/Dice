package dice;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by pawel on 23.01.17.
 *
 * Class for counting points for game of Cubilete.
 */
public class FiveResult implements IResult, Serializable{
    final static long serialVersionUID = 1;

    private int playerResult[]; //Keeps results of a game.
    private int playerRound[];  //Size: amount of players. The result of a player
    private int playerRoundParticular[]; //Like playerRoundGeneral, used during ties.
    private String playerHand[]; //Hand owned by player

    public FiveResult() {
        playerResult = new int[]{0,0};
        playerRoundParticular = new int[2];
        playerHand = new String[2];
    }

    @Override
    public void makeRoundResults(Player player1, Player player2) {
        playerRound = new int[2];
        processHand(player1.diceSet, 0);
        processHand(player2.diceSet, 1);
        if (playerRound[0] > playerRound[1]) playerResult[0] += playerRound[0];
        else if (playerRound[1] > playerRound[0]) playerResult[1] += playerRound[1];
        else if (playerRoundParticular[0] > playerRoundParticular[1]) playerResult[0] += playerRound[0];
        else if (playerRoundParticular[1] > playerRoundParticular[0]) playerResult[1] += playerRound[1];
        else {
            playerResult[0] += playerRound[0];
            playerResult[1] += playerRound[1];
        }
    }

    @Override
    public int getPlayerResult(int player) {
        return playerResult[player];
    }

    @Override
    public boolean getGameFinished() {
        return (playerResult[0] >= 10) || (playerResult[1] >= 10);
    }

    @Override
    public String getPlayerDescription(int player) {
        return playerHand[player];
    }

    private void processHand(Die[] hand, int player) {
        if (IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 1 )){
            playerRound[player] = 10;
            playerRoundParticular[player] = 10;
            playerHand[player] = "Carabina de Aces";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 6 )){
            playerRound[player] = 5;
            playerRoundParticular[player] = 10;
            playerHand[player] = "Carabina de Kings Naturales";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 6 || hand[i].value == 1 )){
            playerRound[player] = 2;
            playerRoundParticular[player] = 10;
            playerHand[player] = "Carabina de Kings No Naturales";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 5 || hand[i].value == 1 )){
            playerRound[player] = 1;
            playerRoundParticular[player] = 5;
            playerHand[player] = "Queens";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 4 || hand[i].value == 1 )){
            playerRound[player] = 1;
            playerRoundParticular[player] = 4;
            playerHand[player] = "Jevas";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 3 || hand[i].value == 1 )){
            playerRound[player] = 1;
            playerRoundParticular[player] = 3;
            playerHand[player] = "Gallegos";
        }else if(IntStream.rangeClosed(0,4).allMatch(i -> hand[i].value == 2 || hand[i].value == 1 )){
            playerRound[player] = 1;
            playerRoundParticular[player] = 2;
            playerHand[player] = "Negros";
        }else{
            playerRound[player] = 0;
            playerRoundParticular[player] = 0;
            playerHand[player] = "Nothing...";
        }
    }
}
