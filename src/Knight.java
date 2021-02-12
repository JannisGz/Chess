import javax.swing.*;

public class Knight extends ChessPiece{

    public Knight(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/KnightW.png");
        else
            this.icon = new ImageIcon("resources/KnightB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        // Moves in a L Shape -> 8 possible targets: x+1/y+2, x+1/y-2, x+2/y-1, x+2/y+1, x-1/y+2, x-1/y-2, x-2/y-1, x-2/y+1

        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        return (currentRow + 1 == targetRow && currentCol + 2 == targetCol) ||
                (currentRow + 1 == targetRow && currentCol - 2 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol + 2 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol - 2 == targetCol) ||
                (currentRow + 2 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow + 2 == targetRow && currentCol - 1 == targetCol) ||
                (currentRow - 2 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow - 2 == targetRow && currentCol - 1 == targetCol);
    }

    @Override
    public String getName() {
        return "Knight";
    }

    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Knight(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
