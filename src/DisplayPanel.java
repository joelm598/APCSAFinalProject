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
                    blockList[row][col] = new Block(img, 480 + (row*25) - 200, 340 + (col*25) - 200, 99);
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
                    if (!blockList[row][col].isCleared()) {
                        checkFlags(row, col, mouseLocation);
                    } else {
                        int flags = 0;
                        for (int dr = -1; dr <= 1; dr++) {
                            for (int dc = -1; dc <= 1; dc++) {
                                int nr = row + dr;
                                int nc = col + dc;
                                if (nr >= 0 && nr < blockList.length && nc >= 0 && nc < blockList[0].length && blockList[nr][nc].isFlagged()) {
                                    flags++;
                                }
                            }
                        }
                        if (blockList[row][col].getNearbyMines() == flags) {
                            for (int dr = -1; dr <= 1; dr++) {
                                for (int dc = -1; dc <= 1; dc++) {
                                    int nr = row + dr;
                                    int nc = col + dc;
                                    if (nr >= 0 && nr < blockList.length && nc >= 0 && nc < blockList[0].length) {
                                        checkNumMines(nr, nc);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    if (blockList[row][col].getRect().contains(mouseLocation)) {
                        if (firstClick) {
                            checkNumMines(row, col);
                        } else if (!blockList[row][col].isFlagged()) {
                            if (blockList[row][col].isMine()) {
                                checkIsMine(row, col);
                            } else {
                                checkNumMines(row, col);
                            }
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
        for (int i = 0; i < Block.getMines(); i++) {
            int row = (int) (Math.random() * blockList.length);
            int col = (int) (Math.random() * blockList[0].length);
            while (blockList[row][col].isMine() || blockList[row][col].isCleared()) {
                row = (int) (Math.random() * blockList.length);
                col = (int) (Math.random() * blockList[0].length);
            }
            blockList[row][col].setMine(true);
        }
        for (int r = 0; r < blockList.length; r++) {
            for (int c = 0; c < blockList[0].length; c++) {
                int mines = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr;
                        int nc = c + dc;
                        if (nr >= 0 && nr < blockList.length && nc >= 0 && nc < blockList[0].length && blockList[nr][nc].isMine()) {
                            mines++;
                        }
                    }
                }
                blockList[r][c].setNearbyMines(mines);
            }
        }
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

    public void checkNumMines(int row, int col) {
        if (blockList[row][col].getNearbyMines() == 0) {
            try {
                BufferedImage img = ImageIO.read(new File("src/tile001.png"));
                blockList[row][col].setImage(img);
                blockList[row][col].setCleared(true);
            } catch (IOException ex) {
            }
            if (firstClick) {
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = row + dr;
                        int nc = col + dc;
                        if (nr >= 0 && nr < blockList.length && nc >= 0 && nc < blockList[0].length) {
                            blockList[nr][nc].setCleared(true);
                        }
                    }
                }
                blockList[row][col].setCleared(true);
                generateMines();
                firstClick = false;
            }
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = row + dr;
                    int nc = col + dc;
                    if (nr >= 0 && nr < blockList.length && nc >= 0 && nc < blockList[0].length && blockList[nr][nc].getNearbyMines() == 0) {
                        checkNumMines(nr, nc);
                    }
                }
            }
        } else {
            try {
                BufferedImage img;
                if (7 + blockList[row][col].getNearbyMines() < 10) {
                    img = ImageIO.read(new File("src/tile00" + (7 + blockList[row][col].getNearbyMines()) + ".png"));
                } else {
                    img = ImageIO.read(new File("src/tile0" + (7 + blockList[row][col].getNearbyMines()) + ".png"));
                }
                blockList[row][col].setImage(img);
                blockList[row][col].setCleared(true);
            } catch (IOException ex) {
            }
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