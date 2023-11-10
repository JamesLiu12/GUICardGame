/**
 * Represents the logic for a card game.
 * This class manages the game deck, the cards for the dealer and the player, and the game's monetary aspects.
 *
 * @author Liu Sizhe
 * @version 1.0
 * @since 2023-11-10
 */
public class CardGameLogic {
    private final Card[] deck, dealerCards, playerCards;
    private int timeChange, moneyRemain;
    public int currentBet;


    /**
     * Constructs a CardGameLogic object.
     * Initializes the deck with 52 cards, assigns initial cards for the dealer and player, and sets initial money.
     */
    public CardGameLogic() {
        deck = new Card[52];
        for (int decor = 1; decor <= 4; decor++) {
            for (int number = 1; number <= 13; number++) {
                deck[(decor - 1) * 13 + number - 1] = new Card(decor, number);
            }
        }
        dealerCards = new Card[3];
        playerCards = new Card[3];
        moneyRemain = 100;
    }

    /**
     * Shuffles the deck of cards.
     */
    private void shuffle() {
        for (int i = 0; i < 52; i++) {
            int j = (int)(Math.random() * 52);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    /**
     * Starts a new game round by shuffling the deck and dealing cards to the dealer and the player.
     */
    public void start() {
        shuffle();
        for (int i = 0; i < 3; i++) {
            dealerCards[i] = deck[i];
            playerCards[i] = deck[i + 3];
        }
        timeChange = 0;
    }

    /**
     * Changes one of the player's cards.
     *
     * @param cardIndex The index of the player's card to be changed.
     */
    public void changePlayerCard(int cardIndex) {
        playerCards[cardIndex] = deck[6 + timeChange];
        timeChange++;
    }

    /**
     * Retrieves one of the dealer's cards.
     *
     * @param cardIndex The index of the dealer's card to retrieve.
     * @return The dealer's card at the specified index.
     */
    public Card getDealerCard(int cardIndex) {
        return dealerCards[cardIndex];
    }

    /**
     * Retrieves one of the player's cards.
     *
     * @param cardIndex The index of the player's card to retrieve.
     * @return The player's card at the specified index.
     */
    public Card getPlayerCard(int cardIndex) {
        return playerCards[cardIndex];
    }

    /**
     * Returns the remaining amount of money.
     *
     * @return The current remaining money.
     */
    public int getMoneyRemain() {
        return moneyRemain;
    }

    /**
     * Updates the amount of money based on the game outcome.
     *
     * @param isPlayerWin True if the player wins the round, false otherwise.
     */
    public void moneyUpdate(boolean isPlayerWin) {
        moneyRemain += isPlayerWin ? currentBet : -currentBet;
    }

    /**
     * Determines if the player can no longer replace cards.
     *
     * @return True if no more card replacements are allowed, false otherwise.
     */
    public boolean isNoMoreReplace() {
        return timeChange == 2;
    }

    /**
     * Checks if a given card is a special card (number greater than 10).
     *
     * @param card The card to check.
     * @return True if the card is special, false otherwise.
     */
    private boolean isSpecialCard(Card card) {
        return card.number > 10;
    }

    /**
     * Counts the number of special cards in a given array of cards.
     *
     * @param cards The array of cards to check.
     * @return The number of special cards in the array.
     */
    private int getSpecialCardNumber(Card[] cards) {
        int result = 0;
        for (int i = 0; i < 3; i++) if(isSpecialCard(cards[i])) result++;
        return result;
    }

    /**
     * Determines if the player wins the round.
     *
     * @return True if the player wins, false otherwise.
     */
    public boolean isPlayerWin() {
        int playerSpecialCardNumber = getSpecialCardNumber(playerCards),
                dealerSpecialCardNumber = getSpecialCardNumber(dealerCards);
        if (playerSpecialCardNumber > dealerSpecialCardNumber) return true;
        else if (dealerSpecialCardNumber > playerSpecialCardNumber) return false;
        int playerPoints = 0, dealerPoints = 0;
        for (int i = 0; i < 3; i++) {
            if (!isSpecialCard(playerCards[i])) playerPoints += playerCards[i].number;
            if (!isSpecialCard(dealerCards[i])) dealerPoints += dealerCards[i].number;
        }
        playerPoints %= 10;
        dealerPoints %= 10;
        return playerPoints > dealerPoints;
    }

    /**
     * Checks if the game is over (i.e., if the player has run out of money).
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return moneyRemain <= 0;
    }
}
