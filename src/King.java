import javax.swing.*;

/**
 * Representation of a king chess piece. A king can move one {@link Tile} in any direction. If it has not moved before,
 * a king can cooperate with a rook to carry out a castling move. It has to be the first move for both pieces and no
 * other pieces can be positioned between them. Then, the king and rook move towards each other and the king leaps over
 * the rook. A castling move can not be carried out, while the king is checked.
 *
 * Whenever, a players king is checked, it has to break the check. This can be done in multiple ways, e.g. by moving out
 * of the attackers range, capturing the attacker or moving another piece between attacker and king. If the check can
 * not be solved by any move of the player, he has lost the game.
 *
 */
public class King extends ChessPiece {

    /**
     * Creates a new King owned by the given Player.
     *
     * @param owner the player who owns this chess piece
     */
    public King(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/KingW.png");
        else
            this.icon = new ImageIcon("resources/KingB.png");

        this.canCastle = true;
    }

    /**
     * Calculates if the Kings move set allows it to move from its current location (the {@link Tile} it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * A King can move one Tile in any direction. It can also use a special move (castling), which is NOT checked by
     * this method.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Queen, else: false
     */
    @Override
    public boolean isValidMove(Tile target, Board board) {
        // King can move one tile into every direction -> 8 possible targets:

        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        return (currentRow + 1 == targetRow && currentCol == targetCol) ||
                (currentRow + 1 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow + 1 == targetRow && currentCol - 1 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol == targetCol) ||
                (currentRow - 1 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol - 1 == targetCol) ||
                (currentRow == targetRow && currentCol + 1 == targetCol) ||
                (currentRow == targetRow && currentCol - 1 == targetCol);
    }

    /**
     * Returns the name of the chess piece. In this case "King".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "King";
    }

    /**
     * Checks if the move is a valid castling move, from the viewpoint of the King.
     *
     * @param target the targeted Tile
     * @param board the board containing the target and the current Tile of this chess piece
     * @return true if the move is a valid castling move, else false
     */
    public boolean isValidCastlingMove(Tile target, Board board) {
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        if (this.canCastle()) {
            if (targetRow == 0 && targetCol == 2) { // long castle for black
                boolean rookOnPosition = board.getTile(0, 0).hasChessPiece();
                if (rookOnPosition) {
                    rookOnPosition = board.getTile(0,0).getChessPiece().canCastle();
                }
                boolean knightGone = !board.getTile(0, 1).hasChessPiece();
                boolean bishopGone = !board.getTile(0, 2).hasChessPiece();
                boolean queenGone = !board.getTile(0, 3).hasChessPiece();
                return rookOnPosition && knightGone && bishopGone && queenGone;
            } else if (targetRow == 0 && targetCol == 6) { // short castle for black
                boolean rookOnPosition = board.getTile(0, 7).hasChessPiece();
                if (rookOnPosition) {
                    rookOnPosition = board.getTile(0,7).getChessPiece().canCastle();
                }
                boolean knightGone = !board.getTile(0, 6).hasChessPiece();
                boolean bishopGone = !board.getTile(0, 5).hasChessPiece();
                return rookOnPosition && knightGone && bishopGone;
            } else if (targetRow == 7 && targetCol == 2) { // long castle for white
                boolean rookOnPosition = board.getTile(7, 0).hasChessPiece();
                if (rookOnPosition) {
                    rookOnPosition = board.getTile(7,0).getChessPiece().canCastle();
                }
                boolean knightGone = !board.getTile(7, 1).hasChessPiece();
                boolean bishopGone = !board.getTile(7, 2).hasChessPiece();
                boolean queenGone = !board.getTile(7, 3).hasChessPiece();
                return rookOnPosition && knightGone && bishopGone && queenGone;
            } else if (targetRow == 7 && targetCol == 6) { // short castle for white
                boolean rookOnPosition = board.getTile(7, 7).hasChessPiece();
                if (rookOnPosition) {
                    rookOnPosition = board.getTile(7,7).getChessPiece().canCastle();
                }
                boolean knightGone = !board.getTile(7, 6).hasChessPiece();
                boolean bishopGone = !board.getTile(7, 5).hasChessPiece();
                return rookOnPosition && knightGone && bishopGone;
            }
        }
        return false;
    }

    /**
     * Carries out a castling move. This does NOT check if castling would be valid move in the current situation. Use
     * the isValidCastlingMove() method beforehand to check.
     *
     * @param target the Tile targeted by the player
     * @param board the Board which contains both the target and the tile on which this King is currently located
     */
    public void castle(Tile target, Board board) {
        int targetCol = target.getCol();
        int targetRow = target.getRow();
        Rook involvedRook;

        if (targetRow == 0 && targetCol == 2) { // long castle for black
            involvedRook = (Rook) board.getTile(0, 0).getChessPiece();
            this.getTile().removeChessPiece();
            target.setChessPiece(this);
            board.getTile(0,0).removeChessPiece();
            board.getTile(0,3).setChessPiece(involvedRook);
        } else if (targetRow == 0 && targetCol == 6) { // short castle for black
            involvedRook = (Rook) board.getTile(0, 7).getChessPiece();
            this.getTile().removeChessPiece();
            target.setChessPiece(this);
            board.getTile(0,7).removeChessPiece();
            board.getTile(0,5).setChessPiece(involvedRook);
        } else if (targetRow == 7 && targetCol == 2) { // long castle for white
            involvedRook = (Rook) board.getTile(7, 0).getChessPiece();
            this.getTile().removeChessPiece();
            target.setChessPiece(this);
            board.getTile(7,0).removeChessPiece();
            board.getTile(7,3).setChessPiece(involvedRook);
        } else if (targetRow == 7 && targetCol == 6) { // short castle for white
            involvedRook = (Rook) board.getTile(7, 7).getChessPiece();
            this.getTile().removeChessPiece();
            target.setChessPiece(this);
            board.getTile(7,7).removeChessPiece();
            board.getTile(7,5).setChessPiece(involvedRook);
        }
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this King
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new King(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
