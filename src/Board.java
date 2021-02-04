import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Board extends JPanel {

    private Tile tiles [][];
    private int tileSize;

    public Board(int tileSize) {
        this.setLayout(new GridLayout(8, 8));
        this.setBorder(new LineBorder(Color.BLACK));
        this.tileSize = tileSize;
        this.tiles = new Tile[8][8];

        for (int rowNum = 0; rowNum < tiles.length; rowNum++) {
            for (int colNum = 0; colNum < tiles[rowNum].length; colNum++) {
                Tile tile;
                if ((rowNum % 2 == 0 && colNum % 2 == 0) || (rowNum % 2 == 1 && colNum % 2 == 1)) {
                    tile = new Tile(ChessColor.WHITE, rowNum, colNum, tileSize);
                } else {
                    tile = new Tile(ChessColor.BLACK, rowNum, colNum, tileSize);
                }
                tiles[colNum][rowNum] = tile;
                this.add(tile);
            }
        }
    }
}
