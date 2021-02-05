import javax.swing.*;

public class Rook extends ChessPiece{

    public Rook(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/RookW.png");
        else
            this.icon = new ImageIcon("resources/RookB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        return false;
    }
}
