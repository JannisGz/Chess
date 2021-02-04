import javax.swing.*;

public class King extends ChessPiece{

    public King(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/KingW.png");
        else
            this.icon = new ImageIcon("resources/KingB.png");
    }
}
