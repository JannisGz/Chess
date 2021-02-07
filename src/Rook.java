import javax.swing.*;

/**
 * Representation of a rook chess piece. A rook can move any number of tiles both vertically and horizontally, but not
 * diagonally. It can not leap over other pieces, but is involved in a king's castling move. Once the rook has been
 * moved it can not be involved in a castling move.
 */
public class Rook extends ChessPiece {

    public Rook(Player owner) {
        super(owner);
        this.canCastle = true;
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/RookW.png");
        else
            this.icon = new ImageIcon("resources/RookB.png");
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        // current and target tiles have to be on the same axis
        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();
        boolean sameCol = currentCol == targetCol;
        boolean sameRow = currentRow == targetRow;
        boolean sameAxis = sameCol || sameRow;

        // check if there are pieces between the tiles
        boolean noPiecesBetween = true;

        if (sameCol) {
            int greaterIndex = Math.max(currentRow, targetRow);
            int smallerIndex = Math.min(currentRow, targetRow);
            for (int row = smallerIndex + 1; row < greaterIndex; row++) {
                Tile tileInBetween = board.getTile(row, currentCol);
                if (tileInBetween.hasChessPiece()) {
                    noPiecesBetween = false;
                }
            }
        } else if (sameRow) {
            int greaterIndex = Math.max(currentCol, targetCol);
            int smallerIndex = Math.min(currentCol, targetCol);
            for (int col = smallerIndex + 1; col < greaterIndex; col++) {
                Tile tileInBetween = board.getTile(currentRow, col);
                if (tileInBetween.hasChessPiece()) {
                    noPiecesBetween = false;
                }
            }
        }

        return sameAxis && noPiecesBetween;
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
