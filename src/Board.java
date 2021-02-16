import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Representation of a chess board. It consists of 8 x 8 square of {@link Tile}s. The Tiles can be used to place
 * {@link ChessPiece}s on them. The Board can be observed by a {@link Game}. When a mouseclick event on one of the Tiles
 * occurs the observing board is notified.
 */
public class Board extends JPanel {

    private final Tile[][] tiles;
    private final int tileSize;
    private final ArrayList<Game> observers = new ArrayList<>();
    private Player playerWhite;
    private Player playerBlack;

    /**
     * Creates a new (Chess-) Board. It consists of 8 x 8 square of {@link Tile}s. The Tiles can be used to place
     * {@link ChessPiece}s on them. The Board can be observed by a {@link Game}. When a mouseclick event on one of the
     * Tiles occurs the observing board is notified.
     *
     * @param tileSize size of the used Tiles in px
     */
    public Board(int tileSize) {
        this.setLayout(new GridLayout(8, 8));
        this.setBorder(new LineBorder(Color.BLACK));
        this.tileSize = tileSize;
        this.tiles = new Tile[8][8];

        // Initialize Tiles with alternating color and ActionListener which calls the notify() method of the Board
        for (int rowNum = 0; rowNum < tiles.length; rowNum++) {
            for (int colNum = 0; colNum < tiles[rowNum].length; colNum++) {
                Tile tile;
                if ((rowNum % 2 == 0 && colNum % 2 == 0) || (rowNum % 2 == 1 && colNum % 2 == 1)) {
                    tile = new Tile(this, ChessColor.WHITE, rowNum, colNum, tileSize);
                } else {
                    tile = new Tile(this, ChessColor.BLACK, rowNum, colNum, tileSize);
                }
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((Tile) e.getSource()).getBoard().notify((Tile) e.getSource());
                    }
                });
                tiles[rowNum][colNum] = tile;
                this.add(tile);
            }
        }
    }

    /**
     * Returns the {@link Tile} of the board, which has the specified index. Row  and column indices start at 0 and go
     * up to 7. The index (0,0) represents the upper left corner and (7,7) the bottom right corner.
     *
     * @param row the index of the row
     * @param col the index of the column
     * @return the Tile which has the (rol, col) position on the Board
     */
    public Tile getTile(int row, int col) {
        if (row >= tiles.length || col >= tiles.length)
            throw new IndexOutOfBoundsException("Error: Trying to access a tile that is not on the board");
        return tiles[row][col];
    }

    /**
     * Returns a two-dimensional array of the {@link Tile}s, which make up the board.
     *
     * @return the Tiles of this Board.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Adds an observing {@link Game} to this Board. When the Board is interacted with, the event data will be forwarded
     * to all observing Games.
     *
     * @param game the Game that will be added to the list of Observers
     */
    public void addObserver(Game game) {
        this.observers.add(game);
    }

    /**
     * Removes the specified {@link Game} from the list of Observers.
     *
     * @param game the Game that will be removed from the list of Observers.
     */
    public void removeObserver(Game game) {
        this.observers.remove(game);
    }

    /**
     * Notifies all observing {@link Game}s of an event. In this case, an event is always a click on a {@link Tile}
     * belonging to the Board. This method calls the {@link Game#processInput(Tile)} method of the observing Games. This
     * method translates this information into moves on the Board.
     *
     * @param clickedTile the Tile that has been clicked
     */
    private void notify(Tile clickedTile) {
        for (Game observer: this.observers) {
            observer.processInput(clickedTile);
        }
    }

    /**
     * Returns the size of this Boards {@link Tile}s.
     *
     * @return the size of the Tiles in px
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Returns the {@link Player}, who owns the white {@link ChessPiece}s on this Board.
     *
     * @return the white Player of this Board
     */
    public Player getPlayerWhite() {
        return playerWhite;
    }

    /**
     * Sets the owner of the white {@link ChessPiece}s located on this Board to the given value.
     *
     * @param playerWhite the new owner of the white ChessPieces on this Board.
     */
    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    /**
     * Returns the {@link Player}, who owns the black {@link ChessPiece}s on this Board.
     *
     * @return the black Player of this Board
     */
    public Player getPlayerBlack() {
        return playerBlack;
    }

    /**
     * Sets the owner of the black {@link ChessPiece}s located on this Board to the given value.
     *
     * @param playerBlack the new owner of the black ChessPieces on this Board.
     */
    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }
}
