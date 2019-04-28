package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class HighScoreMenu {
    public Rectangle backbutton = new Rectangle(50, 50, 200, 70);
    public static List<Integer> highScores = new ArrayList<Integer>();
    private Font myFont;
    ImageIcon background = new ImageIcon(this.getClass().getClassLoader().getResource("img/menu.jpg"));


    public HighScoreMenu(){
        loadHighScores();
        Collections.sort(highScores);
        Collections.reverse(highScores);

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("font/joystix.ttf");
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);

        }catch (Exception e){}
    }

    public void render(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background.getImage(),0,0, null);
        g2d.setColor(Color.WHITE);
        g2d.fill(backbutton);
        g.setFont(myFont.deriveFont(40));
        g.setColor(Color.BLACK);
        g.drawString("BACK", 80,100);
        Font bigFont = myFont.deriveFont(100F);
        g.setColor(Color.WHITE);
        g.setFont(bigFont);
        g.drawString("HIGHSCORES", 350, 100);
        g.setFont(myFont);

        for(int i = 0; i < 5; i++){
            String num = Integer.toString(i+1) + ":";
            g.drawString(num, 400, (300+(i*100)));
        }
        int scoreCounter = 0;

        for(int score : highScores){
            String scoreStr = Integer.toString(score);
            g.drawString(scoreStr, 500, 300+(scoreCounter*100));
            scoreCounter++;
            if(scoreCounter >= 5){
                break;
            }
        }


    }

    private static boolean added = false;
    public static void addHighScore(int score){
        if(!added) {
            highScores.add(score);
            writeHighScores();
            added = true;
        }

    }

    public static void writeHighScores(){
        String highScoresText = "";
        for(int score : highScores) {
            highScoresText += score + "\n";
        }


        try (FileWriter writer = new FileWriter("highscores.txt");
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(highScoresText);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

    }


    public static void loadHighScores(){
        File highscoretxt = new File("highscores.txt");
        BufferedReader br;
        System.out.println("LOADING HIGHSCORES");
        try {
            highscoretxt.createNewFile();
            br = new BufferedReader(new FileReader(highscoretxt));
            System.out.println("LOADED FILE");
            String line;

            while ((line = br.readLine()) != null) {
                highScores.add(Integer.valueOf(line));
                System.out.println(line);
            }
        }catch (Exception e){

        }
    }
}
