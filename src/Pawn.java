import javax.swing.*;

public class Pawn extends ChessPiece{

    public Pawn(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/PawnW.png");
        else
            this.icon = new ImageIcon("resources/PawnB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        return true;
    }
}
