import javax.swing.*;
import java.awt.*;

public abstract class ChessPiece {

    private Player owner;
    protected ImageIcon icon;

    public ChessPiece(Player owner) {
        this.owner = owner;
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
}
