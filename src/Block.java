import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block implements MouseListener {
    private BufferedImage image;
    private BufferedImage flagImage;
    private boolean isMine;

    public Block(BufferedImage image) {
        this.image = image;
        isMine = false;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isMine) {
            try {
                BufferedImage img = ImageIO.read(new File("src/tile001.png"));
            } catch (IOException ex) {}
        } else {
            try {
                BufferedImage img = ImageIO.read(new File("src/tile001.png"));
            } catch (IOException ex) {}
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
