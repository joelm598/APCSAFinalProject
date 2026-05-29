import java.awt.image.BufferedImage;

public class Block {
    private BufferedImage image;
    private BufferedImage flagImage;

    public Block(BufferedImage image, BufferedImage flag) {
        this.image = image;
        flagImage = flag;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void clicked() {
        image = flagImage;
    }
}
