import javax.swing.*;

/**
 * Representation of a queen chess piece. A queen can move horizontally, vertically or diagonally in any direction and
 * as far as possible. However, a queen can not leap over other chess pieces.
 */
public class Queen extends ChessPiece {

    /**
     * Creates a new Queen owned by the given Player.
     *
     * @param owner the player who owns this chess piece
     */
    public Queen(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon(getClass().getResource("/resources/queenW.png"));
        else
            this.icon = new ImageIcon(getClass().getResource("/resources/queenB.png"));
    }

    /**
     * Calculates if the Queens move set allows it to move from its current location (the {@link Tile} it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * A queen can move as many Tiles as they want horizontally, vertically or diagonally. It can not leap over other
     * chess pieces. Therefore, it combines the move set of a {@link Bishop} and a {@link Rook}.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Queen, else: false
     */
    @Override
    public boolean isValidMove(Tile target, Board board) {
        Rook temporaryRook = new Rook(this.getOwner());
        temporaryRook.setTile(this.getTile());
        boolean validRookMove = temporaryRook.isValidMove(target,board);

        Bishop temporaryBishop = new Bishop(this.getOwner());
        temporaryBishop.setTile(this.getTile());
        boolean validBishopMove = temporaryBishop.isValidMove(target, board);

        return validRookMove || validBishopMove;
    }

    /**
     * Returns the name of the chess piece. In this case "Queen".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "Queen";
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this Queen
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Queen(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
