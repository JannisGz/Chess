import javax.swing.*;

public class Queen extends ChessPiece {

    public Queen(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/QueenW.png");
        else
            this.icon = new ImageIcon("resources/QueenB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        Rook temporaryRook = new Rook(this.getOwner());
        temporaryRook.setTile(this.getTile());
        boolean validRookMove = temporaryRook.isValidMove(target,board);

        Bishop temporaryBishop = new Bishop(this.getOwner());
        temporaryBishop.setTile(this.getTile());
        boolean validBishopMove = temporaryBishop.isValidMove(target, board);

        temporaryBishop = null;
        temporaryBishop = null; // Ready for garbage collection

        return validRookMove || validBishopMove;
    }

    @Override
    public String getName() {
        return "Queen";
    }

    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Queen(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
