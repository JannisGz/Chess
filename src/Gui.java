import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Gui implements Observer {

    private int TOTAL_WIDTH, TOTAL_HEIGHT = 600;
    private JTextArea gameLog;
    private JLabel activeColorLabel;
    private ImageIcon whiteIcon;
    private ImageIcon blackIcon;
    private Game game;
    private Board board;
    private JButton restartBtn;
    private JPanel contentContainer;

    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public Gui() {
        // Setting up the window
        JFrame window = new JFrame("Chess");
        window.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // Container for both the chess board and the side panel
        contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.X_AXIS));
        contentContainer.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Container for side elements
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(0, 5, 0, 0));

        // Label with Icon and text for the currently active color
        whiteIcon = new ImageIcon("resources/KingW.png");
        blackIcon = new ImageIcon("resources/KingB.png");
        activeColorLabel = new JLabel("White's Turn ", whiteIcon, JLabel.CENTER);
        activeColorLabel.setVerticalTextPosition(JLabel.CENTER);
        activeColorLabel.setHorizontalTextPosition(JLabel.RIGHT);

        // Game Log
        gameLog = new JTextArea(10, 8);
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);

        // Button to reset the board, game and gui
        restartBtn = new JButton("Restart");
        restartBtn.setBackground(Color.RED);
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        sidePanel.add(activeColorLabel);
        sidePanel.add(scrollPane, BorderLayout.CENTER);
        sidePanel.add(restartBtn);

        // Initialize the board and game
        board = new Board(45);
        game = new Game(board);
        game.addObserver(this);
        contentContainer.add(board);
        contentContainer.add(sidePanel);
        window.add(contentContainer);

        window.pack();
        window.setVisible(true);

    }

    private void restartGame() {
        contentContainer.remove(board);
        board = new Board(45);
        game = new Game(board);
        game.addObserver(this);
        gameLog.setText("");
        setActiveColor(ChessColor.WHITE);
        contentContainer.add(board, 0);
        contentContainer.revalidate();
    }

    private void setActiveColor(ChessColor color) {
        if (color == ChessColor.WHITE) {
            activeColorLabel.setIcon(whiteIcon);
            activeColorLabel.setText("White's Turn");
        } else {
            activeColorLabel.setIcon(blackIcon);
            activeColorLabel.setText("Black's Turn");
        }
    }

    private void appendGameLog(String logEntry) {
        gameLog.append(logEntry + "\n");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            appendGameLog((String) arg);
        } else if (arg instanceof ChessColor){
            setActiveColor((ChessColor) arg);
        }
    }
}
