package View;

import Model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    private int score = 0;
    private int tempScore = 0;
    private boolean scoreScreen = false;

    private boolean isGameOver = false;



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
        if(currentMap != null) {
            currentMap.drawBackground(g2d);

        }
        drawSprites(g2d);

        if(currentMap != null) {
            currentMap.drawTop(g2d);

        }

        drawFPS(g);
        drawHealth(g);
        if(scoreScreen){
            drawScoreScreen(g);
        }
        if(isGameOver){
            drawGameOver(g);
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

    public void setScore(int score){
        this.score = score;
    }
    public void enableScoreScreen(){
        scoreScreen = true;
    }

    public void setGameOver(){


    }

    private void drawScoreScreen(Graphics g){
        Color seeThroughGrey = new Color(0,0,0, 100);
        g.setColor(seeThroughGrey);
        Font myFont = new Font ("Courier New", 1, 52);
        String scoreText = "Score:";

        FontMetrics metrics = g.getFontMetrics(myFont);

        int x1 = (GAME_WIDTH - metrics.stringWidth(scoreText)) / 2;



        String stringScore;

        g.setFont(myFont);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("SCORE:", x1, 250);
        if(tempScore < score){
            stringScore = String.valueOf(tempScore);
            tempScore += 10;
        }
        else{
            stringScore = String.valueOf(score);
        }
        int x2 = (GAME_WIDTH - metrics.stringWidth(stringScore)) / 2;
        g.drawString(String.valueOf(stringScore), x2, 300);

    }


    private int gameOverFontSize = 1;
    private void drawGameOver(Graphics g){

        Color seeThroughGrey = new Color(0,0,0, 100);
        g.setColor(seeThroughGrey);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        g.setColor(Color.RED);

        Font myFont = new Font ("Courier New", 1, gameOverFontSize);
        g.setFont(myFont);
        if(gameOverFontSize < 100){
            gameOverFontSize++;
        }
        String gameOver = "GAME OVER";

        FontMetrics metrics = g.getFontMetrics(myFont);

        int x = (GAME_WIDTH - metrics.stringWidth(gameOver)) / 2;
        int y = ((GAME_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(gameOver, x, y);


    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    private void drawFPS(Graphics g){
        g.drawString(fpsCounter, 1, 30);
    }

    public void setGameOver(boolean gameOver) {

        isGameOver = gameOver;
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
