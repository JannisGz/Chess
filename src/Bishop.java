import javax.swing.*;

public class Bishop extends ChessPiece {

    public Bishop(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/BishopW.png");
        else
            this.icon = new ImageIcon("resources/BishopB.png");
    }
}
