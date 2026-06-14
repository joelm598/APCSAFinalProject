import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    private BufferedImage image;
    private Rectangle rect;
    private boolean isMine;
    private boolean flagged;
    private boolean isCleared;
    private boolean cannotBeMine;
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
        isMine = false;
        cannotBeMine = false;
        mines = totalMines;
    }

    public static void addMines() {
        mines++;
    }

    public static void subtractMines() {
        mines--;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setCannotBeMine(boolean cannotBeMine) {
        this.cannotBeMine = cannotBeMine;
    }

    public void setNearbyMines(int nearbyMines) {
        this.nearbyMines = nearbyMines;
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

    public boolean isCannotBeMine() {
        return cannotBeMine;
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

    public static int getMines() {
        return mines;
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
