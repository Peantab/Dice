package dice;

import java.io.Serializable;

import static dice.Controller.arrows;

/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * The class stating the rules of Dice Poker, as known from The Witcher gae series.
 */
public class Witcher extends AbstractRulebook implements Serializable{
    final static long serialVersionUID = 1;

    private int rollCounter;

    public Witcher(){
        //Required fields
        diceDefaultValues = new int[]{1, 2, 3, 5, 6};
        diceDefaultRollable = new boolean[]{true, true, true, true, true};

        //Specific fields
        rollCounter = 1;
        startRound();
    }

    public void startRound(){
        rollCounter = 0;

        //Initial settings just to be negated
        player1active = false;
        player2active = true;
        arrows[0].setVisible(false);
        arrows[1].setVisible(true);

        startPlayer();
    }

    private void startPlayer(){
        rollCounter++;

        rulesStatus = "Roll the dice!";

        player1active = !player1active;
        player2active = !player2active;
        arrows[0].setVisible(!arrows[0].isVisible());
        arrows[1].setVisible(!arrows[1].isVisible());
    }

    private void continuePlayer(){
        rollCounter++;

        rulesStatus = "Choose dice to keep and reroll the rest.";

        player1active = !player1active;
        player2active = !player2active;
        arrows[0].setVisible(!arrows[0].isVisible());
        arrows[1].setVisible(!arrows[1].isVisible());
    }

    @Override
    boolean permittedSwitch(int which) {
        if (rollCounter==3 || rollCounter==4) { //First roll must be made with all dice.
            if(player1active){ //Player 1 is active
                if (which < 5) return true; //Player 1's die is to be changed
            } else {           //Player 2 is active
                if (which >= 5) return true; //Player 2's die is to be changed
            }
        }
        return false; //Other possibilities
    }

    @Override
    public boolean permittedRoll(int player) {
        if((player == 1 && player1active) || (player == 2 && player2active)){
            rulesStatus = "Choose dice to keep and reroll the rest.";
            if (rollCounter == 1) {
                startPlayer();
            }else if(rollCounter == 2 || rollCounter == 3){
                continuePlayer();
            }
            else{
                roundEnded = true;
            }
            return true;
        }
        else return false;
    }
}
