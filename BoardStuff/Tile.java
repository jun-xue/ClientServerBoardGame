import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
class Tile extends JPanel {
    Player owned = null; // ??? Need to maintain Tile ownership; PlayerX or null...
    private int row, column;

    Tile(int row, int column)   {
        super(new GridLayout(1,1));
        this.row = row;
        this.column = column;
    }

    void addPiece(ImageIcon gamePiece)   {
        JLabel imageHolder = new JLabel(gamePiece);
        add(imageHolder);
        imageHolder.setVisible(true);
        imageHolder.repaint();
    }

    int getRow() {
        return row;
    }

    int getColumn()  {
        return column;
    }

}
