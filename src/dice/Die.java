package dice;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Random;
import java.util.stream.IntStream;

import static dice.Controller.plDice;
import static dice.Controller.selections;


/**
 * Created by Paweł Taborowski on 08.01.17.
 *
 * Class for dice management (its states and display)
 */

public class Die implements Serializable{
    final static long serialVersionUID = 1;
    private int id;
    int value;
    private boolean toRoll;
    private Random random;
    private final static Image pics[] = new Image[]{new Image("blank.png"),
            new Image("1.png"),
            new Image("2.png"),
            new Image("3.png"),
            new Image("4.png"),
            new Image("5.png"),
            new Image("6.png")};


    public Die(int id){
        this.id=id;
        this.value = 1;
        this.toRoll = false;
        random = new Random();
        if(IntStream.rangeClosed(0, 6).anyMatch(i -> pics[i].isError())){
            Errors errors = new Errors(plDice[0]);
            errors.exitAfterError();
        }
    }

    void setValue(int side){
        value = side;
        plDice[id].setImage(pics[side]);
    }

    public void setToRoll() {
        toRoll = true;
        selections[id].setVisible(false);
    }

    public void switchToRoll() {
        if (toRoll){
            selections[id].setVisible(true);
            toRoll = false;
        }else{
            selections[id].setVisible(false);
            toRoll = true;
        }
    }

    public void diceRoll() {
        if(toRoll){
            setValue(random.nextInt(6) + 1);
        }
    }

    public void revertFromSave() {
        if (toRoll) selections[id].setVisible(false);
        else selections[id].setVisible(true);
        plDice[id].setImage(pics[value]);
    }
}
