import javax.swing.*;

public class King extends ChessPiece {

    public King(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/KingW.png");
        else
            this.icon = new ImageIcon("resources/KingB.png");

        this.canCastle = true;
    }

    @Override
    public boolean isValidMove(Tile target, Board board) {
        // King can move one tile into every direction -> 8 possible targets:
        // Alternatively a king can perform a castling move, by moving two tiles to the left or right and 'jumping'
        // over a rook. This move can only be performed if there are no other pieces between the rook and the king and
        // both pieces have not been moved yet.

        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        boolean normalMove = (currentRow + 1 == targetRow && currentCol == targetCol) ||
                (currentRow + 1 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow + 1 == targetRow && currentCol - 1 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol == targetCol) ||
                (currentRow - 1 == targetRow && currentCol + 1 == targetCol) ||
                (currentRow - 1 == targetRow && currentCol - 1 == targetCol) ||
                (currentRow == targetRow && currentCol + 1 == targetCol) ||
                (currentRow == targetRow && currentCol - 1 == targetCol);

        return normalMove;
    }

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
}
