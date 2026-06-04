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
    private boolean firstClick;

    public DisplayPanel() {
        timer = new Timer(10, this);
        firstClick = true;
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
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.BLUE);
        g.drawString("Timer: " + gameTimer, 700,75);
        for (Block[] blocks : blockList) {
            for (int col = 0; col < blockList[0].length; col++) {
                g.drawImage(blocks[col].getImage(), blocks[col].getXCord(), blocks[col].getYCord(), null);
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
        Point mouseLocation = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON3) {
            for (int row = 0; row < blockList[0].length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    checkFlags(row, col, mouseLocation);
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    if (blockList[row][col].getRect().contains(mouseLocation)) {
                        if (firstClick) {
                            generateMines();
                            repeatCheckNoMines(row, col);
                        } else if (blockList[row][col].isMine()) {
                            checkIsMine(row, col);
                        } else if (blockList[row][col].getNearbyMines() == 0) {
                            repeatCheckNoMines(row, col);
                        } else {
                            checkNumMines(row, col);
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

    public void generateMines() {

    }

    public void checkFlags(int row, int col, Point p) {
        if (blockList[row][col].getRect().contains(p)) {
            if (!blockList[row][col].isCleared()) {
                if (!blockList[row][col].isFlagged()) {
                    try {
                        BufferedImage img = ImageIO.read(new File("src/tile002.png"));
                        blockList[row][col].setFlagged(true);
                        blockList[row][col].setImage(img);
                    } catch (IOException ex) {
                    }
                } else {
                    try {
                        BufferedImage img = ImageIO.read(new File("src/tile000.png"));
                        blockList[row][col].setFlagged(false);
                        blockList[row][col].setImage(img);
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    public void repeatCheckNoMines(int row, int col) {
        for (int i = row - 1; i < row + 2; i++) {
            if (i > -1 && i < blockList.length) {
                checkNoMines(row, col);
            }
        }
    }

    public void checkNoMines(int row, int col) {
        try {
            BufferedImage img = ImageIO.read(new File("src/tile001.png"));
            blockList[row][col].setImage(img);
            blockList[row][col].setCleared(true);
        } catch (IOException ex) {
        }
    }

    public void checkNumMines(int row, int col) {
        try {
            BufferedImage img = ImageIO.read(new File("src/tile00" + (7 + blockList[row][col].getNearbyMines()) + ".png"));
            blockList[row][col].setImage(img);
            blockList[row][col].setCleared(true);
        } catch (IOException ex) {
        }
    }

    public void checkIsMine(int row, int col) {
        try {
            BufferedImage img = ImageIO.read(new File("src/tile005.png"));
            blockList[row][col].setImage(img);
        } catch (IOException ex) {
        }
    }
}
