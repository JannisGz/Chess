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
        return false;
    }
}
