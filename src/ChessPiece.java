import javax.swing.*;

/**
 * Representation of a chess piece. A chess piece is either a Pawn, Rook, Knight, Bishop, Queen or King. Each kind of
 * chess piece has its own move set. Those different sets of possible moves are implemented in the according subclasses.
 * This abstract superclass implements fields and methods, that all pieces have in common. Every chess piece belongs to
 * exactly one player, has a specific Icon and a reference to the Tile on which it currently stands.
 */
public abstract class ChessPiece{

    protected Player owner;
    protected ImageIcon icon;
    protected Tile tile;
    protected boolean canCastle;

    /**
     * Creates a new chess piece.
     *
     * @param owner the player which owns this chess piece.
     */
    public ChessPiece(Player owner) {
        if (owner == null) {
            throw new NullPointerException("The owner of a chess piece, has to be a valid player, it can not be null.");
        }
        this.owner = owner;
        this.owner.addChessPiece(this);
        this.canCastle = false;
    }

    /**
     * Returns the color of the chess piece. It corresponds to the color of the player who owns this chess piece.
     *
     * @return the color of this chess piece
     */
    public ChessColor getColor() {
        return owner.getColor();
    }

    /**
     * Returns the player who owns this chess piece.
     *
     * @return the player who owns this chess piece
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owning player of this chess piece to the given value
     *
     * @param owner the player that will own this chess piece
     */
    public void setOwner(Player owner) {
        if (owner == null) {
            throw new NullPointerException("The owner of a chess piece, has to be a valid player, it can not be null.");
        }
        this.owner = owner;
    }

    /**
     * Returns the icon sized image associated with this chess piece.
     *
     * @return the icon associated with this chess piece
     */
    public ImageIcon getIcon() {
        return this.icon;
    }

    /**
     * Returns the Tile on which the chess piece is currently placed. This can be 'null' if the chess piece is currently
     * not placed on a board.
     *
     * @return the Tile this chess piece is placed on
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Sets the 'tile' field to the given value. The chess piece will now reference the given Tile as its current
     * location on a chess board. Setting this to 'null' corresponds to removing this chess piece from a board/game.
     *
     * @param tile the Tile the chess piece will be moved to
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Calculates if the chess pieces' move set allows it to move from its current location (the Tile it currently is
     * placed on) to the desired location (the given target Tile). This method only checks if the move is possible
     * from the viewpoint of the chess piece. It returns false, if the targeted Tile is either not reachable from its
     * current location or if there are other chess pieces in between the two locations. Special moves like 'castling'
     * or 'en passant' are NOT checked by this method. It also does NOT investigate if the move leaves the own king
     * checked.
     *
     * @param target the target Tile of the move
     * @param board the board which both the current and target Tile belong to
     * @return true if this is a valid move for the chess piece, else: false
     */
    public abstract boolean isValidMove(Tile target, Board board);

    /**
     * Returns whether or not the chess piece could theoretically be involved in a castling move. Only Kings and
     * Rooks can castle and only if they have not moved before. There can not be any chess pieces between the involved
     * King and Rook.
     *
     * @return true, if the chess piece can castle. False if it can not castle.
     */
    public boolean canCastle() {
        return this.canCastle;
    }

    /**
     * Returns the name of the chess piece, e.g. 'King', 'Pawn', 'Queen', ...
     *
     * @return a String holding the name of the chess piece.
     */
    public abstract String getName();

    /**
     * Creates a copy of this chess piece.
     *
     * @return a copy of this ChessPiece object
     */
    @Override
    public abstract ChessPiece clone();
}
