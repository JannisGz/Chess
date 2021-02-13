import javax.swing.*;

/**
 * Representation of a pawn chess piece. A pawn cannot move backwards. Normally a pawn moves by advancing a single Tile,
 * but on its first move, a pawn has the option of advancing two Tiles. This vertically movement is only possible if
 * there are no other chess pieces located on the involved tiles. Pawns dont capture in the same way they move. Normally
 * a pawn captures diagonally forward one Tile to the left or right.
 *
 * Pawns also have a special move, called 'en passant'. When a pawn moves two Tiles forward and "passes" a pawn of the
 * enemy, avoiding its diagonal capturing move, the enemy pawn has the option to still carry out his capture move, as if
 * the enemies pawn only moved by one Tile forward. The 'en passant' move can only be carried out immediately after the
 * two tile advance of the enemies pawn.
 */
public class Pawn extends ChessPiece {

    /**
     * Creates a new Pawn owned by the given Player.
     *
     * @param owner the player who owns this chess piece
     */
    public Pawn(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/PawnW.png");
        else
            this.icon = new ImageIcon("resources/PawnB.png");
    }

    /**
     * Calculates if the Pawns move set allows it to move from its current location (the {@link Tile} it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations.
     *
     * Pawns can move one or two Tiles forward and capture diagonally forward by one Tile. Pawns can also carry out an
     * 'en passant' move in complex situation. This is NOT checked by this method.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for a Pawn, else: false
     */
    @Override
    public boolean isValidMove(Tile target, Board board) { // Does not capture en passant
        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        int delta_forward = Math.abs(currentRow - targetRow); // How many tiles forward
        int delta_diagonal = Math.abs(currentCol - targetCol); // Offset to the side

        if (delta_forward > 2 || delta_diagonal > 1) { // More than two tiles forward or more than one sideways
            return false;
        }

        // Pawns can only move in one direction depending on color
        if (getColor() == ChessColor.WHITE && targetRow > currentRow) {
            return false; // White Pawns can only move 'up' (decreasing row  index)
        } else if (getColor() == ChessColor.BLACK && targetRow < currentRow) {
            return false; // Black Pawns can only move 'down' (Increasing row index)
        }

        if (targetCol == currentCol) { // Straight move forward

            if (target.hasChessPiece()) {
                return false; // Tile can not be occupied
            }

            // Move can only be one tile forward OR two tiles forward from the baseline
            if (getColor() == ChessColor.WHITE) {
                return delta_forward == 1 || currentRow == 6;
            } else {
                return delta_forward == 1 || currentRow == 1;
            }

        } else { // Diagonal move -> Has to capture a chess piece
            if (delta_diagonal != 1 || delta_forward != 1) {
                return false; // Capture move has to be 1 tile forward and sideways
            }

            // Diagonal move has to capture a piece
            return target.hasChessPiece();
        }
    }

    /**
     * Returns the name of the chess piece. In this case "Pawn".
     *
     * @return a String holding the name of the chess piece.
     */
    @Override
    public String getName() {
        return "Pawn";
    }

    /**
     * Checks if the move is a valid en passant move, from the viewpoint of the pawn.
     *
     * @param target the targeted Tile
     * @param board the board containing the target and the current Tile of this chess piece
     * @return true if the move is a valid en passant move, else false
     */
    public boolean isValidEnPassantMove(Tile target, Board board) {
        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        int delta_forward = Math.abs(currentRow - targetRow); // How many tiles forward
        int delta_diagonal = Math.abs(currentCol - targetCol); // Offset to the side

        return delta_diagonal == 1 && delta_forward == 1;
    }

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this Pawn
     */
    @Override
    public ChessPiece clone() {
        ChessPiece copy = new Pawn(this.owner);
        this.owner.removeChessPiece(copy);
        copy.canCastle = this.canCastle;
        copy.icon = this.icon;

        return copy;
    }
}
