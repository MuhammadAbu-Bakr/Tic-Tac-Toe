import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
 *
 * @author Muhammad AbuBakr
 *  
 */
public class TicTacToe extends JFrame {
    // Game settings
    private static final int BOARD_SIZE = 3;
    private static final char PLAYER_SYMBOL = 'O';
    private static final char COMPUTER_SYMBOL = 'X';
    private static final String PLAYER_1_SCORE_TEXT = "P1 Score: ";
    private static final String PLAYER_2_SCORE_TEXT = "P2 Score: ";
    private static final String TURN_TEXT = "Turn: ";

    // Game state variables
    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private int turn = 0;
    private int player1Score = 0;
    private int player2Score = 0;
    private boolean isSinglePlayer = true;
    private Random random = new Random();

    // UI components
    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    private JLabel turnLabel;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JPanel boardPanel;

    public TicTacToe() {
        initializeUI();
        resetGame();
    }

    private void initializeUI() {
        setTitle("Tic-Tac-Toe Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

       
        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 60));
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        // Control panel for scores and turn
        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        turnLabel = new JLabel(TURN_TEXT + (turn + 1), SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player1ScoreLabel = new JLabel(PLAYER_1_SCORE_TEXT + player1Score, SwingConstants.CENTER);
        player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player2ScoreLabel = new JLabel(PLAYER_2_SCORE_TEXT + player2Score, SwingConstants.CENTER);
        player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        controlPanel.add(turnLabel);
        controlPanel.add(player1ScoreLabel);
        controlPanel.add(player2ScoreLabel);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem resetMenuItem = new JMenuItem("Reset");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        resetMenuItem.addActionListener(e -> resetGame());
        exitMenuItem.addActionListener(e -> System.exit(0));

        optionsMenu.add(resetMenuItem);
        optionsMenu.add(exitMenuItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }

    private void resetGame() {
        // Clear the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = ' ';
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }

        // Reset scores and turn
        turn = 0;
        player1Score = 0;
        player2Score = 0;
        updateUI();
    }

    private void updateUI() {
        turnLabel.setText(TURN_TEXT + (turn + 1));
        player1ScoreLabel.setText(PLAYER_1_SCORE_TEXT + player1Score);
        player2ScoreLabel.setText(PLAYER_2_SCORE_TEXT + player2Score);
    }

    private void handleButtonClick(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = PLAYER_SYMBOL;
            buttons[row][col].setText(String.valueOf(PLAYER_SYMBOL));

            if (checkWin(PLAYER_SYMBOL)) {
                JOptionPane.showMessageDialog(this, "Player 1 wins!");
                player1Score++;
                lockBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
            } else {
                turn++;
                if (isSinglePlayer) {
                    computerMove();
                }
            }
            updateUI();
        }
    }

    private void computerMove() {
        boolean moveMade = false;
        while (!moveMade) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            if (board[row][col] == ' ') {
                board[row][col] = COMPUTER_SYMBOL;
                buttons[row][col].setText(String.valueOf(COMPUTER_SYMBOL));

                if (checkWin(COMPUTER_SYMBOL)) {
                    JOptionPane.showMessageDialog(this, "Computer wins!");
                    player2Score++;
                    lockBoard();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                } else {
                    turn++;
                }
                moveMade = true;
                updateUI();
            }
        }
    }

    private boolean checkWin(char symbol) {
        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void lockBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleButtonClick(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
