public class CardGameLogic {
    private final Card[] deck, dealerCards, playerCards;
    private int timeChange, moneyRemain;
    public int currentBet;

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

    private void shuffle() {
        for (int i = 0; i < 52; i++) {
            int j = (int)(Math.random() * 52);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public void start() {
        shuffle();
        for (int i = 0; i < 3; i++) {
            dealerCards[i] = deck[i];
            playerCards[i] = deck[i + 3];
        }
        timeChange = 0;
    }

    public void changePlayerCard(int cardNumber) {
        playerCards[cardNumber] = deck[6 + timeChange];
        timeChange++;
    }

    public Card getDealerCard(int cardIndex) {
        return dealerCards[cardIndex];
    }
    public Card getPlayerCard(int cardIndex) {
        return playerCards[cardIndex];
    }
    public int getMoneyRemain() {
        return moneyRemain;
    }
    public void moneyUpdate(boolean isPlayerWin) {
        moneyRemain += isPlayerWin ? currentBet : -currentBet;
    }
    public boolean isNoMoreReplace() {
        return timeChange == 2;
    }

    private boolean isSpecialCard(Card card) {
        return card.number > 10;
    }
    private int getSpecialCardNumber(Card[] cards) {
        int result = 0;
        for (int i = 0; i < 3; i++) if(isSpecialCard(cards[i])) result++;
        return result;
    }
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
    public boolean isGameOver() {
        return moneyRemain <= 0;
    }
}
