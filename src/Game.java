public class Game {

    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private Player currentPlayer;
    private Phase currentPhase;
    private ChessPiece chosenPiece;
    private Tile chosenTile;

    enum Phase {
        Choosing,
        Moving
    }


    public Game(Board board) {
        this.board = board;
        this.board.addObserver(this);

        playerWhite = new Player(ChessColor.WHITE);
        playerBlack = new Player(ChessColor.BLACK);

        Rook rook1 = new Rook(playerBlack);
        board.getTile(0, 0).setChessPiece(rook1);
        Knight knight1 = new Knight(playerBlack);
        board.getTile(0, 1).setChessPiece(knight1);
        Bishop bishop1 = new Bishop(playerBlack);
        board.getTile(0, 2).setChessPiece(bishop1);
        Queen queen = new Queen(playerBlack);
        board.getTile(0, 3).setChessPiece(queen);
        King king = playerBlack.getKing();
        board.getTile(0, 4).setChessPiece(king);
        Bishop bishop2 = new Bishop(playerBlack);
        board.getTile(0, 5).setChessPiece(bishop2);
        Knight knight2 = new Knight(playerBlack);
        board.getTile(0, 6).setChessPiece(knight2);
        Rook rook2 = new Rook(playerBlack);
        board.getTile(0, 7).setChessPiece(rook2);
        Pawn pawn1 = new Pawn(playerBlack);
        board.getTile(1, 0).setChessPiece(pawn1);
        Pawn pawn2 = new Pawn(playerBlack);
        board.getTile(1, 1).setChessPiece(pawn2);
        Pawn pawn3 = new Pawn(playerBlack);
        board.getTile(1, 2).setChessPiece(pawn3);
        Pawn pawn4 = new Pawn(playerBlack);
        board.getTile(1, 3).setChessPiece(pawn4);
        Pawn pawn5 = new Pawn(playerBlack);
        board.getTile(1, 4).setChessPiece(pawn5);
        Pawn pawn6 = new Pawn(playerBlack);
        board.getTile(1, 5).setChessPiece(pawn6);
        Pawn pawn7 = new Pawn(playerBlack);
        board.getTile(1, 6).setChessPiece(pawn7);
        Pawn pawn8 = new Pawn(playerBlack);
        board.getTile(1, 7).setChessPiece(pawn8);

        rook1 = new Rook(playerWhite);
        board.getTile(7, 0).setChessPiece(rook1);
        knight1 = new Knight(playerWhite);
        board.getTile(7, 1).setChessPiece(knight1);
        bishop1 = new Bishop(playerWhite);
        board.getTile(7, 2).setChessPiece(bishop1);
        queen = new Queen(playerWhite);
        board.getTile(7, 3).setChessPiece(queen);
        king = playerWhite.getKing();
        board.getTile(7, 4).setChessPiece(king);
        bishop2 = new Bishop(playerWhite);
        board.getTile(7, 5).setChessPiece(bishop2);
        knight2 = new Knight(playerWhite);
        board.getTile(7, 6).setChessPiece(knight2);
        rook2 = new Rook(playerWhite);
        board.getTile(7, 7).setChessPiece(rook2);
        pawn1 = new Pawn(playerWhite);
        board.getTile(6, 0).setChessPiece(pawn1);
        pawn2 = new Pawn(playerWhite);
        board.getTile(6, 1).setChessPiece(pawn2);
        pawn3 = new Pawn(playerWhite);
        board.getTile(6, 2).setChessPiece(pawn3);
        pawn4 = new Pawn(playerWhite);
        board.getTile(6, 3).setChessPiece(pawn4);
        pawn5 = new Pawn(playerWhite);
        board.getTile(6, 4).setChessPiece(pawn5);
        pawn6 = new Pawn(playerWhite);
        board.getTile(6, 5).setChessPiece(pawn6);
        pawn7 = new Pawn(playerWhite);
        board.getTile(6, 6).setChessPiece(pawn7);
        pawn8 = new Pawn(playerWhite);
        board.getTile(6, 7).setChessPiece(pawn8);

        currentPlayer = playerWhite;
        currentPhase = Phase.Choosing;
    }

    public void update(Tile clickedTile) {

        switch (currentPhase) {
            case Choosing: // A Tile with a piece of the currently active player has to be chosen

                // Check if the chosen tile is a valid choice for the active player
                if (!clickedTile.hasChessPiece()) {
                    // The Tile does not contain a chess piece
                    break;
                }
                this.chosenPiece = clickedTile.getChessPiece();

                if (this.chosenPiece.getColor() != this.currentPlayer.getColor()) {
                    // The Tile contains a chess piece, that does NOT belong to the active player
                    break;
                } else {
                    // The chosen Tile is a valid option -> Entering Moving phase
                    this.chosenTile = clickedTile;
                    this.chosenTile.markAsActive();
                    this.currentPhase = Phase.Moving;
                }

                break;

            case Moving:

                if (this.chosenTile == clickedTile) {
                    // The same Tile was chosen again -> Reset to choosing phase
                    this.resetToChoosingPhase(clickedTile);
                } else if (clickedTile.hasChessPiece()) {
                    // There is a chess piece on the targeted tile
                    ChessPiece targetedPiece = clickedTile.getChessPiece();
                    if (targetedPiece.getColor() == chosenPiece.getColor()) {
                        // Targeting a ChessPiece that belongs to the same player -> Reset to choosing phase
                        this.resetToChoosingPhase(clickedTile);
                    } else {
                        // Check if chosen Piece can eliminate the targeted piece
                        if (this.chosenPiece.isValidMove(clickedTile, this.board)) {
                            this.chosenTile.removeChessPiece(); // Remove from original tile
                            targetedPiece.setTile(null); // Remove currently occupying chess piece
                            clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                            togglePlayer();
                        }
                        this.resetToChoosingPhase(clickedTile);
                        break;
                    }
                } else {
                    // Empty tile -> Check if chosen piece can move to the targeted tile
                    if (this.chosenPiece.isValidMove(clickedTile, this.board)) {
                        this.chosenTile.removeChessPiece(); // Remove from original tile
                        clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                        togglePlayer();
                    }
                    this.resetToChoosingPhase(clickedTile);
                    break;
                }
                break;
            default:
                throw new IllegalStateException("Error: Game is in a unknown Phase.");
        }
    }

    private void resetToChoosingPhase(Tile clickedTile) {
        this.chosenTile.markAsInactive();
        clickedTile.markAsInactive();
        this.currentPhase = Phase.Choosing;
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == playerWhite) ? playerBlack : playerWhite;
    }
}
