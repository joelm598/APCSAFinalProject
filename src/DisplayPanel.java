import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayPanel extends JPanel implements MouseListener, KeyListener, ActionListener {
    private Timer timer;
    private Timer gameTimerTimer;
    private int gameTimer;
    private Block[][] blockList;
    private BufferedImage blankSquare;
    private BufferedImage[] tileList = new BufferedImage[14];
    private BufferedImage[] timerList = new BufferedImage[10];
    private boolean gameStart;
    private boolean firstClick;
    private boolean gameOver;
    private boolean gameWin;
    private JButton reset;
    private JButton gameStartButton;

    public DisplayPanel() {
        timer = new Timer(10, this);
        gameTimerTimer = new Timer(1000, this);
        gameStart = false;
        firstClick = true;
        gameOver = false;
        gameWin = false;
        gameTimer = 0;
        blockList = new Block[16][16];
        reset = new JButton("Try again?");
        reset.addActionListener(this);
        add(reset);
        reset.setVisible(false);
        gameStartButton = new JButton("Normal");
        gameStartButton.addActionListener(this);
        add(gameStartButton);
        gameStartButton.setVisible(true);
        try {
            BufferedImage img = ImageIO.read(new File("src/blank_square.png"));
            blankSquare = img;
        } catch (IOException e) {}
        for (int i = 0; i < 13; i++) {
            if (i < 10) {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile00" + i + ".png"));
                    tileList[i] = img;
                } catch (IOException e) {}
            } else {
                try {
                    BufferedImage img = ImageIO.read(new File("src/tile0" + i + ".png"));
                    tileList[i] = img;
                } catch (IOException e) {}
            }
        }
        for (int i = 0; i < 10; i++) {
            try {
                BufferedImage img = ImageIO.read(new File("src/timer00" + i + ".png"));
                timerList[i] = img;
            } catch (IOException e) {}
        }
        for (int row = 0; row < blockList.length; row++) {
            for (int col = 0; col < blockList[0].length; col++) {
                blockList[row][col] = new Block(tileList[0], 280 + (row*25), 140 + (col*25), 50);
                blockList[row][col].setImage(blankSquare);
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
        if (gameStart) {
            int ones = gameTimer % 10;
            int tens = (gameTimer / 10) % 10;
            int hundreds = (gameTimer / 10) / 10;
            if (gameTimer < 10) {
                g.drawImage(timerList[0], 615,95, null);
                g.drawImage(timerList[0], 635,95, null);
                g.drawImage(timerList[ones], 655,95, null);
            } else if (gameTimer < 100) {
                g.drawImage(timerList[0], 615,95, null);
                g.drawImage(timerList[tens], 635,95, null);
                g.drawImage(timerList[ones], 655,95, null);
            } else {
                g.drawImage(timerList[hundreds], 615,95, null);
                g.drawImage(timerList[tens], 635,95, null);
                g.drawImage(timerList[ones], 655,95, null);
            }
            ones = Block.getMines() % 10;
            tens = Block.getMines() / 10;
            if (Block.getMines() < 10) {
                g.drawImage(timerList[0], 287,95, null);
                g.drawImage(timerList[0], 307,95, null);
                g.drawImage(timerList[ones], 327,95, null);
            } else {
                g.drawImage(timerList[0], 287,95, null);
                g.drawImage(timerList[tens], 307,95, null);
                g.drawImage(timerList[ones], 327,95, null);
            }
            for (Block[] blocks : blockList) {
                for (int col = 0; col < blockList[0].length; col++) {
                    g.drawImage(blocks[col].getImage(), blocks[col].getXCord(), blocks[col].getYCord(), null);
                }
            }
        }
        if (gameOver) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 40));
            g.drawString("You lose!", 400, 590);
            reset.setLocation(430,600);
            reset.setVisible(true);
        }
        if (gameWin) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 40));
            g.drawString("You win!", 400, 590);
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
            if (gameOver) {
                timer.stop();
                gameTimerTimer.stop();
            }
            if (gameWin) {
                timer.stop();
                gameTimerTimer.stop();
            }
        }
        if (e.getSource() == gameTimerTimer) {
            gameTimer++;
        }
        if (e.getSource() == gameStartButton) {
            startGame();
        }
        if (e.getSource() == reset) {
            resetGame();
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

    public void startGame() {
        for (Block[] blocks : blockList) {
            for (Block block : blocks) {
                block.setImage(tileList[0]);
            }
        }
        gameStartButton.setVisible(false);
        gameStart = true;
    }

    public void generateMines() {
        for (int i = 0; i < 50; i++) {
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
                    if (Block.getMines() > 0) {
                        blockList[row][col].setFlagged(true);
                        blockList[row][col].setImage(tileList[2]);
                        Block.subtractMines();
                    }
                } else {
                    blockList[row][col].setFlagged(false);
                    blockList[row][col].setImage(tileList[0]);
                    Block.addMines();
                }
            }
        }
    }

    public void checkNumMines(int row, int col) {
        if (blockList[row][col].isCleared()) {
            return;
        }
        if (blockList[row][col].isFlagged() && blockList[row][col].isCannotBeMine()) {
            blockList[row][col].setFlagged(false);
            Block.addMines();
        }
        if (firstClick && !blockList[row][col].isFlagged()) {
            gameTimerTimer.start();
            if (row - 1 > -1) {
                if (col - 1 > -1) {
                    blockList[row-1][col-1].setCannotBeMine(true);
                    if (blockList[row-1][col-1].isFlagged()) {
                        blockList[row-1][col-1].setFlagged(false);
                        Block.addMines();
                    }
                }
                blockList[row-1][col].setCannotBeMine(true);
                if (col + 1 < blockList[0].length) {
                    blockList[row-1][col+1].setCannotBeMine(true);
                    if (blockList[row-1][col+1].isFlagged()) {
                        blockList[row-1][col+1].setFlagged(false);
                        Block.addMines();
                    }
                }
            }
            if (row + 1 < blockList.length) {
                if (col - 1 > -1) {
                    blockList[row+1][col-1].setCannotBeMine(true);
                    if (blockList[row+1][col-1].isFlagged()) {
                        blockList[row+1][col-1].setFlagged(false);
                        Block.addMines();
                    }
                }
                blockList[row+1][col].setCannotBeMine(true);
                if (blockList[row+1][col].isFlagged()) {
                    blockList[row+1][col].setFlagged(false);
                    Block.addMines();
                }
                if (col + 1 < blockList[0].length) {
                    blockList[row+1][col+1].setCannotBeMine(true);
                    if (blockList[row+1][col+1].isFlagged()) {
                        blockList[row+1][col+1].setFlagged(false);
                        Block.addMines();
                    }
                }
            }
            if (col - 1 > -1) {
                blockList[row][col-1].setCannotBeMine(true);
                if (blockList[row][col-1].isFlagged()) {
                    blockList[row][col-1].setFlagged(false);
                    Block.addMines();
                }
            }
            if (col + 1 < blockList[0].length) {
                blockList[row][col+1].setCannotBeMine(true);
                if (blockList[row][col+1].isFlagged()) {
                    blockList[row][col+1].setFlagged(false);
                    Block.addMines();
                }
            }
            blockList[row][col].setCannotBeMine(true);
            if (blockList[row][col].isFlagged()) {
                blockList[row][col].setFlagged(false);
                Block.addMines();
            }
            generateMines();
            firstClick = false;
        }
        if (blockList[row][col].isMine() && !blockList[row][col].isFlagged()) {
            blockList[row][col].setImage(tileList[4]);
            gameOver(row, col);
        } else if (blockList[row][col].getNearbyMines() == 0 && !blockList[row][col].isFlagged()) {
            if (!blockList[row][col].isCleared()) {
                blockList[row][col].setImage(tileList[1]);
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
                blockList[row][col].setImage(tileList[0]);
            } else {
                blockList[row][col].setImage(tileList[4 + blockList[row][col].getNearbyMines()]);
            }
            blockList[row][col].setCleared(true);
        }
        gameWin();
    }
    public void gameOver(int row, int col) {
        gameOver = true;
        for (Block[] blocks : blockList) {
            for (Block block : blocks) {
                if (block != blockList[row][col]) {
                    if (block.isMine()) {
                        block.setImage(tileList[3]);
                    }
                    if (!block.isMine() && block.isFlagged()) {
                        block.setImage(tileList[0]);
                    }
                }
            }
        }
    }

    public void gameWin() {
        for (Block[] blocks : blockList) {
            for (int c = 0; c < blockList[0].length; c++) {
                if (!blocks[c].isCleared() && !blocks[c].isMine()) {
                    gameWin = false;
                    return;
                }
            }
        }
        gameWin = true;
    }

    public void resetGame() {
        firstClick = true;
        gameOver = false;
        gameWin = false;
        gameStart = false;
        gameTimer = 0;
        reset.setVisible(false);
        gameStartButton.setVisible(true);
        for (Block[] blocks : blockList) {
            for (Block block : blocks) {
                block.setImage(blankSquare);
                block.setMine(false);
                block.setCleared(false);
                block.setFlagged(false);
                block.setCannotBeMine(false);
                block.setNearbyMines(0);
            }
        }
        timer.start();
        gameTimerTimer.start();
        Block.setMines(50);
        repaint();
    }
}