package dice;

import java.io.Serializable;

/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * The class representing a player – keeps and governs his name and set of dice.
 */

public class Player implements Serializable{
    final static long serialVersionUID = 1;
    int id;
    private String name;
    Die diceSet[];

    public Player(int number){
        id = number;
        name = "Player " + (number + 1);
        diceSet = new Die[5];

        for (int i=0; i<5; i++) diceSet[i] = new Die(number*5+i);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void prepareDice(int[] diceDefaultValues) {
        for (int i=0; i<5; i++){
            diceSet[i].setValue(diceDefaultValues[i]);
        }
    }

    public void readyAllDice() {
        for (int i=0; i<5; i++){
            diceSet[i].setToRoll();
        }
    }

    public void switchRoll(int which) {
        diceSet[which].switchToRoll();
    }

    public void playerRoll() {
        for (int i=0; i<5; i++){
            diceSet[i].diceRoll();
        }
    }

    public void revertFromSave(){
        for (int i=0; i<5; i++){
            diceSet[i].revertFromSave();
        }
    }
}
