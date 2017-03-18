import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
class Tile extends JPanel implements MouseListener {
    private Player owner;
    private String owned; // ??? Need to maintain Tile ownership; PlayerX or null...
    private int row, column;

    Tile(int row, int column)   {
        super(new GridLayout(1,1));
        this.row = row;
        this.column = column;
        setMinimumSize(new Dimension(75,75));
        setMaximumSize(new Dimension(75,75));
        addMouseListener(this);
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
//        imageHolder.setVisible(true);
//        imageHolder.repaint();
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tile selected = (Tile)e.getComponent();
        if (selected.owner != null) {
            System.out.println("owner: " + getOwner().getName() + ". Row: " + getRow() + ". Col: " + getColumn() + ".\n");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
