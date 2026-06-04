import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    private BufferedImage image;
    private Rectangle rect;
    private boolean isMine;
    private boolean flagged;
    private int nearbyMines;
    private int xcord;
    private int ycord;
    private static int mines;

    public Block(BufferedImage image, int xcord, int ycord, int totalMines) {
        this.image = image;
        this.xcord = xcord;
        this.ycord = ycord;
        makeRectangle();
        nearbyMines = 0;
        flagged = false;
//        Rewrite logic to make sure mines don't spawn on first click
//        if (mines < totalMines) {
//            if (Math.random() == 0) {
//                isMine = false;
//            } else {
//                mines++;
//                isMine = true;
//            }
//        }
    }

    public static void setMines(int mines) {
        Block.mines = mines;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getXcord() {
        return xcord;
    }

    public int getYcord() {
        return ycord;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void makeRectangle() {
        int imageWidth = getImage().getWidth();
        int imageHeight = getImage().getHeight();
        rect = new Rectangle(xcord, ycord, imageWidth, imageHeight);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public BufferedImage getImage() {
        return image;
    }
}
