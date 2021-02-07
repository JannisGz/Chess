import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Tile extends JButton {

    private Board board;
    private ChessColor color;
    private ChessPiece chessPiece;
    private final int size;
    private final int row;
    private final int col;
    private String name; // To Do: Calculate Name of tile (e.g. B6, H7)
    private Map<Integer, String> colToName = new HashMap<Integer, String>();
    private Map<Integer, String> rowToName = new HashMap<Integer, String>();

    public Tile(Board board, ChessColor color, int row, int col, int size) {
        this.board = board;
        this.color = color;
        if (this.color == ChessColor.WHITE)
            this.setBackground(Color.WHITE);
        else
            this.setBackground(Color.DARK_GRAY);

        this.chessPiece = null;
        this.row = row;
        this.col = col;
        this.size = size;
        ImageIcon empty_icon = new ImageIcon(new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB));
        this.setIcon(empty_icon);

        this.setMargin(new Insets(0,0,0,0));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(Color.blue, size/10)); // Highlights the tile when clicked
        this.setBorderPainted(false);

        colToName.put(0, "A");
        colToName.put(1, "B");
        colToName.put(2, "C");
        colToName.put(3, "D");
        colToName.put(4, "E");
        colToName.put(5, "F");
        colToName.put(6, "G");
        colToName.put(7, "H");

        rowToName.put(0, "8");
        rowToName.put(1, "7");
        rowToName.put(2, "6");
        rowToName.put(3, "5");
        rowToName.put(4, "4");
        rowToName.put(5, "3");
        rowToName.put(6, "2");
        rowToName.put(7, "1");

        this.name = colToName.get(col) + rowToName.get(row);
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        this.chessPiece.setTile(this);
        this.setIcon(chessPiece.getIcon());
    }

    public void removeChessPiece() {
        this.chessPiece.setTile(null);
        this.chessPiece = null;
        ImageIcon empty_icon = new ImageIcon(new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB));
        this.setIcon(empty_icon);
    }

    public ChessPiece getChessPiece() {
        if (this.hasChessPiece())
            return this.chessPiece;
        else
            throw new NullPointerException("Error: Trying to get a chess piece from an empty tile.");
    }

    public boolean hasChessPiece() {
        if (chessPiece != null)
            return true;
        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void markAsActive() {
        this.setBorder(BorderFactory.createLineBorder(Color.blue, this.size/10));
        this.setBorderPainted(true);
    }

    public void markAsInactive() {
        this.setBorder(BorderFactory.createLineBorder(Color.blue, 0));
        this.setBorderPainted(false);
    }

    public String getName() {
        return name;
    }
}
