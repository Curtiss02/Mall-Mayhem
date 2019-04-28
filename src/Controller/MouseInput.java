package Controller;

import Audio.MusicPlayer;
import View.GUIPanel;
import View.Menu;
import View.SettingsMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        MusicPlayer musicPlayer = new MusicPlayer();

        /*public Rectangle playbutton = new Rectangle(650, 150, 300, 75);
    public Rectangle highscoresbutton = new Rectangle(650, 300, 300, 75);
    public Rectangle settingsbutton = new Rectangle(650, 450, 300, 75);
    public Rectangle quitbutton = new Rectangle(650, 600, 300, 75);*/


        if(mx >= 650 && mx <= 950 && GUIPanel.State == GUIPanel.STATE.MENU){
            //Play Button
            if(my >= 150 && my <= 225) {
                //Pressed play button, State changes to game
                GUIPanel.State = GUIPanel.STATE.GAME;
                if(SettingsMenu.musicOn == 1) {
                    musicPlayer.playMusic("C:\\Users\\josha\\IdeaProjects\\2019-Java-Group42\\src\\Audio\\Old_Town_Road_I_Got_Horses_In_The_Back_8_Bit_Tribute_to_Lil_Nas_X_-_8_Bit_Universe-j_F75fjZ8w4.wav");
                }
            }
            //Quit Button
            else if(my >= 600 && my <= 675){
                //Exits the program
                System.exit(0);
            }
            //Settings Button
            else if(my >= 450 && my <= 525){
                GUIPanel.State = GUIPanel.STATE.SETTINGS;
            }
        }
        else if(my >= 350 && my <= 420 && GUIPanel.State == GUIPanel.STATE.QUIT){
            //Yes Button
            if(mx >= 100 && mx <= 630){
                System.exit(0);
            }
            else if(mx >= 800 && mx <= 1330){
                GUIPanel.State = GUIPanel.STATE.GAME;
            }
        }
        else if(GUIPanel.State == GUIPanel.STATE.SETTINGS){
            if(mx >= 50 && mx <= 250){
                if(my >= 50 && my <= 120){
                    GUIPanel.State = GUIPanel.STATE.MENU;
                }
            }
            if(mx >= 680 && mx <= 780){
                if(my >= 250 && my <= 320){
                    SettingsMenu.musicOn = 1;
                }
            }
            if(mx >= 780 && mx <= 880){
                if(my >= 250 && my <= 320){
                    SettingsMenu.musicOn = 0;
                }
            }
            if(mx >= 680 && mx <= 780){
                if(my >= 450 && my <= 520){
                    SettingsMenu.soundOn = 1;
                }
            }
            if(mx >= 780 && mx <= 880){
                if(my >= 450 && my <= 520){
                    SettingsMenu.soundOn = 0;
                }
            }
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
