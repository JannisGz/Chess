import java.util.ArrayList;

public class Player {

    private ChessColor color;
    private ArrayList<ChessPiece> chessPieces;
    private King king;
    private boolean isChecked;
    private boolean isCheckmate;

    public Player(ChessColor color) {
        this.color = color;
        chessPieces = new ArrayList<ChessPiece>();

        this.king = new King(this);
        this.chessPieces.add(this.king);
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public ArrayList<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    public void setChessPieces(ArrayList<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }

    public void addChessPiece(ChessPiece chessPiece) {
        this.chessPieces.add(chessPiece);
    }

    public void removeChessPiece(ChessPiece chessPiece) {
        this.chessPieces.remove(chessPiece);
    }

    public King getKing() {
        return this.king;
    }

    public void setKing(King king) {
        this.king = king;
    }
}
