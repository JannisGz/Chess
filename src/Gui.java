import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Gui {

    private int TOTAL_WIDTH, TOTAL_HEIGHT = 400;

    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public Gui() {

        JFrame window = new JFrame("Chess");
        window.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setBorder(new LineBorder(Color.BLACK));
        window.add(board);

        JButton[][] squares = new JButton[8][8];
        for (int rowNum = 0; rowNum < squares.length; rowNum++) {
            for (int colNum = 0; colNum < squares[rowNum].length; colNum++) {
                ImageIcon icon = new ImageIcon(new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB));
                JButton b = new JButton(icon);
                b.setMargin(new Insets(0,0,0,0));
                b.setOpaque(true);
                b.setBorderPainted(false);
                if ((rowNum % 2 == 0 && colNum % 2 == 0) || (rowNum % 2 == 1 && colNum % 2 == 1)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                squares[colNum][rowNum] = b;
                board.add(b);
            }
        }

        window.pack();
        window.setVisible(true);

    }
}
