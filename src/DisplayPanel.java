import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private int timer;

    public DisplayPanel() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Impact", Font.BOLD, 12));
        g.setColor(Color.BLUE);
        g.drawString("" + timer, 700,75);
    }
}
