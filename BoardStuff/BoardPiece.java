import javax.swing.*;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/19/2017
 */
public class BoardPiece extends JLabel {

    ImageIcon checkerOnBackground;
    boolean isKing;
    boolean starts; //isRedPiece

    BoardPiece(ImageIcon piece, boolean isRed, boolean isKing)    {
        super(piece);
        checkerOnBackground = piece;
        this.isKing = isKing;
        starts = isRed;
    }

}
