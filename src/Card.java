/**
 * The {@code Card} class represents a playing card with a specific suit and number.
 * It provides functionality to create a card and retrieve its associated image file name.
 *
 * @author Liu Sizhe
 * @version 1.0
 * @since 2023-11-10
 */
public class Card {

    /**
     * Constants representing the clubs suits.
     */
    public static final int CLUB = 1, SPADE = 2, DIAMOND = 3, HEART = 4;

    /**
     * The suit of the card.
     */
    public final int suit;

    /**
     * The number of the card.
     */
    public final int number;

    /**
     * Constructs a Card with specified suit and number.
     *
     * @param suit  The suit of the card, represented by one of the constants (CLUB, SPADE, DIAMOND, HEART).
     * @param number The number of the card.
     */
    public Card(int suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    /**
     * Returns the file name of the card image.
     * The file name is generated based on the suit and number of the card.
     *
     * @return A string representing the file name of the card image.
     */
    public String getCardFileName() {
        return String.format("Images/card_%d%d.gif", suit, number);
    }
}