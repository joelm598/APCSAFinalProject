import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private Timer timer;
    private double gameTimer;
    private ArrayList<Block> blockList;

    public DisplayPanel() {
        timer = new Timer(10, this);
        gameTimer = 200.0;
        blockList = new ArrayList<>();
        try {
            BufferedImage img = ImageIO.read(new File("src/tile000.png"));
            for (int i = 0; i < 16; i++) {
                Block b = new Block(img);
                blockList.add(b);
            }
        } catch (IOException e) {}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.BLUE);
        g.drawString("Timer: " + gameTimer, 700,75);
        for (int i = 0; i < blockList.size(); i++) {
            g.drawImage(blockList.get(i).getImage(), 300 + (i*50), 100, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
