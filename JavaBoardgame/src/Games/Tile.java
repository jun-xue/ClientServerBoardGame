package Games;
import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
public class Tile extends JPanel {
	private static final long serialVersionUID = -6407769276756452673L;
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

    void addPiece(ImageIcon gamePiece)  {
        JLabel imageHolder = new JLabel(gamePiece);
        add(imageHolder);
    }

    void addPiece(ImageIcon gamePiece, boolean isKing, boolean starts)   {
        BoardPiece checker = new BoardPiece(gamePiece, isKing, starts);
        add(checker);
    }

    public int getRow() {
        return row;
    }

    public int getColumn()  {
        return column;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner()    {
        return owner;
    }

}
