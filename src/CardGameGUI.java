import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class represents the graphical user interface (GUI) for a card game.
 * It includes components like buttons, labels, and text fields to interact with the game logic.
 *
 * @author Liu Sizhe
 * @version 1.0
 * @since 2023-11-10
 */
public class CardGameGUI {
    private JFrame frame;

    private JLabel[] dealerCardsLabels, playerCardsLabels;

    private static final ImageIcon cardBackIcon = new ImageIcon("Images/card_back.gif");

    private JButton[] replaceButtons;

    private JTextField betInput;

    private JButton startButton, resultButton;

    private JLabel importantMessage, moneyRemainMessage;

    private final JOptionPane messageBox = new JOptionPane();

    private CardGameLogic cardGameLogic;

    /**
     * Initializes and displays the GUI components of the card game.
     */
    public void go() {
        cardGameLogic = new CardGameLogic();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 700);
        frame.setLayout(null);

        dealerCardsLabels = new JLabel[3];
        JPanel dealerCardsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            dealerCardsLabels[i] = new JLabel();
            dealerCardsPanel.add(dealerCardsLabels[i]);
        }
        dealerCardsPanel.setLayout(new FlowLayout());
        dealerCardsPanel.setSize(240, 100);
        dealerCardsPanel.setLocation(frame.getWidth() / 2 - dealerCardsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.05));

        playerCardsLabels = new JLabel[3];
        JPanel playerCardsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            playerCardsLabels[i] = new JLabel();
            playerCardsPanel.add(playerCardsLabels[i]);
        }
        playerCardsPanel.setLayout(new FlowLayout());
        playerCardsPanel.setSize(240, 100);
        playerCardsPanel.setLocation(frame.getWidth() / 2 - playerCardsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.2));

        replaceButtons = new JButton[3];
        JPanel replaceButtonsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            replaceButtons[i] = new JButton(String.format("Replace Card %d", i + 1));
            replaceButtonsPanel.add(replaceButtons[i]);
            replaceButtons[i].addActionListener(new replaceCardClicked());
        }
        replaceButtonsPanel.setLayout(new FlowLayout());
        replaceButtonsPanel.setSize(400, 40);
        replaceButtonsPanel.setLocation(frame.getWidth() / 2 - replaceButtonsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.4));

        JLabel betText = new JLabel("Bet: $");
        betInput = new JTextField(5);
        betInput.getDocument().addDocumentListener(new betInputChanged());
        JPanel betPanel = new JPanel();
        betPanel.add(betText);
        betPanel.add(betInput);
        betPanel.setLayout(new FlowLayout());
        betPanel.setSize(400, 40);
        betPanel.setLocation(frame.getWidth() / 2 - betPanel.getWidth() / 2, (int)(frame.getHeight() * 0.5));

        startButton = new JButton("Start");
        startButton.addActionListener(new startClicked());
        resultButton = new JButton("Result");
        resultButton.addActionListener(new resultClicked());
        JPanel startAndResultPanel = new JPanel();
        startAndResultPanel.add(startButton);
        startAndResultPanel.add(resultButton);
        startAndResultPanel.setLayout(new FlowLayout());
        startAndResultPanel.setSize(400, 40);
        startAndResultPanel.setLocation(frame.getWidth() / 2 - startAndResultPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.55));

        importantMessage = new JLabel("Please place your bet!");
        moneyRemainMessage = new JLabel(String.format("Money you have: $%d", cardGameLogic.getMoneyRemain()));
        JPanel messagesPanel = new JPanel();
        messagesPanel.add(importantMessage);
        messagesPanel.add(moneyRemainMessage);
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setSize(400, 80);
        messagesPanel.setLocation(frame.getWidth() / 2 - messagesPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.75));

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new exitClicked());
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(new restartClicked());
        JMenu controlMenu = new JMenu("Control");
        JMenuBar menuBar = new JMenuBar();
        controlMenu.add(exitMenuItem);
        controlMenu.add(restartMenuItem);
        menuBar.add(controlMenu);
        frame.setJMenuBar(menuBar);

        componentsSateInit();

        frame.add(dealerCardsPanel);
        frame.add(playerCardsPanel);
        frame.add(replaceButtonsPanel);
        frame.add(betPanel);
        frame.add(startAndResultPanel);
        frame.add(messagesPanel);
        frame.add(messageBox);
        frame.setVisible(true);
    }

    /**
     * Initializes the state of GUI components.
     */
    private void componentsSateInit() {
        startButton.setEnabled(false);
        betInput.setEnabled(true);
        resultButton.setEnabled(false);
        betInput.setText("");
        importantMessage.setText("Please place your bet!");
        moneyRemainMessage.setText(String.format("Money you have: $%d", cardGameLogic.getMoneyRemain()));
        for (int i = 0; i < 3; i++) {
            replaceButtons[i].setEnabled(false);
            dealerCardsLabels[i].setIcon(cardBackIcon);
            playerCardsLabels[i].setIcon(cardBackIcon);
        }
    }

    /**
     * Updates the player card labels to show current cards.
     */
    private void showPlayerCards() {
        for (int i = 0; i < 3; i++) {
            playerCardsLabels[i].setIcon(new ImageIcon(cardGameLogic.getPlayerCard(i).getCardFileName()));
        }
    }

    /**
     * Refreshes the display of a specific player card.
     *
     * @param cardIndex Index of the card to be refreshed.
     */
    private void refreshPlayerCard(int cardIndex) {
        playerCardsLabels[cardIndex].setIcon(new ImageIcon(cardGameLogic.getPlayerCard(cardIndex).getCardFileName()));
    }

    /**
     * Updates the dealer card labels to show current cards.
     */
    private void showDealerCards() {
        for (int i = 0; i < 3; i++) {
            dealerCardsLabels[i].setIcon(new ImageIcon(cardGameLogic.getDealerCard(i).getCardFileName()));
        }
    }

    /**
     * Inner class to handle 'Start' button click events.
     */
    class startClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cardGameLogic.start();
            showPlayerCards();
            betInput.setEnabled(false);
            startButton.setEnabled(false);
            resultButton.setEnabled(true);
            int bet = Integer.parseInt(betInput.getText());
            cardGameLogic.currentBet = bet;
            importantMessage.setText(String.format("Your current bet is: $%d", bet));
            for (int i = 0; i < 3; i++) replaceButtons[i].setEnabled(true);
        }
    }

    /**
     * Inner class to handle 'Replace Card' button click events.
     */
    class replaceCardClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 3; i++) {
                if (e.getSource() == replaceButtons[i]) {
                    cardGameLogic.changePlayerCard(i);
                    refreshPlayerCard(i);
                    replaceButtons[i].setEnabled(false);
                    if (cardGameLogic.isNoMoreReplace()) {
                        for (int j = 0; j < 3; j++) replaceButtons[j].setEnabled(false);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Inner class to handle 'Result' button click events.
     */
    class resultClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showDealerCards();
            boolean isPlayerWin = cardGameLogic.isPlayerWin();
            if (isPlayerWin) messageBox.setMessage("Congratulation! You win this round!");
            else messageBox.setMessage("Sorry! The dealer wins this round!");
            messageBox.createDialog(frame, "Message").setVisible(true);
            messageBox.setVisible(true);
            cardGameLogic.moneyUpdate(isPlayerWin);
            componentsSateInit();
            if (cardGameLogic.isGameOver()) {
                betInput.setEnabled(false);
                messageBox.setMessage("Game over!\nYou have no more money!\nPlease start a new game!");
                importantMessage.setText("You have no more money!");
                moneyRemainMessage.setText("Please start a new game!");
            }
        }
    }

    /**
     * Inner class to handle changes in the bet input field.
     */
    class betInputChanged implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            String text = betInput.getText();
            if (text.matches("\\d+")) {
                if (Integer.parseInt(text) <= cardGameLogic.getMoneyRemain()) {
                    startButton.setEnabled(true);
                    return;
                }
            }
            startButton.setEnabled(false);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            insertUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {}
    }

    /**
     * Inner class to handle 'Exit' menu item click events.
     */
    class exitClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    /**
     * Inner class to handle 'Restart' menu item click events.
     */
    class restartClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cardGameLogic = new CardGameLogic();
            componentsSateInit();
        }
    }
}