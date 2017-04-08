package dice;


/**
 * Created by Paweł Taborowski on 09.01.17.
 *
 * Interface generalizing counting points in dice games.
 */
public interface IResult{
    void makeRoundResults(Player player1, Player player2);

    int getPlayerResult(int player);

    boolean getGameFinished();

    String getPlayerDescription(int player);
}
