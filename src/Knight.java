import javax.swing.*;

public class Knight extends ChessPiece{

    public Knight(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/KnightW.png");
        else
            this.icon = new ImageIcon("resources/KnightB.png");
    }
}
