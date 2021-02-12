import javax.swing.*;

public abstract class ChessPiece{

    protected Player owner;
    protected ImageIcon icon;
    protected Tile tile;
    protected boolean canCastle;

    public ChessPiece(Player owner) {
        this.owner = owner;
        this.owner.addChessPiece(this);
        this.canCastle = false;
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

    public boolean canCastle() {
        return this.canCastle;
    }

    public abstract String getName();

    @Override
    public abstract ChessPiece clone();
}
