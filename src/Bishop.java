import javax.swing.*;

public class Bishop extends ChessPiece {

    /**
     * Creates a new Bishop owned by the given Player.
     *
     * @param owner the player who owns this chess piece
     */
    public Bishop(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon(getClass().getResource("/resources/bishopW.png"));
        else
            this.icon = new ImageIcon(getClass().getResource("/resources/bishopB.png"));
    }

    /**
     * Calculates if the Bishops move set allows it to move from its current location (the Tile it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * Bishops can only move diagonally.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Bishop, else: false
     */
    @Override
    public boolean isValidMove(Tile target, Board board) {
        if (target == null || board == null) {
            throw new NullPointerException("Arguments for the isValidMove method can not be null.");
        }
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
        // Directions -- (Up and to the left) , -+ (Up and to the right), ++, +-
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

    /**
     * Returns the name of the chess piece. In this case "Bishop".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "Bishop";
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this Bishop
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Bishop(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
