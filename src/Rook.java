import javax.swing.*;

/**
 * Representation of a rook chess piece. A rook can move any number of tiles both vertically and horizontally, but not
 * diagonally. It can not leap over other pieces, but is involved in a king's castling move. Once the rook has been
 * moved it can not be involved in a castling move.
 */
public class Rook extends ChessPiece {

    /**
     * Creates a new Rook owned by the given Player. Rooks are able to castle, if they have not been moved before.
     *
     * @param owner the player who owns this chess piece
     */
    public Rook(Player owner) {
        super(owner);
        this.canCastle = true;
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon(getClass().getResource("/resources/rookW.png"));
        else
            this.icon = new ImageIcon(getClass().getResource("/resources/rookB.png"));
    }

    /**
     * Calculates if the Rooks move set allows it to move from its current location (the Tile it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * Rooks can either move horizontally or vertically, but not diagonally. They are involved in castling moves, which
     * are NOT checked by this method.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Rook, else: false
     */
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

    /**
     * Returns the name of the chess piece. In this case "Rook".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "Rook";
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this Rook
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Rook(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
