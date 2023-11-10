import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CardGameGUI {
    private JFrame frame;

    private JLabel[] dealerCardsLabels, playerCardsLabels;
    private JPanel dealerCardsPanel, playerCardsPanel;

    private static final ImageIcon cardBackIcon = new ImageIcon("Images/card_back.gif");

    private JButton[] replaceButtons;
    private JPanel replaceButtonsPanel;

    private JLabel betText;
    private JTextField betInput;
    private JPanel betPanel;

    private JButton startButton, resultButton;
    private JPanel startAndResultPanel;

    private JLabel importantMessage, moneyRemainMessage;
    private JPanel messagesPanel;

    private JOptionPane messageBox = new JOptionPane();

    private JMenuBar menuBar;
    private JMenu controlMenu;
    private JMenuItem exitMenuItem, restartMenuItem;


    CardGameLogic cardGameLogic;

    public void go() {
        cardGameLogic = new CardGameLogic();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 700);
        frame.setLayout(null);

        dealerCardsLabels = new JLabel[3];
        dealerCardsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            dealerCardsLabels[i] = new JLabel();
            dealerCardsPanel.add(dealerCardsLabels[i]);
        }
        dealerCardsPanel.setLayout(new FlowLayout());
        dealerCardsPanel.setSize(240, 100);
        dealerCardsPanel.setLocation(frame.getWidth() / 2 - dealerCardsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.05));

        playerCardsLabels = new JLabel[3];
        playerCardsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            playerCardsLabels[i] = new JLabel();
            playerCardsPanel.add(playerCardsLabels[i]);
        }
        playerCardsPanel.setLayout(new FlowLayout());
        playerCardsPanel.setSize(240, 100);
        playerCardsPanel.setLocation(frame.getWidth() / 2 - playerCardsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.2));

        replaceButtons = new JButton[3];
        replaceButtonsPanel = new JPanel();
        for (int i = 0; i < 3; i++) {
            replaceButtons[i] = new JButton(String.format("Replace Card %d", i + 1));
            replaceButtonsPanel.add(replaceButtons[i]);
            replaceButtons[i].addActionListener(new replaceCardClicked());
        }
        replaceButtonsPanel.setLayout(new FlowLayout());
        replaceButtonsPanel.setSize(400, 40);
        replaceButtonsPanel.setLocation(frame.getWidth() / 2 - replaceButtonsPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.4));

        betText = new JLabel("Bet: $");
        betInput = new JTextField(5);
        betInput.getDocument().addDocumentListener(new betInputChanged());
        betPanel = new JPanel();
        betPanel.add(betText);
        betPanel.add(betInput);
        betPanel.setLayout(new FlowLayout());
        betPanel.setSize(400, 40);
        betPanel.setLocation(frame.getWidth() / 2 - betPanel.getWidth() / 2, (int)(frame.getHeight() * 0.5));

        startButton = new JButton("Start");
        startButton.addActionListener(new startClicked());
        resultButton = new JButton("Result");
        resultButton.addActionListener(new resultClicked());
        startAndResultPanel = new JPanel();
        startAndResultPanel.add(startButton);
        startAndResultPanel.add(resultButton);
        startAndResultPanel.setLayout(new FlowLayout());
        startAndResultPanel.setSize(400, 40);
        startAndResultPanel.setLocation(frame.getWidth() / 2 - startAndResultPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.55));

        importantMessage = new JLabel("Please place your bet!");
        moneyRemainMessage = new JLabel(String.format("Money you have: $%d", cardGameLogic.getMoneyRemain()));
        messagesPanel = new JPanel();
        messagesPanel.add(importantMessage);
        messagesPanel.add(moneyRemainMessage);
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setSize(400, 80);
        messagesPanel.setLocation(frame.getWidth() / 2 - messagesPanel.getWidth() / 2,
                (int)(frame.getHeight() * 0.75));

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new exitClicked());
        restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(new restartClicked());
        controlMenu = new JMenu("Control");
        menuBar = new JMenuBar();
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

    private void showPlayerCards() {
        for (int i = 0; i < 3; i++) {
            playerCardsLabels[i].setIcon(new ImageIcon(cardGameLogic.getPlayerCard(i).getCardFileName()));
        }
    }

    private void refreshPlayerCard(int cardIndex) {
        playerCardsLabels[cardIndex].setIcon(new ImageIcon(cardGameLogic.getPlayerCard(cardIndex).getCardFileName()));
    }

    private void showDealerCards() {
        for (int i = 0; i < 3; i++) {
            dealerCardsLabels[i].setIcon(new ImageIcon(cardGameLogic.getDealerCard(i).getCardFileName()));
        }
    }

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

    class exitClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    class restartClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cardGameLogic = new CardGameLogic();
            componentsSateInit();
        }
    }
}