import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

public class Board extends JPanel {

    private Tile tiles [][];
    private int tileSize;
    private ArrayList<Game> observers = new ArrayList<Game>();

    public Board(int tileSize) {
        this.setLayout(new GridLayout(8, 8));
        this.setBorder(new LineBorder(Color.BLACK));
        this.tileSize = tileSize;
        this.tiles = new Tile[8][8];

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

    public Tile getTile(int row, int col) {
        if (row >= tiles.length || col >= tiles.length)
            throw new IndexOutOfBoundsException("Error: Trying to access a tile that is not on the board");
        return tiles[row][col];
    }

    public void addObserver(Game game) {
        this.observers.add(game);
    }

    public void removeObserver(Game game) {
        this.observers.remove(game);
    }

    private void notify(Tile clickedTile) {
        for (Game observer: this.observers) {
            observer.update(clickedTile);
        }
    }
}
