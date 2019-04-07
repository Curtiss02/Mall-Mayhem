package View;

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
    private final int GAME_WIDTH = 1400;
    private final int GAME_HEIGHT = 900;

    //Stores current keyboard information
    // true == pressed
    // false == released
    private Boolean[] keyPresses;

    private List<Sprite> spriteList;

    private String fpsCounter;

    private int frameNum = 0;


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



    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawFrames(g);
        drawSprites(g2d);
        drawFPS(g);

        Toolkit.getDefaultToolkit().sync();


    }
    public void Update(){
        repaint();

        if (frameNum > 60) {
            frameNum = 0;
        } else {
            frameNum++;
        }


    }

    public void UpdateFPS(int fps, int tps){

        StringBuilder fps_counter_build = new StringBuilder();
        fps_counter_build.append("FPS: ");
        fps_counter_build.append(fps);
        fps_counter_build.append(" UPS: ");
        fps_counter_build.append(tps);
        fpsCounter = fps_counter_build.toString();

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

    private void drawFPS(Graphics g){
        g.drawString(fpsCounter, 1, 30);
    }

    private void drawFrames(Graphics g) {
        g.drawString(Integer.toString(frameNum), getWidth() / 2, getHeight() / 2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            keyPresses[e.getKeyCode()] = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPresses[e.getKeyCode()] = true;
        }
    }

    public Boolean[] getKeyPresses() {
        return keyPresses;
    }
}
