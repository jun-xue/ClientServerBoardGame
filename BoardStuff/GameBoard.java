import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a checkered board in a JFrame to be used in various boardgames.
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/5/17
 */
public class GameBoard extends JPanel implements MouseListener{
    //instance variables
    private Color primary;
    private Color alternate;
    private int width;
    private int height;
    private int numRows;
    private int numColumns;
    private String gameTitle;
    Tile[][] boardMatrix;

    GameBoard(int boardWidth, int boardHeight, int rows, int cols, Color primary, Color alternate, String gameTitle) {

        super(new GridLayout(rows, cols));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width + 20, height + 20));
        Border boardBorder = BorderFactory.createMatteBorder(10,10,10,10, Color.BLACK);
        setBorder(boardBorder);

        //init board specs
        width = boardWidth;
        height = boardHeight;
        numRows = rows;
        numColumns = cols;
        this.primary = primary;
        this.alternate = alternate;
        this.gameTitle = gameTitle;
        boardMatrix = new Tile[numRows][numColumns];

        //2-D matrix for board representation/tile references
        boardMatrix = new Tile[numRows][numColumns];

//        //Outer JFrame - May add other features later...
//        JFrame board = new JFrame();
//        board.setSize(width + 20, height + 20);
//        board.setTitle(gameTitle);
//        board.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //Outer JPanel or 'Board' that will contain the board Tiles.
//        JPanel gameBoard = new JPanel(new GridLayout(numRows, numColumns));
//        gameBoard.setSize(width + 20, height + 20); //+20 for border width (10 + 10)
//        Border boardBorder = BorderFactory.createMatteBorder(10,10,10,10, Color.BLACK);
//        gameBoard.setBorder(boardBorder);
//        addMouseListener(this);

        //Alternate primary/secondary colors to paint the tiles/checkerboard
        Color paint;
        boolean even = true;
        for (int i = 0; i < numRows; ++i)    {
            if (even) {
                paint = primary;
            }   else {
                paint = alternate;
            }
            for (int j = 0; j < numColumns; ++j)    {
                Tile tile = new Tile(i, j);
                tile.setBackground(paint);
                if (paint.equals(primary))  {
                    paint = alternate;
                }   else    {
                    paint = primary;
                }
                add(tile);
                boardMatrix[i][j] = tile;
            }
            even ^= true;   //Flip boolean
        }
//        board.add(gameBoard);
//        board.setResizable(false);
        setVisible(true);
    }

    /**
     * Simply cycles through tiles, checking boardMatrix Tile handles, and highlights a border, then removes them.
     */
    void tileTrial()   {
        Border tileBorder = BorderFactory.createLineBorder(Color.CYAN, 1);
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                try {
                    Thread.sleep(25);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Tile change = boardMatrix[i][j];
                change.setBorder(tileBorder);
            }
        }
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                try {
                    Thread.sleep(25);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Tile change = boardMatrix[i][j];
                change.setBorder(null);
            }
        }
    }

    /**
     * Highlights the border when a mouse-click is detected on a tile. Removes all other highlights.
     * Also prints the row and column values to console for grid verification.
     * @param e - The mouse-click event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        Tile selected = (Tile)e.getComponent();
//        Border existingBorder = selected.getBorder();
//        System.out.println("Selected row: " + selected.getRow() + ", column: " + selected.getColumn() + ".");
//        Border highLight = BorderFactory.createLineBorder(Color.RED, 3);
//        if (gameTitle.contentEquals("Tic Tac Toe")) {
//            addTicTacToeBorders();
//        }   else    {
//            for (Tile[] tt : boardMatrix)   {
//                for (Tile t : tt)   {
//                    t.setBorder(null);
//                }
//            }
//        }
//        if (selected.getBorder() != highLight)   {
//            selected.setBorder(highLight);
//        }   else    {
//            selected.setBorder(existingBorder);
//        }
        Tile selected = (Tile)e.getComponent();
        System.out.println(selected.getName());
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    public int getNumRows() {
        return  numRows;
    }

    public int getNumColumns() {
        return  numColumns;
    }

    void addTicTacToeBorders()   {
        Border leftRight, topBottom, all;
        leftRight = BorderFactory.createMatteBorder(0,3,0,3,Color.BLACK);
        topBottom = BorderFactory.createMatteBorder(3,0,3,0,Color.BLACK);
        all       = BorderFactory.createMatteBorder(3,3,3,3,Color.BLACK);
        boardMatrix[0][1].setBorder(leftRight);
        boardMatrix[2][1].setBorder(leftRight);
        boardMatrix[1][0].setBorder(topBottom);
        boardMatrix[1][2].setBorder(topBottom);
        boardMatrix[1][1].setBorder(all);
        boardMatrix[0][0].setBorder(null);
        boardMatrix[0][2].setBorder(null);
        boardMatrix[2][0].setBorder(null);
        boardMatrix[2][2].setBorder(null);
    }

}