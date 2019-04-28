package View;

import Audio.MusicPlayer;
import Controller.MouseInput;
import Model.Level;
import Model.Map;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIPanel extends JPanel {

    //Define game window width and height
    private final int GAME_WIDTH = 1440;
    private final int GAME_HEIGHT = 900;

    //Stores current keyboard information
    // true == pressed
    // false == released
    private Boolean[] keyPresses;

    private List<Sprite> spriteList;

    private Map currentMap;

    private String fpsCounter;

    private int playerHealth;

    public enum STATE {
        MENU,
        GAME,
        PAUSE,
        QUIT,
        SETTINGS,
        HIGHSCORES

    };

    public static STATE State = STATE.MENU;
    private Menu menu = new Menu();
    private QuitMenu quitmenu = new QuitMenu();
    private SettingsMenu settingsMenu = new SettingsMenu();



    public GUIPanel() {

        this.setVisible(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        fpsCounter = "FPS: 0 UPS: 0";

        spriteList= new ArrayList<Sprite>();

        // Must add key listener to panel in order to actually receive keybaord inputs
        addKeyListener(new TAdapter());

        keyPresses = new Boolean[256];
        Arrays.fill(keyPresses, Boolean.FALSE);


        setBackground(Color.WHITE);
        //Panel must be focusable in order to accept user input
        setFocusable(true);

        this.addMouseListener(new MouseInput());



    }


    @Override
    public void paintComponent(Graphics g) {
        if(State == STATE.GAME) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (currentMap != null) {
                currentMap.drawBackground(g2d);

            }
            drawSprites(g2d);
            if (currentMap != null) {
                currentMap.drawTop(g2d);

            }

            drawFPS(g);
            drawHealth(g);
            g.setColor(Color.WHITE);
            g.fillRect(0,0,110,30);
            g.fillRect(0,30, 110, 30);
            Font healthLabel = new Font("arial", Font.BOLD, 25);
            g.setFont(healthLabel);
            g.setColor(Color.BLACK);
            g.drawString("Health", 9, 23);
            g.drawString("Stamina", 9, 53);
            g.drawRect(0,0,110,30);
            g.drawRect(0,30,110,30);
            g.setColor(Color.CYAN);
            if(Player.stamina < 300){
                Player.stamina += 4;
                if(Player.stamina > 300){
                    Player.stamina = 300;
                }
            }
            g.fillRect(110, 30, Player.stamina,30 );
            if(playerHealth > 5) {
                g.setColor(Color.GREEN);
            }
            else if(playerHealth > 2){
                g.setColor(Color.YELLOW);
            }
            else if(playerHealth > 0){
                g.setColor(Color.RED);
            }
            g.fillRect(110, 0, playerHealth * 3, 30);
        }

        else if(State == STATE.MENU) {
            menu.render(g);
        }

        else if(State == STATE.QUIT){
            quitmenu.render(g);
        }

        else if(State == STATE.SETTINGS){
            settingsMenu.render(g);
        }
        Toolkit.getDefaultToolkit().sync();



    }
    public void Update(){
        repaint();


    }

    public void UpdateFPS(int fps, int tps){

        StringBuilder fps_counter_build = new StringBuilder();
        fps_counter_build.append("FPS: ");
        fps_counter_build.append(fps);
        fps_counter_build.append(" UPS: ");
        fps_counter_build.append(tps);
        fpsCounter = fps_counter_build.toString();

    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public void setSpriteList(List<Sprite> spriteList){
        this.spriteList = spriteList;
    }

    private void drawSprites(Graphics2D g){
        for(int i = 0; i < spriteList.size(); i++){
            Sprite thisSprite = spriteList.get(i);
            if (thisSprite.isVisible()){
                thisSprite.drawSprite(g);
            }
        }
    }

    private void drawHealth(Graphics g){
        g.setColor(Color.RED);
        g.drawString(String.valueOf(playerHealth), 1400, 40);
        g.setColor(Color.BLACK);
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    private void drawFPS(Graphics g){
        g.drawString(fpsCounter, 1, 30);
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            keyPresses[e.getKeyCode()] = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {

            //Used for finding key codes for more user input
            //System.out.println(e.getKeyCode());
            keyPresses[e.getKeyCode()] = true;
        }
    }

    public Boolean[] getKeyPresses() {
        return keyPresses;
    }
}
