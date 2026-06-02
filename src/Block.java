import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    private BufferedImage image;
    private Rectangle rect;
    private boolean isMine;
    private int nearbyMines;
    private int xcord;
    private int ycord;

    public Block(BufferedImage image, int xcord, int ycord) {
        this.image = image;
        this.xcord = xcord;
        this.ycord = ycord;
        makeRectangle();
        isMine = false;
        nearbyMines = 0;
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

    public BufferedImage getImage() {
        return image;
    }
}
