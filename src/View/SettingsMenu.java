package View;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu {
    public Rectangle musicbutton = new Rectangle(430, 250, 450, 70);
    public Rectangle soundbutton = new Rectangle(430, 450, 450, 70);
    public Rectangle backbutton = new Rectangle(50, 50, 200, 70);
    public static int musicOn = 1;
    public static int soundOn = 1;

    ImageIcon background = new ImageIcon(this.getClass().getClassLoader().getResource("img/menu.jpg"));

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background.getImage(),0,0, null);
        g.setColor(Color.WHITE);
        g2d.fillRect(430, 250, 450, 70);
        g2d.fillRect(430, 450, 450, 70);
        g2d.fillRect(50,50,200,70);
        Font fnt0 = new Font("arial", Font.BOLD, 70);
        g.setFont(fnt0);
        g.setColor(Color.BLACK);
        //Setting Title
        g.drawString("Settings", 570, 100);
        //Setting buttons
        Font fnt1 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt1);
        g.drawString("Music", musicbutton.x + 10, musicbutton.y + 50);
        g.drawString("Sounds", soundbutton.x + 10, soundbutton.y + 50);
        g.drawString("Back", backbutton.x + 20, backbutton.y + 50);


        if(musicOn == 1){
            g.setColor(Color.WHITE);
            g2d.fillRect(680,250, 100, 70);
            g.setColor(Color.BLACK);
            g.drawString("On", 690, 300);
            g2d.fillRect(780,250,100,70);
            g.setColor(Color.WHITE);
            g.drawString("Off", 790, 300);

        }
        else if(musicOn == 0){
            g.setColor(Color.BLACK);
            g2d.fillRect(680,250, 100, 70);
            g.setColor(Color.WHITE);
            g.drawString("On", 690, 300);
            g2d.fillRect(780,250,100,70);
            g.setColor(Color.BLACK);
            g.drawString("Off", 790, 300);
        }
        if(soundOn == 1){
            g.setColor(Color.WHITE);
            g2d.fillRect(680,450, 100, 70);
            g.setColor(Color.BLACK);
            g.drawString("On", 690, 500);
            g2d.fillRect(780,450,100,70);
            g.setColor(Color.WHITE);
            g.drawString("Off", 790, 500);

        }
        else if(soundOn == 0){
            g.setColor(Color.BLACK);
            g2d.fillRect(680,450, 100, 70);
            g.setColor(Color.WHITE);
            g.drawString("On", 690, 500);
            g2d.fillRect(780,450,100,70);
            g.setColor(Color.BLACK);
            g.drawString("Off", 790, 500);
        }
        g.setColor(Color.BLACK);
        g2d.draw(musicbutton);
        g2d.draw(soundbutton);
        g2d.draw(backbutton);

    }
}
