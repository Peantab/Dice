package dice;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Paweł Taborowski on 21.01.17.
 *
 * Class for keeping players' names across games during one run of the program
 * and storing/restoring them using mass storage
 */
class Settings implements Externalizable {
    static String player1 = "Player 1";
    static String player2 = "Player 2";

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(player1);
        out.writeObject(player2);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        player1 = (String) in.readObject();
        player2 = (String) in.readObject();
    }
}
