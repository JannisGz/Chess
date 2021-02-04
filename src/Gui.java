import javax.swing.*;

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

        Board board = new Board(25);
        window.add(board);
        window.pack();
        window.setVisible(true);
    }
}
