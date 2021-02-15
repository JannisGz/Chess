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
                            if (moveLeavesKingExposed(chosenTile, clickedTile, false, false, currentPlayer)) {
                                System.out.println("Can not carry out this move because it leaves the current players king exposed.");
                                this.resetToChoosingPhase(clickedTile);
                            } else {
                                this.chosenTile.removeChessPiece(); // Remove from original tile
                                targetedPiece.setTile(null); // Remove currently occupying chess piece
                                clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                                this.chosenPiece.canCastle = false;
                                if (chosenPiece instanceof Pawn) {
                                    chosenPiece = transformPawn((Pawn) chosenPiece);
                                }
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
                        if (moveLeavesKingExposed(chosenTile, clickedTile, false, false, currentPlayer)) {
                            System.out.println("Can not carry out this move because it leaves the current players king exposed.");
                            this.resetToChoosingPhase(clickedTile);
                        } else {
                            this.chosenTile.removeChessPiece(); // Remove from original tile
                            clickedTile.setChessPiece(this.chosenPiece); // Place on new tile
                            this.chosenPiece.canCastle = false;
                            if (chosenPiece instanceof Pawn) {
                                chosenPiece = transformPawn((Pawn) chosenPiece);
                            }
                            if (this.chosenPiece instanceof Pawn && Math.abs(clickedTile.getRow() - this.chosenTile.getRow()) == 2) {
                                this.lastMovedPiece = this.chosenPiece;
                            }
                            setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                            togglePlayer();
                        }
                    } else if (this.chosenPiece instanceof King) {
                        if (((King) this.chosenPiece).isValidCastlingMove(clickedTile, this.board)) {

                            if (moveLeavesKingExposed(chosenTile, clickedTile, true, false, currentPlayer)) {
                                System.out.println("Can not carry out this move because it leaves the current players king exposed.");
                                this.resetToChoosingPhase(clickedTile);
                            } else {
                                ((King) this.chosenPiece).castle(clickedTile, this.board);
                                setLastMove(this.chosenTile, clickedTile, this.chosenPiece);
                                togglePlayer();
                            }
                        }
                    } else if (this.chosenPiece instanceof Pawn && isEnPassantPossible(clickedTile, this.chosenPiece)) {
                        if (((Pawn) this.chosenPiece).isValidEnPassantMove(clickedTile, this.board)) {
                            if (moveLeavesKingExposed(chosenTile, clickedTile, false, true, currentPlayer)) {
                                System.out.println("Can not carry out this move because it leaves the current players king exposed.");
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
        if (isCheckMate(playerWhite, board)) {
            System.out.println("  White is checkmate. Black wins.");
        } else if (isChecked(playerWhite, board)) {
            System.out.println("  White is checked.");
        }
        if (isCheckMate(playerBlack, board)) {
            System.out.println("  Black is checkmate. White wins.");
        } else if (isChecked(playerBlack, board)) {
            System.out.println("  Black is checked.");
        }
        currentPlayer = (currentPlayer == playerWhite) ? playerBlack : playerWhite;
        moveNum++;

        if (isRemis(currentPlayer, board)) {
            System.out.println("  No possible moves left for unchecked " + currentPlayer.getColor() + ". Remis");
        }
    }

    /**
     * Tests if given move involving the specified {@link ChessPiece} and its targeted {@link Tile}, is a valid
     * 'en Passant' move. This means the given ChessPiece has to be a {@link Pawn}, the previous move of the game has to
     * be a two-tile forward move carried out by an opposing Pawn. This method does NOT check if this move leaves the
     * {@link Player}s {@link King} checked.
     *
     * @param target the targeted Tile of the move
     * @param piece the involved ChessPiece
     * @return true if this is a valid en passant move
     */
    private boolean isEnPassantPossible(Tile target, ChessPiece piece) {
        if (!(piece instanceof Pawn) || !(this.lastMovedPiece instanceof Pawn)) {
            return false; // Both the currently moved ChessPiece and the last one have to be Pawns
        } else {
            int row1 = this.lastSourceTile.getRow();
            int row2 = target.getRow();
            int row3 = this.lastTargetTile.getRow();

            // The currently moved Pawn has to targeted the Tile between the Source and Target Tile of the previous move
            return (row1 < row2 && row2 < row3) || (row3 < row2 && row2 < row1);
        }
    }

    /**
     * Saves the combination of source and target {@link Tile} and involved {@link ChessPiece} as the latest move of
     * this {@link Game}.
     *
     * @param source the Tile from which the ChessPiece started its move
     * @param target the Tile on which the ChessPiece ended its move
     * @param movedPiece the ChessPiece that moved from source to target
     */
    private void setLastMove(Tile source, Tile target, ChessPiece movedPiece) {
        this.lastSourceTile = source;
        this.lastTargetTile = target;
        this.lastMovedPiece = movedPiece;
    }

    /**
     * Tests if the specified {@link Player} is checked by his opponent on the given {@link Board}.
     *
     * @param player the player who will be tested for being checked
     * @param board the board containing the Players {@link ChessPiece}s
     * @return true if the Player is checked
     */
    private boolean isChecked(Player player, Board board) {

        // Loop through all opposing chess pieces and test if they check the players king
        Tile[][] tiles = board.getTiles();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                if (!tiles[row][col].hasChessPiece()) {
                    continue; // Skip this tile, it has no ChessPiece
                }

                ChessPiece potentialAttacker = tiles[row][col].getChessPiece();

                if (potentialAttacker.getOwner() == player) {
                    continue; // Skip this piece, it does not belong to the opponent
                }

                King playerKing = player.getKing();

                // Test if the ChessPiece could capture the players King with its next move
                if (potentialAttacker.isValidMove(playerKing.getTile(), board)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Checks if moving a {@link  ChessPiece} from the given source {@link Tile} to the given targeted Tile leaves the
     * owning {@link Player}s {@link King} checked. If this is the case the move is considered illegal or invalid.
     *
     * @param source the Tile containing the ChessPiece which will be moved
     * @param target the targeted Tile of the move
     * @param castling whether the move is a castling move
     * @param enPassant whether the move is an en passant move
     * @param player the player who owns the ChessPiece on the source Tile
     * @return true if the move leaves the players king checked (illegal move)
     */
    private boolean moveLeavesKingExposed(Tile source, Tile target, boolean castling, boolean enPassant, Player player) {

        // Copy the board and determine the involved Tiles and Players on it -> The 'real' board will NOT be altered.
        Board boardCopy = copyBoard();

        Player copyWhite = boardCopy.getPlayerWhite();
        Player copyBlack = boardCopy.getPlayerBlack();
        Player currentPlayer;

        if (player.getColor() == ChessColor.WHITE) {
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
            if (chosenPiece instanceof Pawn) {
                transformPawn((Pawn) chosenPiece); // Transform Pawn into a Queen if it reaches the other side
            }
        }

        return isChecked(currentPlayer, boardCopy);
    }

    /**
     * Creates a {@link Board} that contains the same {@link ChessPiece}s with the same {@link ChessColor}s, but two new
     * {@link Player}s. Using this copy, moves can be tested without altering the real Board or the real ChessPieces.
     *
     * @return a copy of this games Board.
     */
    private Board copyBoard() {
        // Create a new Board and players without any ChessPieces
        Board copy = new Board(board.getTileSize());
        Player copyWhite = new Player(ChessColor.WHITE);
        Player copyBlack = new Player(ChessColor.BLACK);

        // Loop through the original board and the new copy and clone the ChessPieces found on the original
        Tile[][] tiles = board.getTiles();
        Tile[][] copiedTiles = copy.getTiles();
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

    /**
     * EValuates if the given {@link Player} is checkmate. This means that the Players' {@link King} is currently
     * checked and none of his {@link ChessPiece}s are able to move in a way, that breaks this check. This means the
     * Player loses the match.
     *
     * @param player the player who is investigated
     * @param board the Board that is investigated
     * @return true if the given Player is checkmate
     */
    private boolean isCheckMate(Player player, Board board) {
        if (!isChecked(player, board)) { // Can not be check mate if not checked
            return false;
        }

        return !canMove(player, board);
    }

    /**
     * Checks for Remis. Remis means the match ends in a draw, because the currently active {@link Player} is not
     * currently checked, but can not move any of his {@link ChessPiece}s.
     *
     * @param player the player who is investigated for Remis
     * @param board the Board that is investigated
     * @return true if there are no valid moves left for the specified player
     */
    private boolean isRemis(Player player, Board board) {
        if (isChecked(player, board)) { // Can not be Remis if checked
            return false;
        }

        return !canMove(player, board);
    }

    /**
     * Iterates over all {@link Tile}s and all corresponding {@link ChessPiece}s on the Board to test if the specified
     * {@link Player} has any valid moves left. Any legal move that results in the investigated Players {@link King} not
     * being checked is considered a valid option. All normal moves, but also special moves like 'en passant' or
     * 'castling' are tested by this method.
     *
     * @param player the player who's move options are investigated
     * @param board the board that is investigated
     * @return true if there a valid moves left. False if no valid move was found.
     */
    private boolean canMove(Player player, Board board) {

        // Loop through all Tiles to find all chess pieces
        Tile[][] tiles = board.getTiles();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                Tile currentTile = tiles[row][col];

                // Skip if there is no chess piece on the tile
                if (!currentTile.hasChessPiece()) {
                    continue;
                }

                ChessPiece currentPiece = currentTile.getChessPiece();

                // Skip if the chess piece belongs to the enemy
                if (currentPiece.getColor() != player.getColor()) {
                    continue;
                }

                // Loop through all Tiles to check if they are valid targets for a move
                for (int row2 = 0; row2 < tiles.length; row2++) {
                    for (int col2 = 0; col2 < tiles[row2].length; col2++) {

                        // Skip if source and target tile are identical
                        if (row2 == row && col2 == col) {
                            continue;
                        }

                        Tile targetTile = tiles[row2][col2];

                        // Skip if the targeted Tile is occupied by a chess piece of the same player
                        if (targetTile.hasChessPiece()) {
                            if (targetTile.getChessPiece().getOwner() == player) {
                                continue;
                            }
                        }

                        // Check if the currently investigated Piece can use a normal move to reach the targeted Tile
                        boolean validMove = currentPiece.isValidMove(targetTile, board);

                        // Check if the currently investigated Piece can use 'en Passant' to reach the targeted Tile
                        boolean enPassant = false;
                        if (currentPiece instanceof Pawn && isEnPassantPossible(targetTile, currentPiece)) {
                            if (((Pawn) currentPiece).isValidEnPassantMove(targetTile, board)) {
                                enPassant = true;
                            }
                        }

                        // Check if the currently investigated Piece can 'castle' to reach the targeted Tile
                        boolean castling = false;
                        if (currentPiece instanceof King) {
                            if (((King) currentPiece).isValidCastlingMove(targetTile, board)) {
                                castling = true;
                            }
                        }

                        // Check if the move leaves the own King exposed and is therefore invalid
                        if (validMove || enPassant || castling) {
                            if(!moveLeavesKingExposed(currentTile, targetTile, castling, enPassant, player)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Replaces the given Pawn with a Queen, if it has reached the other side of the Board. The new Queen belongs to the
     * same owner and occupies the same Tile as the original ChessPiece.
     *
     * @param pawn the Pawn that will potentially be transformed into a Queen
     * @return the new Queen if the transformation was successful or the original pawn
     */
    private ChessPiece transformPawn(Pawn pawn) {
        Tile occupiedTile = pawn.getTile();

        // Check if the Pawn reached the opposite side of the board
        if ((occupiedTile.getRow() == 0 && pawn.getColor() == ChessColor.WHITE) ||
                (occupiedTile.getRow() == 7 && pawn.getColor() == ChessColor.BLACK)) {

            occupiedTile.removeChessPiece();
            Queen newQueen = new Queen(pawn.getOwner());
            occupiedTile.setChessPiece(newQueen);
            pawn.getOwner().removeChessPiece(pawn);

            return newQueen;
        }
        return pawn;
    }
}
