import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a tile on a chess board.
 */
public class Tile extends JButton {

    private Board board;
    private final ChessColor color;
    private ChessPiece chessPiece;
    private final int size;
    private final int row;
    private final int col;
    private final String name;

    /**
     * Creates a new Tile. Tiles are components of a (chess-) {@link Board}. Every Tile has a fixed color: Either black
     * or white. Every tile has a fixed location on the board, which is specified by a row and a column argument. Both
     * are integers that start at 0.
     * Tiles are also square buttons and act as the main interaction method of players with the chess board. The size
     * argument specifies how big the Tile is, when displayed.
     * <p>
     * One {@link ChessPiece} can be placed on every Tile. The corresponding icon will then be displayed on the Tile.
     *
     * @param board the board this tile belongs to
     * @param color the color of the tile
     * @param row   the row this tile is located in on the board
     * @param col   the column this tile is located in on the board
     * @param size  the size of this Tile when it is displayed in pixel
     */
    public Tile(Board board, ChessColor color, int row, int col, int size) {
        if (board == null) {
            throw new NullPointerException("Arguments for the Tile Constructor can not be null.");
        } else if (row < 0 || col < 0 || row > 7 || col > 7 || size < 0) {
            throw new IllegalArgumentException("Integer arguments for the Tile Constructor have to be between 0 and 7.");
        }
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

        this.setMargin(new Insets(0, 0, 0, 0));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(Color.blue, size / 10)); // Blue highlight when clicked
        this.setBorderPainted(false);

        Map<Integer, String> colToName = new HashMap<>();
        colToName.put(0, "A");
        colToName.put(1, "B");
        colToName.put(2, "C");
        colToName.put(3, "D");
        colToName.put(4, "E");
        colToName.put(5, "F");
        colToName.put(6, "G");
        colToName.put(7, "H");

        Map<Integer, String> rowToName = new HashMap<>();
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

    /**
     * Returns the {@link ChessColor} of the Tile. This can be either BLACK or WHITE.
     *
     * @return the color of the Tile
     */
    public ChessColor getColor() {
        return color;
    }

    /**
     * Places a {@link ChessPiece} on the Tile. The Icon of the ChessPiece will be displayed on the Tile.
     *
     * @param chessPiece the ChessPiece that will be placed on the Tile
     */
    public void setChessPiece(ChessPiece chessPiece) {
        if (chessPiece == null) {
            throw new NullPointerException("Arguments for the setChessPiece() method must not ne null.");
        }
        this.chessPiece = chessPiece;
        this.chessPiece.setTile(this);
        this.setIcon(chessPiece.getIcon());
    }

    /**
     * Removes the {@link ChessPiece} from this Tile. This will also remove the image of the ChessPiece from the Tile.
     */
    public void removeChessPiece() {
        if (hasChessPiece()) {
            this.chessPiece.setTile(null);
        }
        this.chessPiece = null;
        ImageIcon empty_icon = new ImageIcon(new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB));
        this.setIcon(empty_icon);
    }

    /**
     * Returns the {@link ChessPiece} which is currently placed on this tile. This method will throw a
     * NullPointerException if there is no ChessPiece on the Tile.
     *
     * @return the ChessPiece currently placed on this Tile
     */
    public ChessPiece getChessPiece() {
        if (this.hasChessPiece())
            return this.chessPiece;
        else
            throw new NullPointerException("Error: Trying to get a chess piece from an empty tile.");
    }

    /**
     * Returns whether or not there is currently a {@link ChessPiece} on this Tile.
     *
     * @return true if there is a ChessPiece on this Tile
     */
    public boolean hasChessPiece() {
        return chessPiece != null;
    }

    /**
     * Returns the row number of this Tile.
     *
     * @return the row number of this Tile
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column number of this Tile.
     *
     * @return the column number of this Tile
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the board of which this Tile is a part of.
     *
     * @return the board this Tile belongs to.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Changes the board which this Tile belongs to, to the desired value.
     *
     * @param board the board the Tile will belong to
     */
    public void setBoard(Board board) {
        if (board == null) {
            throw new NullPointerException("Arguments for the setBoard() method must not be null.");
        }
        this.board = board;
    }

    /**
     * Marks the Tile as inactive by adding a blue 10 px wide border on each side.
     */
    public void markAsActive() {
        this.setBorder(BorderFactory.createLineBorder(Color.blue, this.size / 10));
        this.setBorderPainted(true);
    }

    /**
     * Marks the Tile as inactive by removing its border.
     */
    public void markAsInactive() {
        this.setBorder(BorderFactory.createLineBorder(Color.blue, 0));
        this.setBorderPainted(false);
    }

    /**
     * Returns the name of the tile as a string. This is done by translating the row and column index of the Tile into
     * the more common form of a letter and a number, e.g. 'A6' or 'G7'.
     *
     * @return the name of the tile
     */
    @Override
    public String getName() {
        return name;
    }
}
