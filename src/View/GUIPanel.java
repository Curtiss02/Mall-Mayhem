package View;

import Controller.MouseInput;
import Model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private String fontString = "font/joystix.ttf";

    private URL fontURL;

    private Font myFont;

    public enum STATE {
        MENU,
        GAME,
        PAUSE,
        QUIT,
        SETTINGS,
        HIGHSCORES

    };
    private int score = 0;
    private int tempScore = 0;
    private boolean scoreScreen = false;

    private boolean isGameOver = false;
    public static STATE State = STATE.MENU;
    private Menu menu = new Menu();
    private QuitMenu quitmenu = new QuitMenu();
    private SettingsMenu settingsMenu = new SettingsMenu();
    private HighScoreMenu highScoreMenu = new HighScoreMenu();



    public GUIPanel() {

        this.setVisible(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        fpsCounter = "FPS: 0 UPS: 0";

        spriteList= new ArrayList<Sprite>();

        // Must add key listener to panel in order to actually receive keybaord inputs
        addKeyListener(new TAdapter());

        keyPresses = new Boolean[1000];
        Arrays.fill(keyPresses, Boolean.FALSE);

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(fontString);
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);

        }catch (Exception e){}



        setBackground(Color.WHITE);
        //Panel must be focusable in order to accept user input
        setFocusable(true);

        this.addMouseListener(new MouseInput());


    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    if(State == STATE.GAME) {
        Graphics2D g2d = (Graphics2D) g;
        if(currentMap != null) {
            currentMap.drawBackground(g2d);

        }
        drawSprites(g2d);

        if(currentMap != null) {
            currentMap.drawTop(g2d);

        }
        // Debugging functions
        //drawFPS(g);
        //drawHealth(g);

        if(isGameOver){
            drawGameOver(g);
        }
        if(gameWon){
            drawGameWon(g);
        }
        if(scoreScreen){
            drawScoreScreen(g);
        }

            g.setFont(myFont);

            drawHealthBar(g);
            drawTime(g);


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
        else if(State == STATE.HIGHSCORES){
            highScoreMenu.render(g);

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

    private void drawScoreScreen(Graphics g) {


        g.setFont(myFont);
        String scoreText = "Score:";

        FontMetrics metrics = g.getFontMetrics(myFont);

        int x1 = (GAME_WIDTH - metrics.stringWidth(scoreText)) / 2;



        String stringScore;

        g.setFont(myFont);

        g.setColor(Color.WHITE);
        g.drawString("SCORE:", x1, 250);
        if(tempScore < score){
            stringScore = String.valueOf(tempScore);
            tempScore += 100;
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




        g.setFont(myFont);
        if(gameOverFontSize < 100){
            gameOverFontSize++;
        }

        Font gameOverFont = myFont.deriveFont((float)gameOverFontSize);
        g.setFont(gameOverFont);
        String gameOver = "GAME OVER";

        FontMetrics metrics = g.getFontMetrics(gameOverFont);

        int x = (GAME_WIDTH - metrics.stringWidth(gameOver)) / 2;
        int y = ((GAME_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(gameOver, x, y);

        if(gameOverFontSize >= 100) {
            String esc = "Press ESC to quit";
            x = (GAME_WIDTH - metrics.stringWidth(esc)) / 2;
            y = GAME_HEIGHT/2+((GAME_HEIGHT/2 - metrics.getHeight()) / 2) + metrics.getAscent();
            g.drawString(esc, x, y);
        }

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
    private boolean gameWon = false;

    public void setGameWon(boolean gameWon){
        this.gameWon = gameWon;
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

    private void drawHealthBar(Graphics g){
        Font healthFont = myFont.deriveFont(24L);
        g.setFont(healthFont);
        g.drawString("HEALTH", 1000, 45);
        g.setColor(Color.WHITE);
        g.fillRect(1130,20,300, 30);
        g.setColor(Color.RED);
        g.fillRect(1130, 20, playerHealth * 3, 30);
        g.setColor(Color.BLACK);
        g.drawRect(1130,20, 300, 30);
    }

    private String timerString = "5:00";
    public void setTimer(int time){
        Long mins = (TimeUnit.SECONDS.toMinutes(time));
        Long seconds = time - (mins * 60);
        timerString = "" + mins + ":" + String.format("%02d", seconds);
    }
    public void drawTime(Graphics g){
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString(timerString,30,50);
    }
    private void drawGameWon(Graphics g){
        Color seeThroughGrey = new Color(0,0,0, 100);
        g.setColor(seeThroughGrey);
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        g.setColor(Color.WHITE);




        g.setFont(myFont);
        if(gameOverFontSize < 100){
            gameOverFontSize++;
        }

        Font gameOverFont = myFont.deriveFont((float)gameOverFontSize);
        g.setFont(gameOverFont);
        String gameOver = "YOU WIN";

        FontMetrics metrics = g.getFontMetrics(gameOverFont);

        int x = (GAME_WIDTH - metrics.stringWidth(gameOver)) / 2;
        int y = ((GAME_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(gameOver, x, y);
        if(gameOverFontSize >= 100) {
            String esc = "Press ESC to quit";
            x = (GAME_WIDTH - metrics.stringWidth(esc)) / 2;
            y = GAME_HEIGHT/2+((GAME_HEIGHT/2 - metrics.getHeight()) / 2) + metrics.getAscent();
            g.drawString(esc, x, y);
        }
    }

}
