package Games;
import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Daniel Ackerman 23104834
 * @version 0.1.0, 3/9/2017
 */
class Player {
    boolean starts = false; //red starts
    ArrayList<ImageIcon> playerPieces;
    ArrayList<Tile> myTiles;
    String name;

    Player(String name) {
        playerPieces = new ArrayList<>(2);
        this.name = name;
        myTiles = new ArrayList<>(12);
    }

    public String getName() {
        return name;
    }

    public void addTile(Tile mine)  {
        mine.setOwner(this);
        mine.free = false;
        myTiles.add(mine);
    }

    public void removeTile(Tile moved)  {
        moved.setOwner(null);
        moved.free = true;
        myTiles.remove(moved);
    }
}
