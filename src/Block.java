import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block implements MouseListener {
    private BufferedImage image;
    private boolean isMine;
    private int nearbyMines;

    public Block(BufferedImage image) {
        this.image = image;
        isMine = true;
        nearbyMines = 0;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
        if (e.getButton() == MouseEvent.BUTTON3) {
            try {
                BufferedImage img = ImageIO.read(new File("src/tile002.png"));
                setImage(img);
            } catch (IOException ex) {}
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isMine) {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile005.png"));
                    setImage(img);
                } catch (IOException ex) {}
            } else if (nearbyMines == 0) {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile001.png"));
                    setImage(img);
                } catch (IOException ex) {}
            } else {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile00" + (7 + nearbyMines) + ".png"));
                    setImage(img);
                } catch (IOException ex) {}
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
