import javax.swing.*;

public class Bishop extends ChessPiece {

    public Bishop(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/BishopW.png");
        else
            this.icon = new ImageIcon("resources/BishopB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        // current and target tiles have to be on a diagonal (delta between row and col index have to be identical)
        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        int deltaRow = Math.abs(currentRow - targetRow);
        int deltaCol = Math.abs(currentCol - targetCol);
        int minDelta = Math.min(deltaCol, deltaRow);

        boolean sameDiagonal = deltaCol == deltaRow;

        // check if there are pieces between the tiles
        boolean noPiecesBetween = true;
        // Directions --, -+, ++, +-
            if (targetRow < currentRow && targetCol < currentCol) { // -- 
                for (int delta = 1; delta < minDelta; delta++) {
                    Tile tileInBetween = board.getTile(currentRow - delta, currentCol - delta);
                    if (tileInBetween.hasChessPiece()) {
                        noPiecesBetween = false;
                    }
                }

            } else if (targetRow > currentRow && targetCol < currentCol) { // +-
                for (int delta = 1; delta < minDelta; delta++) {
                    Tile tileInBetween = board.getTile(currentRow + delta, currentCol - delta);
                    if (tileInBetween.hasChessPiece()) {
                        noPiecesBetween = false;
                    }
                }
            } else if (targetRow > currentRow && targetCol > currentCol) { // ++
                for (int delta = 1; delta < minDelta; delta++) {
                    Tile tileInBetween = board.getTile(currentRow + delta, currentCol + delta);
                    if (tileInBetween.hasChessPiece()) {
                        noPiecesBetween = false;
                    }
                }
            } else if (targetRow < currentRow && targetCol > currentCol) { // -+
                for (int delta = 1; delta < minDelta; delta++) {
                    Tile tileInBetween = board.getTile(currentRow - delta, currentCol + delta);
                    if (tileInBetween.hasChessPiece()) {
                        noPiecesBetween = false;
                    }
                }
            }

        return sameDiagonal && noPiecesBetween;
    }
}
