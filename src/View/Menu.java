package View;

import Controller.GameController;

import javax.swing.*;
import java.awt.*;


public class Menu {
    //Creating Different buttons to press
    public Rectangle playbutton = new Rectangle(570, 150, 300, 75);
    public Rectangle highscoresbutton = new Rectangle(570, 300, 300, 75);
    public Rectangle settingsbutton = new Rectangle(570, 450, 300, 75);
    public Rectangle quitbutton = new Rectangle(570, 600, 300, 75);
    ImageIcon background = new ImageIcon("C:\\Users\\josha\\MenuCover.jpg");

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background.getImage(),0,0, null);
        g.setColor(Color.WHITE);
        g2d.draw(playbutton);
        g2d.draw(highscoresbutton);
        g2d.draw(settingsbutton);
        g2d.draw(quitbutton);
        g2d.fillRect(570, 150, 300, 75);
        g2d.fillRect(570, 300, 300, 75);
        g2d.fillRect(570, 450, 300, 75);
        g2d.fillRect(570, 600, 300, 75);
        Font fnt0 = new Font("arial", Font.BOLD, 85);
        g.setFont(fnt0);
        g.setColor(Color.BLACK);
        //Setting Title
        g.drawString("Mall Mayhem", 450, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 45);
        g.setFont(fnt1);
        //Setting Buttons
        g.drawString("Play", playbutton.x + 93, playbutton.y + 47);
        g.drawString("High Scores", highscoresbutton.x + 22, highscoresbutton.y + 47);
        g.drawString("Settings", settingsbutton.x + 55, settingsbutton.y + 47);
        g.drawString("Quit", quitbutton.x + 80, quitbutton.y + 47);

    }
}
