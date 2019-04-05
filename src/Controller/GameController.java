package Controller;


import View.*;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;


public class GameController {

    //Setup game timer, each game tick the game is refreshed and certain logic is calculated
    private Timer gameTimer;
    //Sets refresh rate, 16ms ~= 60fps
    private final int TICK_DELAY = 16;
    private final int FPS = 60;
    private final int UPS = 120;
    private int frameNum = 0;
    private boolean running;


    private Player player;
    GUIPanel view;


    //Create arraylists for game objects
    List<Sprite> spriteList;

    public GameController() {


    }

    public void setView(GUIPanel view) {
        this.view = view;
    }

    public void start() {
        running = true;
        init();
        gameLoop();
    }

    public void gameLoop() {

        long startTime = System.nanoTime();
        final double updateTime = 1000000000 / UPS;
        final double frameTime = 1000000000 / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();


        while (running) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - startTime) / updateTime;
            deltaF += (currentTime - startTime) / frameTime;
            startTime = currentTime;

            if (deltaU >= 1) {

                getInput();
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                view.Update();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                view.UpdateFPS(frames, ticks);
                System.out.println(String.format("Ticks/second: %s, FPS: %s", ticks, frames));

                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }


    private void init(){

        player = new Player(100, 100);

        spriteList = new ArrayList<Sprite>();

        spriteList.add(player.getSprite());

//        for(int i = 0; i < 100; i++){
//            Random rand = new Random();
//
//            int rand_y = rand.nextInt(1440 + 1);
//            int rand_x = rand.nextInt(900 + 1);
//            Sprite newSprite = new Sprite(rand_x, rand_y);
//            newSprite.setImage("src/img/blackblob.png");
//
//            spriteList.add(newSprite);
//
//        }

        view.setSpriteList(spriteList);
    }


    private void update(){
        //tickSprites();
        player.tick();
    }

    private void getInput(){

        Boolean[] keyPresses =  view.getKeyPresses();

        player.stop();

        if (keyPresses[KeyEvent.VK_LEFT]) {
            player.moveLeft();
        }
        if (keyPresses[KeyEvent.VK_RIGHT]) {
            player.moveRight();
        }
        if (keyPresses[KeyEvent.VK_UP]) {
            player.moveUp();
        }
        if (keyPresses[KeyEvent.VK_DOWN]) {
            player.moveDown();
        }



    }


    private void tickSprites(){


        for(int i = 0; i < spriteList.size(); i++){


            Random rand = new Random();
            int max = 6;
            int min = -5;
            int rand_y = rand.nextInt(max + 1 -min) + min;
            int rand_x = rand.nextInt(max + 1 -min) + min;

            spriteList.get(i).setX(spriteList.get(i).getX() + rand_x);
            spriteList.get(i).setY(spriteList.get(i).getY() + rand_y);


            if(spriteList.get(i).getX() > 1440){
                spriteList.get(i).setVisible(false);
            }
            else if(spriteList.get(i).getX() < 0){
                spriteList.get(i).setVisible(false);
            }
            if(spriteList.get(i).getY() > 900){
                spriteList.get(i).setVisible(false);
            }
            else if(spriteList.get(i).getY() < 0){
                spriteList.get(i).setVisible(false);
            }

        }


    }
}

