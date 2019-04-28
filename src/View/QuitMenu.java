package View;

import javax.swing.*;
import java.awt.*;

public class QuitMenu {
    public Rectangle yesbutton = new Rectangle(400, 350, 150, 70);
    public Rectangle nobutton = new Rectangle(800, 350, 150, 70);


    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);
        g2d.fillRect(400, 350, 150, 70);
        g2d.fillRect(800, 350, 150, 70);
        Font fnt0 = new Font("arial", Font.BOLD, 60);
        g.setFont(fnt0);
        g.setColor(Color.BLACK);
        //Setting Title
        g.drawString("Are You Sure You want To Quit?", 220, 100);
        //Setting buttons
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.drawString("Yes", yesbutton.x + 20, yesbutton.y + 55);
        g.drawString("No", nobutton.x + 35, nobutton.y + 55);

        g2d.draw(yesbutton);
        g2d.draw(nobutton);



    }
}
