package dice;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created by Paweł Taborowski on 21.01.17.
 */
public class Manual extends ReturnToMenu{

    @FXML
    private Text title;

    @FXML
    private Text content;

    public void initialize(){
        title.setText("Manual");
        content.setText("Choose topic from the panel on the left, or press \"Back\" button to return to menu.");
    }

    @FXML
    private void pokerHands(){
        title.setText("Poker Hands");
        content.setText("In Poker–style games you can throw the following combinations (the best first):\n" +
                "• Poker – all 5 dice with the same value\n• Four of a kind – four dice with the same value\n• Full house – three dice form \"Three of a kind\", the remaining two form \"Pair\"\n" +
                "• Large straight – all of those are present: 2, 3, 4, 5, 6\n• Small Straight – all of those are present: 1, 2, 3, 4, 5\n• Three of a kind – three dice with the same value\n" +
                "• Two Pairs – two dice with the same value and another two dice also with the same value\n• Pair – two dice with the same value\n• Nothing – none of the above.");
    }

    @FXML
    private void witcher(){
        title.setText("Poker à la Witcher");
        content.setText("The game known from \"The Witcher\", \"The Witcher 2\" and \"Two Worlds II\" (and probably many more sources).\n" +
                "  Players roll their dice one after another, first time they have to roll all the dice, the second they can decide to save part of them and roll the rest. Player with a better Poker hand wins.\n" +
                "  Game consist of three rounds, in each both players roll two times. For each won game player gains one point, one who has more points win.");
    }

    @FXML
    private void poker(){
        title.setText("Poker modified");
        content.setText("In this variation, in his round, each player makes his two rolls one after another, without being interrupted by the other player.");
    }

    @FXML
    private void cubileteHands(){
        title.setText("Cubilete Hands");
        content.setText("In Cubilete you can throw the following combinations (the best first). Differently named combinations worth one point are not considered equal:\n" +
                "• Carabina de Aces – all 5 dice with \"1\" – 10 points\n• Carabina de Kings Naturales – all 5 dice with \"6\" – 5 points\n• Carabina de Kings No Naturales – among dice are only \"6s\" and \"1s\" – 2 points\n" +
                "• Queens – among dice are only \"5s\" and \"1s\" – 1 point\n• Jevas – among dice are only \"4s\" and \"1s\" – 1 point\n• Gallegos – among dice are only \"3s\" and \"1s\" – 1 point\n" +
                "• Negros – among dice are only \"3s\" and \"1s\" – 1 point\n• Nothing – none of the above.");
    }

    @FXML
    private void cubilete(){
        title.setText("Cubilete");
        content.setText("Traditional game popular in Cuba. The goal is to score 10 points by rolling combinations. During one turn, only scores a player with higher combination. Once you roll all of your dice, you can reroll any of them in two consecutive rolls.");
    }

    @FXML
    private void legal(){
        title.setText("Legal");
        content.setText("Copyright notice:\nfor background \"dark wood\" picture: Designed by Jannoon028 - Freepik.com\nfor background \"Old Wood\": Designed by jannoon028 / Freepik\n" +
                "Dice pictures: Kenney.nl; modified by Paweł Taborowski\n\nAll the other works are made by Paweł Taborowski.");
    }

    @FXML
    private void backToMenu(){
        toMenu(title);
    }
}
