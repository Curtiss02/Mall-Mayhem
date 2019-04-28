package View;

import javax.swing.*;
import java.awt.*;

public class PauseMenu {
    ImageIcon background = new ImageIcon("C:\\Users\\josha\\MenuCover.jpg");

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background.getImage(),0,0, null);
        Font fnt0 = new Font("arial", Font.BOLD, 70);
        g.setFont(fnt0);
        g.setColor(Color.YELLOW);
        //Setting Title
        g.drawString("Game Paused, press P to resume", 300, 100);

    }
}
