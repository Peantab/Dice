package dice;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Paweł Taborowski on 09.01.17.
 *
 * Class for counting points in Poker. Shared across two variants of a game.
 */
public class PokerResult implements IResult, Serializable{
    final static long serialVersionUID = 1;

    private int playerRoundGeneral[];  //Size: amount of players. The result of a player
    private int playerRoundParticular[]; //Like playerRoundGeneral, used during ties.
    private int playerRoundThirdRow[]; //Used during ties, when playerRoundParticular didn't help.

    private int playerResult[]; //Keeps results of a game.
    private String playerHand[]; //Hand owned by player

    public PokerResult() {
        this.playerResult = new int[]{0,0};
        this.playerHand = new String[2];
    }

    @Override
    public void makeRoundResults(Player player1, Player player2) {
        playerRoundGeneral = new int[2];
        playerRoundParticular = new int[2];
        playerRoundThirdRow = new int[]{0,0};

        processHand(player1.diceSet, 0);
        processHand(player2.diceSet, 1);

        if (playerRoundGeneral[0] > playerRoundGeneral[1]){
            playerResult[0]++;
        }else if (playerRoundGeneral[1] > playerRoundGeneral[0]){
            playerResult[1]++;
        }else{
            if (playerRoundParticular[0] > playerRoundParticular[1]){
                playerResult[0]++;
            }else if (playerRoundParticular[1] > playerRoundParticular[0]){
                playerResult[1]++;
            }else{
                if (playerRoundThirdRow[0] > playerRoundThirdRow[1]){
                    playerResult[0]++;
                }else if (playerRoundThirdRow[1] > playerRoundThirdRow[0]){
                    playerResult[1]++;
                }
            }
        }
    }

    @Override
    public int getPlayerResult(int player) {
        return playerResult[player];
    }

    @Override
    public boolean getGameFinished() {
        return playerResult[0] + playerResult[1] == 3;
    }

    @Override
    public String getPlayerDescription(int player) {
        return playerHand[player];
    }

    private void processHand(Die[] hand, int player){
        //Segregation
        int occurrences[] = new int[6];
        for (int i=0; i<5; i++){
            occurrences[hand[i].value -1]++;
        }

        //For two pairs
        int sortedOccurrences[] = new int[6];
        System.arraycopy(occurrences, 0, sortedOccurrences, 0, 6);
        Arrays.sort(sortedOccurrences);

        if (IntStream.of(occurrences).anyMatch(x -> x == 5)){ //Poker
            playerRoundGeneral[player] = 9;
            playerRoundParticular[player] = hand[0].value;
            playerHand[player] = "Poker!";
        }else if(IntStream.of(occurrences).anyMatch(x -> x == 4)){ //Four of a kind
            playerRoundGeneral[player] = 8;
            playerHand[player] = "Four of a Kind";
            if(hand[0].value == hand[1].value) playerRoundParticular[player] = hand[0].value;
            else playerRoundParticular[player] = hand[2].value;
            //Szukanie ostatniej
            for (int i=0; i<5; i++) if (hand[i].value != playerRoundParticular[player]) playerRoundThirdRow[player] = hand[i].value;
        }else if(IntStream.of(occurrences).anyMatch(x -> x == 3) && IntStream.of(occurrences).anyMatch(x -> x == 2)){ // Full House
            playerRoundGeneral[player] = 7;
            int particular = 0; //Two digit number of form '[Three of a kind][Pair]'
            for(int i = 0; i<6; i++){
                if(occurrences[i] == 3) particular += 10*i;
                if(occurrences[i] == 2) particular += i;
            }
            playerRoundParticular[player] = particular;
            playerHand[player] = "Full House";
        }else if(!(IntStream.of(occurrences).anyMatch(x -> x > 1)) && occurrences[0] == 0){ //Large Straight
            playerRoundGeneral[player] = 6;
            playerRoundParticular[player] = 0;
            playerHand[player] = "Large Straight";
        }else if(!(IntStream.of(occurrences).anyMatch(x -> x > 1)) && occurrences[5] == 0) { //Small Straight
            playerRoundGeneral[player] = 5;
            playerRoundParticular[player] = 0;
            playerHand[player] = "Small Straight";
        }else if(IntStream.of(occurrences).anyMatch(x -> x == 3)){ //Three of a kind
            playerRoundGeneral[player] = 4;
            playerHand[player] = "Three of a kind";
            for(int i = 0; i<6; i++){
                if(occurrences[i] == 3) playerRoundParticular[player] = i;
            }
            int particular = 0; //Two digit number of form '[Older][Younger]'
            int multiplier=1;
            for(int i=0;i<6;i++){
                if (occurrences[i] == 1){
                    particular += multiplier*i;
                    multiplier += 9;
                }
            }
            playerRoundThirdRow[player] = particular;
        }else if(sortedOccurrences[4] == 2){ //Two Pairs
            playerRoundGeneral[player] = 3;
            playerHand[player] = "Two Pairs";
            int particular = 0; //Two digit number of form '[Older Pair][Pair]'
            int multiplier=1;
            for(int i=0;i<6;i++){
                if (occurrences[i] == 2){
                    particular += multiplier*i;
                    multiplier += 9;
                }
            }
            playerRoundParticular[player] = particular;

            //Last die
            for(int i=0;i<6;i++) if(occurrences[i] == 1) playerRoundThirdRow[player] = i;
        }else if(sortedOccurrences[5] == 2){ //Pair
            playerRoundGeneral[player] = 2;
            playerHand[player] = "Pair";
            for(int i=0;i<6;i++){
                if (occurrences[i] == 2) playerRoundParticular[player] = i;
            }
            //Third row
            int particular = 0; //Three digit number of form '[Eldest][Older][Young]'
            int multiplier=1;
            for(int i=0;i<6;i++){
                if (occurrences[i] == 1){
                    particular += multiplier*i;
                    multiplier *= 10;
                }
            }
            playerRoundThirdRow[player] = particular;
        }else{ //Nothing
            playerRoundGeneral[player] = 1;
            playerHand[player] = "Nothing...";
            int particular = 0;
            for(int i=0;i<5;i++){
                particular += hand[i].value;
            }
            playerRoundParticular[player] = particular;
        }
    }
}
