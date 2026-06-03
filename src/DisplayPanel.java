import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private Timer timer;
    private double gameTimer;
    private Block[][] blockList;

    public DisplayPanel() {
        timer = new Timer(10, this);
        gameTimer = 200.0;
        blockList = new Block[16][16];
        try {
            BufferedImage img = ImageIO.read(new File("src/tile000.png"));
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    blockList[row][col] = new Block(img, 480 + (row*25) - 200, 340 + (col*25) - 200, 30);
                }
            }
        } catch (IOException e) {}
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.BLUE);
        g.drawString("Timer: " + gameTimer, 700,75);
        for (int row = 0; row < blockList.length; row++) {
            for (int col = 0; col < blockList[0].length; col++) {
                g.drawImage(blockList[row][col].getImage(), blockList[row][col].getXcord(), blockList[row][col].getYcord(), null);
            }
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
        if (e.getButton() == MouseEvent.BUTTON3) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList.length; col++) {
                    try {
                        BufferedImage img = ImageIO.read(new File("src/tile002.png"));
                        blockList[row][col].setImage(img);
                    } catch (IOException ex) {
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList.length; col++) {
                    Point mouseLocation = e.getPoint();
                    if (blockList[row][col].getRect().contains(mouseLocation)) {
                        if (blockList[row][col].isMine()) {
                            try {
                                BufferedImage img = ImageIO.read(new File("src/tile005.png"));
                                blockList[row][col].setImage(img);
                            } catch (IOException ex) {
                            }
//                        } else if (nearbyMines == 0) {
//                            try {
//                                BufferedImage img = ImageIO.read(new File("src/tile001.png"));
//                                blockList[row][col].setImage(img);
//                            } catch (IOException ex) {
//                            }
//                        } else {
//                            try {
//                                BufferedImage img = ImageIO.read(new File("src/tile00" + (7 + nearbyMines) + ".png"));
//                                blockList[row][col].setImage(img);
//                            } catch (IOException ex) {
//                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
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
