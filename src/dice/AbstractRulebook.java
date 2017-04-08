package dice;

import java.io.Serializable;

import static dice.Controller.arrows;

/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * Requirements and default method for classes stating the rules of dice games.
 */
public abstract class AbstractRulebook implements Serializable{
    final static long serialVersionUID = 1;
    abstract boolean permittedSwitch(int which); //Is it allowed to roll dice number 'which'?
    public abstract boolean permittedRoll(int player); //Is player 'player' allowed to roll now?
    public abstract void startRound(); //Start new round

    public void revertFromSave() { //After loading game from disk, point to active player appropriately.
        if (player1active) {
            arrows[0].setVisible(true);
            arrows[1].setVisible(false);
        } else {
            arrows[0].setVisible(false);
            arrows[1].setVisible(true);
        }
    }

    int diceDefaultValues[];
    boolean diceDefaultRollable[];
    public String rulesStatus;

    boolean player1active;
    boolean player2active;

    public boolean roundEnded = false; //One round of the game has just ended.
}
