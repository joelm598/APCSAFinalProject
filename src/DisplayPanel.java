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
    private BufferedImage[] imageList = new BufferedImage[14];
    private boolean firstClick;
    private boolean gameOver;

    public DisplayPanel() {
        timer = new Timer(10, this);
        firstClick = true;
        gameTimer = 200.0;
        blockList = new Block[16][16];
        for (int i = 0; i < 14; i++) {
            if (i < 10) {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile00" + i + ".png"));
                    imageList[i] = img;
                } catch (IOException e) {}
            } else {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile0" + i + ".png"));
                    imageList[i] = img;
                } catch (IOException e) {}
            }
        }
        for (int row = 0; row < blockList.length; row++) {
            for (int col = 0; col < blockList[0].length; col++) {
                blockList[row][col] = new Block(imageList[0], 480 + (row*25) - 200, 340 + (col*25) - 200, 50);
            }
        }
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
        if (gameOver) {
            timer.stop();
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
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    if (blockList[row][col].getRect().contains(mouseLocation)) {
                        checkNumMines(row, col);
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            for (int row = 0; row < blockList.length; row++) {
                for (int col = 0; col < blockList[0].length; col++) {
                    if (!blockList[row][col].isCleared()) {
                        checkFlags(row, col, mouseLocation);
                    } else if (blockList[row][col].isCleared() && !blockList[row][col].isFlagged() && blockList[row][col].getRect().contains(mouseLocation)){
                        int flags = 0;
                        if (row - 1 > -1) {
                            if (col - 1 > -1) {
                                if (blockList[row-1][col-1].isFlagged()) {
                                    flags++;
                                }
                            }
                            if (blockList[row-1][col].isFlagged()) {
                                flags++;
                            }
                            if (col + 1 < blockList[0].length) {
                                if (blockList[row-1][col+1].isFlagged()) {
                                    flags++;
                                }
                            }
                        }
                        if (row + 1 < blockList.length) {
                            if (col - 1 > -1) {
                                if (blockList[row+1][col-1].isFlagged()) {
                                    flags++;
                                }
                            }
                            if (blockList[row+1][col].isFlagged()) {
                                flags++;
                            }
                            if (col + 1 < blockList[0].length) {
                                if (blockList[row+1][col+1].isFlagged()) {
                                    flags++;
                                }
                            }
                        }
                        if (col - 1 > -1) {
                            if (blockList[row][col-1].isFlagged()) {
                                flags++;
                            }
                        }
                        if (col + 1 < blockList[0].length) {
                            if (blockList[row][col+1].isFlagged()) {
                                flags++;
                            }
                        }
                        if (blockList[row][col].getNearbyMines() == flags) {
                            if (row - 1 > -1) {
                                if (col - 1 > -1) {
                                    checkNumMines(row-1,col-1);
                                }
                                checkNumMines(row-1,col);
                                if (col + 1 < blockList[0].length) {
                                    checkNumMines(row-1,col+1);
                                }
                            }
                            if (row + 1 < blockList.length) {
                                if (col - 1 > -1) {
                                    checkNumMines(row+1,col-1);
                                }
                                checkNumMines(row+1,col);
                                if (col + 1 < blockList[0].length) {
                                    checkNumMines(row+1,col+1);
                                }
                            }
                            if (col - 1 > -1) {
                                checkNumMines(row,col-1);
                            }
                            if (col + 1 < blockList[0].length) {
                                checkNumMines(row,col+1);
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
            while (blockList[row][col].isMine() || blockList[row][col].isCannotBeMine()) {
                row = (int) (Math.random() * blockList.length);
                col = (int) (Math.random() * blockList[0].length);
            }
            blockList[row][col].setMine(true);
        }
        for (int r = 0; r < blockList.length; r++) {
            for (int c = 0; c < blockList[0].length; c++) {
                int mines = 0;
                if (r - 1 > -1) {
                    if (c - 1 > -1) {
                        if (blockList[r-1][c-1].isMine()) {
                            mines++;
                        }
                    }
                    if (blockList[r-1][c].isMine()) {
                        mines++;
                    }
                    if (c + 1 < blockList[0].length) {
                        if (blockList[r-1][c+1].isMine()) {
                            mines++;
                        }
                    }
                }
                if (r + 1 < blockList.length) {
                    if (c - 1 > -1) {
                        if (blockList[r+1][c-1].isMine()) {
                            mines++;
                        }
                    }
                    if (blockList[r+1][c].isMine()) {
                        mines++;
                    }
                    if (c + 1 < blockList[0].length) {
                        if (blockList[r+1][c+1].isMine()) {
                            mines++;
                        }
                    }
                }
                if (c - 1 > -1) {
                    if (blockList[r][c-1].isMine()) {
                        mines++;
                    }
                }
                if (c + 1 < blockList[0].length) {
                    if (blockList[r][c+1].isMine()) {
                        mines++;
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
                    blockList[row][col].setFlagged(true);
                    blockList[row][col].setImage(imageList[2]);
                } else {
                    blockList[row][col].setFlagged(false);
                    blockList[row][col].setImage(imageList[0]);
                }
            }
        }
    }

    public void checkNumMines(int row, int col) {
        if (blockList[row][col].isCleared()) {
            return;
        }
        if (firstClick) {
            if (row - 1 > -1) {
                if (col - 1 > -1) {
                    blockList[row-1][col-1].setCannotBeMine(true);
                }
                blockList[row-1][col].setCannotBeMine(true);
                if (col + 1 < blockList[0].length) {
                    blockList[row-1][col+1].setCannotBeMine(true);
                }
            }
            if (row + 1 < blockList.length) {
                if (col - 1 > -1) {
                    blockList[row+1][col-1].setCannotBeMine(true);
                }
                blockList[row+1][col].setCannotBeMine(true);
                if (col + 1 < blockList[0].length) {
                    blockList[row+1][col+1].setCannotBeMine(true);
                }
            }
            if (col - 1 > -1) {
                blockList[row][col-1].setCannotBeMine(true);
            }
            if (col + 1 < blockList[0].length) {
                blockList[row][col+1].setCannotBeMine(true);
            }
            blockList[row][col].setCannotBeMine(true);
            generateMines();
            firstClick = false;
        }
        if (blockList[row][col].isMine() && !blockList[row][col].isFlagged()) {
            blockList[row][col].setImage(imageList[4]);
            gameOver(row, col);
        } else if (blockList[row][col].getNearbyMines() == 0 && !blockList[row][col].isFlagged()) {
            if (!blockList[row][col].isCleared()) {
                blockList[row][col].setImage(imageList[1]);
                blockList[row][col].setCleared(true);
            }
            if (row - 1 > -1) {
                if (col - 1 > -1) {
                    checkNumMines(row-1, col-1);
                }
                checkNumMines(row-1, col);
                if (col + 1 < blockList[0].length) {
                    checkNumMines(row-1, col+1);
                }
            }
            if (row + 1 < blockList.length) {
                if (col - 1 > -1) {
                    checkNumMines(row+1, col-1);
                }
                checkNumMines(row+1, col);
                if (col + 1 < blockList[0].length) {
                    checkNumMines(row+1, col+1);
                }
            }
            if (col - 1 > -1) {
                checkNumMines(row, col-1);
            }
            if (col + 1 < blockList[0].length) {
                checkNumMines(row, col+1);
            }
        } else if (!blockList[row][col].isFlagged()) {
            if (blockList[row][col].getNearbyMines() == 0) {
                blockList[row][col].setImage(imageList[0]);
            } else {
                blockList[row][col].setImage(imageList[5 + blockList[row][col].getNearbyMines()]);
            }
            blockList[row][col].setCleared(true);
        }
    }
    public void gameOver(int row, int col) {
        gameOver = true;
        for (int r = 0; r < blockList.length; r++) {
            for (int c = 0; c < blockList[0].length; c++) {
                if (blockList[r][c] != blockList[row][col]) {
                    if (blockList[r][c].isMine()) {
                        blockList[r][c].setImage(imageList[3]);
                    }
                    if (!blockList[r][c].isMine() && blockList[r][c].isFlagged()) {
                        blockList[r][c].setImage(imageList[0]);
                    }
                }
            }
        }
    }
}