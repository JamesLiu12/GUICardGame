public class Card {
    static final int CLUB = 1, SPADE = 2, DIAMOND = 3, HEART = 4;

    int decor, number;

    public Card(int decor, int number) {
        this.decor = decor;
        this.number = number;
    }

    public String getCardFileName() {
        return String.format("Images/card_%d%d.gif", decor, number);
    }
}