import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    private BufferedImage image;
    private Rectangle rect;
    private boolean isMine;
    private boolean flagged;
    private boolean isCleared;
    private int nearbyMines;
    private int xCord;
    private int yCord;
    private static int mines;

    public Block(BufferedImage image, int xCord, int yCord, int totalMines) {
        this.image = image;
        this.xCord = xCord;
        this.yCord = yCord;
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isCleared() {
        return isCleared;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }

    public Rectangle getRect() {
        return rect;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void makeRectangle() {
        int imageWidth = getImage().getWidth();
        int imageHeight = getImage().getHeight();
        rect = new Rectangle(xCord, yCord, imageWidth, imageHeight);
    }
}
