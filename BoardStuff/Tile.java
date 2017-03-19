import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
class Tile extends JPanel {
    private Player owner;
    private String owned;
    private int row, column;
    boolean free = true;

    Tile(int row, int column)   {
        super(new GridLayout(1,1));
        this.row = row;
        this.column = column;
        setMinimumSize(new Dimension(75,75));
        setMaximumSize(new Dimension(75,75));
        setVisible(true);
    }

    public String getOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
    }

    void addPiece(ImageIcon gamePiece)   {
        JLabel imageHolder = new JLabel(gamePiece);
        add(imageHolder);
    }

    int getRow() {
        return row;
    }

    int getColumn()  {
        return column;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner()    {
        return owner;
    }

}
