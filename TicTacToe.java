import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
 *
 * @author Muhammad AbuBakr
 *  
 */
class ModernButton extends JButton {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color HOVER_COLOR = new Color(41, 128, 185, 200);
    private static final int ARC = 15;

    public ModernButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setForeground(Color.WHITE);
        setBackground(PRIMARY_COLOR);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(250, 45));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(HOVER_COLOR);
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(PRIMARY_COLOR);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(HOVER_COLOR);
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        
        // Add subtle shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
        
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(getText(), x, y);
        g2.dispose();
    }
}

class ModernPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final int ARC = 20;

    public ModernPanel() {
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        g2.dispose();
    }
}

class PlayOptionsScreen extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public PlayOptionsScreen() {
        setTitle("Play Options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        ModernPanel mainPanel = new ModernPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Select Game Mode");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        ModernButton singlePlayerBtn = new ModernButton("Single Player");
        ModernButton multiPlayerBtn = new ModernButton("Multi Player");
        ModernButton backBtn = new ModernButton("Back to Menu");

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
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    public MenuScreen() {
        setTitle("Tic-Tac-Toe Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        ModernPanel mainPanel = new ModernPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Tic-Tac-Toe");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        ModernButton playBtn = new ModernButton("1. Play");
        ModernButton optionsBtn = new ModernButton("2. Options");
        ModernButton leaderboardBtn = new ModernButton("3. Leaderboard");
        ModernButton aboutBtn = new ModernButton("4. About");
        ModernButton quitBtn = new ModernButton("5. Quit");

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

class GameButton extends JButton {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color HOVER_COLOR = new Color(41, 128, 185, 200);
    private static final int ARC = 15;

    public GameButton() {
        setFont(new Font("Segoe UI", Font.BOLD, 60));
        setForeground(Color.WHITE);
        setBackground(PRIMARY_COLOR);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isEnabled()) {
                    setBackground(HOVER_COLOR);
                    repaint();
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (isEnabled()) {
                    setBackground(PRIMARY_COLOR);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(HOVER_COLOR);
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        
        // Add subtle shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
        
        if (getText() != null && !getText().isEmpty()) {
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(getText(), x, y);
        }
        
        g2.dispose();
    }
}

public class TicTacToe extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color BOARD_COLOR = new Color(52, 73, 94);
    private static final Font GAME_FONT = new Font("Segoe UI", Font.BOLD, 60);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);

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
        setSize(600, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        ModernPanel mainPanel = new ModernPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        getContentPane().add(mainPanel);

        boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE, 5, 5));
        boardPanel.setBackground(BOARD_COLOR);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                GameButton button = new GameButton();
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        ModernPanel controlPanel = new ModernPanel();
        controlPanel.setLayout(new GridLayout(3, 1, 5, 5));
        
        turnLabel = new JLabel("Player's Turn", SwingConstants.CENTER);
        turnLabel.setFont(LABEL_FONT);
        turnLabel.setForeground(TEXT_COLOR);
        
        player1ScoreLabel = new JLabel(PLAYER_1_SCORE_TEXT + player1Score, SwingConstants.CENTER);
        player1ScoreLabel.setFont(LABEL_FONT);
        player1ScoreLabel.setForeground(TEXT_COLOR);
        
        player2ScoreLabel = new JLabel(PLAYER_2_SCORE_TEXT + player2Score, SwingConstants.CENTER);
        player2ScoreLabel.setFont(LABEL_FONT);
        player2ScoreLabel.setForeground(TEXT_COLOR);

        controlPanel.add(turnLabel);
        controlPanel.add(player1ScoreLabel);
        controlPanel.add(player2ScoreLabel);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        JMenuBar menuBar = createStyledMenuBar();
        setJMenuBar(menuBar);
    }

    private JMenuBar createStyledMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);
        menuBar.setBorderPainted(false);

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setFont(MENU_FONT);
        optionsMenu.setForeground(Color.WHITE);

        JMenuItem newGameItem = createMenuItem("New Game");
        JMenuItem backToMenuItem = createMenuItem("Back to Menu");
        JMenuItem exitMenuItem = createMenuItem("Exit");

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

        return menuBar;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(MENU_FONT);
        item.setForeground(TEXT_COLOR);
        return item;
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
