import java.util.ArrayList;

public class Player {

    private final ChessColor color;
    private ArrayList<ChessPiece> chessPieces;
    private King king;

    public Player(ChessColor color) {
        this.color = color;
        chessPieces = new ArrayList<>();

        this.king = new King(this);
        this.chessPieces.add(this.king);
    }

    public ChessColor getColor() {
        return color;
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
