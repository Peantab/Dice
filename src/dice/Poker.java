package dice;

import java.io.Serializable;

import static dice.Controller.arrows;

/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * The class stating the rules of the firstly written game – one of infinite number of Poker variations.
 */
public class Poker extends AbstractRulebook implements Serializable{
    final static long serialVersionUID = 1;

    private int round;
    private int rollCounter;

    public Poker(){
        //Required fields
        diceDefaultValues = new int[]{1, 2, 3, 5, 6};
        diceDefaultRollable = new boolean[]{true, true, true, true, true};

        //Specific fields
        round = 0;
        rollCounter = 1;
        startRound();
    }

    public void startRound(){
        round++;
        rollCounter = 1;

        rulesStatus = "Roll the dice!";

        player1active = true;
        player2active = false;
        arrows[0].setVisible(true);
        arrows[1].setVisible(false);
    }

    private void startPlayer2(){
        rollCounter = 1;

        rulesStatus = "Roll the dice!";

        player1active = false;
        player2active = true;
        arrows[0].setVisible(false);
        arrows[1].setVisible(true);
    }

    @Override
    boolean permittedSwitch(int which) {
        if (rollCounter!=1) { //First roll must be made with all dice.
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
            rollCounter++; //This roll will be now performed.
            rulesStatus = "Choose dice to keep and reroll the rest.";
            if (rollCounter == 3){
                if(player1active) startPlayer2();
                else{
                    roundEnded = true;
                }
            }
            return true;
        }
        else return false;
    }
}
