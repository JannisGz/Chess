import javax.swing.*;

/**
 * Representation of a Knight chess piece. Knights can move in L-Shapes in any direction.
 */
public class Knight extends ChessPiece{

    /**
     * Creates a new Knight owned by the given Player.
     *
     * @param owner the player who owns this chess piece
     */
    public Knight(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon(getClass().getResource("/resources/knightW.png"));
        else
            this.icon = new ImageIcon(getClass().getResource("/resources/knightB.png"));
    }

    /**
     * Calculates if the Knights move set allows it to move from its current location (the Tile it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * Knights can move in L Shapes: Either 2 tiles horizontally and 1 tile vertically or 2 tiles vertically and 1 tile
     * horizontally. The Knight is the only chess peace, that can leap over other chess pieces on its way.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Knight, else: false
     */
    @Override
    public boolean isValidMove(Tile target, Board board) {
        if (target == null || board == null) {
            throw new NullPointerException("Arguments for the isValidMove method can not be null.");
        }
        // Moves in L Shapes: 8 possible targets: x+1/y+2, x+1/y-2, x+2/y-1, x+2/y+1, x-1/y+2, x-1/y-2, x-2/y-1, x-2/y+1

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

    /**
     * Returns the name of the chess piece. In this case "Knight".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "Knight";
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this Knight
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Knight(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
