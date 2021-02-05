import javax.swing.*;
import java.awt.*;

public abstract class ChessPiece {

    private Player owner;
    protected ImageIcon icon;
    protected Tile tile;

    public ChessPiece(Player owner) {
        this.owner = owner;
        this.owner.addChessPiece(this);
    }

    public ChessColor getColor() {
        return owner.getColor();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public abstract boolean isValidMove(Tile target, Board board);
}
