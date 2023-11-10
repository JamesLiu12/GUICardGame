/**
 * The Main class for starting the card game.
 * This class contains the main method which initiates the card game GUI.
 *
 * @author Liu Sizhe
 * @version 1.0
 * @since 2023-11-10
 */
public class Main {

    /**
     * The entry point for the application.
     * This method creates an instance of CardGameGUI and starts the game.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        CardGameGUI cardGameGUI = new CardGameGUI();
        cardGameGUI.go();
    }
}