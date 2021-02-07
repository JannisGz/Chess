import javax.swing.*;

public class Pawn extends ChessPiece {

    public Pawn(Player owner) {
        super(owner);
        if (owner.getColor() == ChessColor.WHITE)
            this.icon = new ImageIcon("resources/PawnW.png");
        else
            this.icon = new ImageIcon("resources/PawnB.png");
    }

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

    @Override
    public String getName() {
        return "Pawn";
    }

    public boolean isValidEnPassantMove(Tile target, Board board) {
        int currentCol = this.getTile().getCol();
        int currentRow = this.getTile().getRow();
        int targetCol = target.getCol();
        int targetRow = target.getRow();

        int delta_forward = Math.abs(currentRow - targetRow); // How many tiles forward
        int delta_diagonal = Math.abs(currentCol - targetCol); // Offset to the side

        return delta_diagonal == 1 && delta_forward == 1;
    }
}
