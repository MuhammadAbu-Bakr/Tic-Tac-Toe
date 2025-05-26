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
class PlayOptionsScreen extends JFrame {
    public PlayOptionsScreen() {
        setTitle("Play Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Select Game Mode");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JButton singlePlayerBtn = new JButton("Single Player");
        JButton multiPlayerBtn = new JButton("Multi Player");
        JButton backBtn = new JButton("Back to Menu");

        singlePlayerBtn.setPreferredSize(new Dimension(200, 40));
        multiPlayerBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.setPreferredSize(new Dimension(200, 40));

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(singlePlayerBtn, gbc);

        gbc.gridy = 2;
        mainPanel.add(multiPlayerBtn, gbc);

        gbc.gridy = 3;
        mainPanel.add(backBtn, gbc);

        singlePlayerBtn.addActionListener(e -> {
            TicTacToe game = new TicTacToe(true);
            game.setVisible(true);
            dispose();
        });

        multiPlayerBtn.addActionListener(e -> {
            TicTacToe game = new TicTacToe(false);
            game.setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MenuScreen().setVisible(true);
        });

        add(mainPanel);
    }
}

class MenuScreen extends JFrame {
    public MenuScreen() {
        setTitle("Tic-Tac-Toe Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Tic-Tac-Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JButton playBtn = new JButton("1. Play");
        JButton optionsBtn = new JButton("2. Options");
        JButton leaderboardBtn = new JButton("3. Leaderboard");
        JButton aboutBtn = new JButton("4. About");
        JButton quitBtn = new JButton("5. Quit");

        playBtn.setPreferredSize(new Dimension(200, 40));
        optionsBtn.setPreferredSize(new Dimension(200, 40));
        leaderboardBtn.setPreferredSize(new Dimension(200, 40));
        aboutBtn.setPreferredSize(new Dimension(200, 40));
        quitBtn.setPreferredSize(new Dimension(200, 40));

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(playBtn, gbc);

        gbc.gridy = 2;
        mainPanel.add(optionsBtn, gbc);

        gbc.gridy = 3;
        mainPanel.add(leaderboardBtn, gbc);

        gbc.gridy = 4;
        mainPanel.add(aboutBtn, gbc);

        gbc.gridy = 5;
        mainPanel.add(quitBtn, gbc);

        playBtn.addActionListener(e -> {
            dispose();
            new PlayOptionsScreen().setVisible(true);
        });

        optionsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Options feature coming soon!");
        });

        leaderboardBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Leaderboard feature coming soon!");
        });

        aboutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "About section coming soon!");
        });

        quitBtn.addActionListener(e -> System.exit(0));

        add(mainPanel);
    }
}

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

    public TicTacToe(boolean singlePlayer) {
        this.isSinglePlayer = singlePlayer;
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

        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem backToMenuItem = new JMenuItem("Back to Menu");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> resetGame());
        backToMenuItem.addActionListener(e -> {
            dispose();
            new MenuScreen().setVisible(true);
        });
        exitMenuItem.addActionListener(e -> System.exit(0));

        optionsMenu.add(newGameItem);
        optionsMenu.add(backToMenuItem);
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
        if (isSinglePlayer) {
            turnLabel.setText("Player's Turn");
        } else {
            turnLabel.setText(turn % 2 == 0 ? "Player 1's Turn" : "Player 2's Turn");
        }
        player1ScoreLabel.setText(PLAYER_1_SCORE_TEXT + player1Score);
        player2ScoreLabel.setText(PLAYER_2_SCORE_TEXT + player2Score);
    }

    private void handleButtonClick(int row, int col) {
        if (board[row][col] == ' ') {
            if (isSinglePlayer) {
                makePlayerMove(row, col);
                if (!isBoardFull() && !checkWin(PLAYER_SYMBOL)) {
                    makeComputerMove();
                }
            } else {
                if (turn % 2 == 0) {
                    makePlayer1Move(row, col);
                } else {
                    makePlayer2Move(row, col);
                }
            }
        }
    }

    private void makePlayer1Move(int row, int col) {
        board[row][col] = PLAYER_SYMBOL;
        buttons[row][col].setText(String.valueOf(PLAYER_SYMBOL));
        buttons[row][col].setEnabled(false);

        if (checkWin(PLAYER_SYMBOL)) {
            JOptionPane.showMessageDialog(this, "Player 1 wins!");
            player1Score++;
            lockBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            lockBoard();
        } else {
            turn++;
        }
        updateUI();
    }

    private void makePlayer2Move(int row, int col) {
        board[row][col] = COMPUTER_SYMBOL;
        buttons[row][col].setText(String.valueOf(COMPUTER_SYMBOL));
        buttons[row][col].setEnabled(false);

        if (checkWin(COMPUTER_SYMBOL)) {
            JOptionPane.showMessageDialog(this, "Player 2 wins!");
            player2Score++;
            lockBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            lockBoard();
        } else {
            turn++;
        }
        updateUI();
    }

    private void makePlayerMove(int row, int col) {
        board[row][col] = PLAYER_SYMBOL;
        buttons[row][col].setText(String.valueOf(PLAYER_SYMBOL));
        buttons[row][col].setEnabled(false);

        if (checkWin(PLAYER_SYMBOL)) {
            JOptionPane.showMessageDialog(this, "Player wins!");
            player1Score++;
            lockBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            lockBoard();
        }
        updateUI();
    }

    private void makeComputerMove() {
        int[] move = findBestMove();
        int row = move[0];
        int col = move[1];
        
        board[row][col] = COMPUTER_SYMBOL;
        buttons[row][col].setText(String.valueOf(COMPUTER_SYMBOL));
        buttons[row][col].setEnabled(false);

        if (checkWin(COMPUTER_SYMBOL)) {
            JOptionPane.showMessageDialog(this, "Computer wins!");
            player2Score++;
            lockBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            lockBoard();
        }
        updateUI();
    }

    private int[] findBestMove() {
        int[] move = new int[2];
        
        if (board[1][1] == ' ') {
            move[0] = 1;
            move[1] = 1;
            return move;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = COMPUTER_SYMBOL;
                    if (checkWin(COMPUTER_SYMBOL)) {
                        board[i][j] = ' ';
                        move[0] = i;
                        move[1] = j;
                        return move;
                    }
                    board[i][j] = ' ';
                }
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = PLAYER_SYMBOL;
                    if (checkWin(PLAYER_SYMBOL)) {
                        board[i][j] = ' ';
                        move[0] = i;
                        move[1] = j;
                        return move;
                    }
                    board[i][j] = ' ';
                }
            }
        }

        while (true) {
            int i = random.nextInt(BOARD_SIZE);
            int j = random.nextInt(BOARD_SIZE);
            if (board[i][j] == ' ') {
                move[0] = i;
                move[1] = j;
                return move;
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
            new MenuScreen().setVisible(true);
        });
    }
}
