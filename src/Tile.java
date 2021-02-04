import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends JButton {

    private ChessColor color;
    private ChessPiece chessPiece;
    private final int size;
    private final int row;
    private final int col;
    private String tileName; // To Do: Calculate Name of tile (e.g. B6, H7)

    public Tile(ChessColor color, int row, int col, int size) {
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
        this.setBorderPainted(false);
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        this.setIcon(chessPiece.getIcon());
    }

    public void removeChessPiece() {
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
}
