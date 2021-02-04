import javax.swing.*;

public abstract class ChessPiece {

    private Player owner;

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

    public abstract ImageIcon getIcon();
}
