public class Game {

    private final Board board;
    private final Player playerWhite;
    private final Player playerBlack;
    private Player currentPlayer;
    private Phase currentPhase;
    private ChessPiece chosenPiece;
    private Tile chosenTile;
    private ChessPiece lastMovedPiece;
    private Tile lastSourceTile;
    private Tile lastTargetTile;
    private int moveNum;

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

        this.currentPlayer = playerWhite;
        this.currentPhase = Phase.Choosing;
        this.moveNum = 1;
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
                            if (moveLeavesKingExposed(chosenTile, clickedTile, false, false)) {
                                this.resetToChoosingPhase(clickedTile);
                            } else {
                                this.chosenTile.removeChessPiece(); // Remove from original tile
                                targetedPiece.setTile(null); // Remove currently occupying chess piece
                                clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                                this.chosenPiece.canCastle = false;
                                setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                                togglePlayer();
                            }
                        }
                        this.resetToChoosingPhase(clickedTile);
                        break;
                    }
                } else {
                    // Empty tile -> Check if chosen piece can move to the targeted tile
                    if (this.chosenPiece.isValidMove(clickedTile, this.board)) {
                        if (moveLeavesKingExposed(chosenTile, clickedTile, false, false)) {
                            this.resetToChoosingPhase(clickedTile);
                        } else {
                            this.chosenTile.removeChessPiece(); // Remove from original tile
                            clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                            this.chosenPiece.canCastle = false;
                            if (this.chosenPiece instanceof Pawn && Math.abs(clickedTile.getRow() - this.chosenTile.getRow()) == 2) {
                                this.lastMovedPiece = this.chosenPiece;
                            }
                            setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                            togglePlayer();
                        }
                    } else if (this.chosenPiece instanceof King) {
                        if (((King) this.chosenPiece).isValidCastlingMove(clickedTile, this.board)) {

                            if (moveLeavesKingExposed(chosenTile, clickedTile, true, false)) {
                                this.resetToChoosingPhase(clickedTile);
                            } else {
                                ((King) this.chosenPiece).castle(clickedTile, this.board);
                                setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                                togglePlayer();
                            }
                        }
                    } else if (this.chosenPiece instanceof Pawn && isEnPassantPossible(clickedTile, this.chosenPiece)) {
                        if (((Pawn) this.chosenPiece).isValidEnPassantMove(clickedTile, this.board)) {
                            if (moveLeavesKingExposed(chosenTile, clickedTile, false, true)) {
                                this.resetToChoosingPhase(clickedTile);
                            } else {
                                this.chosenTile.removeChessPiece(); // Remove from original tile
                                clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                                this.lastMovedPiece.getTile().removeChessPiece();
                                setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                                togglePlayer();
                            }
                        }
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
        System.out.println("Move #" + moveNum + ": " + currentPlayer.getColor() + " moved a " + this.lastMovedPiece.getName() + " from " + this.lastSourceTile.getName() + " to " + this.lastTargetTile.getName());
        if (isChecked(playerWhite, board)) {
            System.out.println("  White is checked.");
        }
        if (isChecked(playerBlack, board)) {
            System.out.println("  Black is checked.");
        }
        currentPlayer = (currentPlayer == playerWhite) ? playerBlack : playerWhite;
        moveNum++;
    }

    private boolean isEnPassantPossible(Tile target, ChessPiece piece) {
        if (!(piece instanceof Pawn) || !(this.lastMovedPiece instanceof Pawn)) {
            return false;
        } else {
            int row1 = this.lastSourceTile.getRow();
            int row2 = target.getRow();
            int row3 = this.lastTargetTile.getRow();

            return (row1 < row2 && row2 < row3) || (row3 < row2 && row2 < row1);
        }
    }

    private void setLastMove(Tile source, Tile target, ChessPiece movedPiece) {
        this.lastSourceTile = source;
        this.lastTargetTile = target;
        this.lastMovedPiece = movedPiece;
    }

    private boolean isChecked(Player player, Board board) {

        // Loop through all enemy chess pieces and check if the check the players king
        Tile [][] tiles = board.getTiles();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                if (!tiles[row][col].hasChessPiece()) {
                    continue; // Skip this tile if it has no chess piece
                }

                ChessPiece potentialAttacker = tiles[row][col].getChessPiece();

                if (potentialAttacker.getOwner() == player) {
                    continue; // Skip this piece if it does not belong to the enemy player
                }

                King playerKing = player.getKing();

                if (potentialAttacker.isValidMove(playerKing.getTile(), board)) {
                    return true; // Check if enemy piece could capture the players king
                }

            }
        }

        return false;
    }

    private boolean moveLeavesKingExposed(Tile source, Tile target, boolean castling, boolean enPassant) {
        // Copy board, carry out the move and check if the players King is checked

        Board boardCopy = copyBoard();

        Player copyWhite = boardCopy.getPlayerWhite();
        Player copyBlack = boardCopy.getPlayerBlack();
        Player currentPlayer;

        if (this.currentPlayer.getColor() == ChessColor.WHITE) {
            currentPlayer = copyWhite;
        } else {
            currentPlayer = copyBlack;
        }

        Tile chosenTile = boardCopy.getTile(source.getRow(), source.getCol());
        ChessPiece chosenPiece = chosenTile.getChessPiece();
        Tile targetedTile = boardCopy.getTile(target.getRow(), target.getCol());

        if (castling) { // castling move
            ((King) chosenPiece).castle(targetedTile, boardCopy);
        } else if (enPassant) { // en passant move
            chosenTile.removeChessPiece(); // Remove from original tile
            targetedTile.setChessPiece(chosenPiece); // Place on new tile

            Tile enPassantTile = boardCopy.getTile(lastMovedPiece.getTile().getRow(), lastMovedPiece.getTile().getCol());
            enPassantTile.removeChessPiece();
        } else { // normal move
            chosenTile.removeChessPiece(); // Remove from original tile
            targetedTile.setChessPiece(chosenPiece); // Place on new tile
        }

        boolean isChecked = isChecked(currentPlayer, boardCopy);
        if (isChecked) {
            System.out.println("Can not carry out this move because it leaves the current players king exposed.");
        }
        return isChecked;
    }

    private Board copyBoard() {
        Board copy = new Board(board.getTileSize());
        Player copyWhite = new Player(ChessColor.WHITE);
        Player copyBlack = new Player(ChessColor.BLACK);

        Tile [][] tiles = board.getTiles();
        Tile [][] copiedTiles = copy.getTiles();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].hasChessPiece()) {
                    ChessPiece piece = tiles[row][col].getChessPiece();
                    ChessPiece clonedPiece = piece.clone();
                    if (piece.getColor() == ChessColor.WHITE) {
                        clonedPiece.setOwner(copyWhite);
                        if (piece instanceof King) {
                            copyWhite.setKing((King) clonedPiece);
                        }
                    } else {
                        clonedPiece.setOwner(copyBlack);
                        if (piece instanceof King) {
                            copyBlack.setKing((King) clonedPiece);
                        }
                    }
                    copiedTiles[row][col].setChessPiece(clonedPiece);
                    clonedPiece.setTile(copiedTiles[row][col]);
                }
            }
        }

        copy.setPlayerBlack(copyBlack);
        copy.setPlayerWhite(copyWhite);
        return copy;
    }
}
