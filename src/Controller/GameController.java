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
import java.util.concurrent.RecursiveAction;


public class GameController {

    //Setup game timer, each game tick the game is refreshed and certain logic is calculated
    private Timer gameTimer;
    //Sets refresh rate, 16ms ~= 60fps
    private final int TICK_DELAY = 16;
    private final int FPS = 60;
    private final int UPS = 120;

    //Defines whether the game is running or not, used for the main game loop
    private boolean running;

    private Player player;

    //Must load the view into the controller
    private GUIPanel view;


    //Create arraylists for game objects, is shared with the view
    private List<Sprite> spriteList;

    // Keep an list of enemy
    private List<Enemy> enemyList;


    public GameController() {}

    public void setView(GUIPanel view) {
        this.view = view;
    }

    public void start() {
        running = true;
        init();
        gameLoop();
    }

    private void gameLoop() {

        long startTime = System.nanoTime();
        final double updateTime = (double)1000000000 / UPS;
        final double frameTime = (double)1000000000 / FPS;
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
        //Create a new player

        Level testLevel = new Level();

        testLevel.loadXML("src/maps/room1.xml");


        player = new Player(100, 100);

        spriteList = new ArrayList<Sprite>();
        enemyList = new ArrayList<Enemy>();
        //Add to list of sprites being drawn
        spriteList.add(player.getSprite());


        Shopper testShopper = new Shopper(250, 250);
        testShopper.addPatrolPoint(new Point(500, 500));
        testShopper.addPatrolPoint(new Point(300, 100));
        testShopper.addPatrolPoint(new Point(900, 700));

        Shopper shopper2 = new Shopper(900, 900);
        shopper2.addPatrolPoint(new Point(50,100));
        shopper2.addPatrolPoint(new Point(100,100));
        shopper2.addPatrolPoint(new Point(200,0));
        shopper2.addPatrolPoint(new Point(394,983));
        shopper2.addPatrolPoint(new Point(900,700));

        enemyList.add(testShopper);
        enemyList.add(shopper2);

        spriteList.add(testShopper.getSprite());
        spriteList.add(shopper2.getSprite());
        view.setSpriteList(spriteList);
    }

    // Will evetually include function which will tick() trough every currentl used entity
    private void update(){

        moveEnemies();

        checkCollisions();

        player.tick();

        tickEnemies();
    }



    private void checkCollisions(){
        Rectangle playerBound = player.getFutureBounds();
        //If the playe currently has collision disabled, we dont need to check
        if(!player.isInvulnerable()) {
            for (Enemy thisEnemy : enemyList) {
                Rectangle enemyBound = thisEnemy.getFutureBounds();
                if (playerBound.intersects(enemyBound)) {

                    //Should probably not stop player in event of enemy collision, just take damage + invuln for small time
                    System.out.println("PLAYER HIT");
                    //Temporary just for now

                    //int xDir = (player.getX() - thisEnemy.getX()) / Math.abs(player.getX() - thisEnemy.getX());
                    //int yDir = (player.getY() - thisEnemy.getY()) / Math.abs(player.getY() - thisEnemy.getY());

                    //player.knockBack(xDir, yDir, 20);

                    player.setInvulnerable(true);
                    break;
                }
            }
        }

        for(int i = 0; i < enemyList.size(); i++) {
            if (enemyList.get(i).hasCollision()) {
                Rectangle firstEnemyBounds = enemyList.get(i).getFutureBounds();
                for (int j = i + 1; j < enemyList.size(); j++) {
                    Rectangle secondEnemyBound = enemyList.get(j).getFutureBounds();
                    if (firstEnemyBounds.intersects(secondEnemyBound)) {

                        enemyList.get(i).stop();
                        break;
                    }
                }
            }
        }
    }

    private void moveEnemies(){
        for(Enemy thisEnemy : enemyList){
            thisEnemy.move();
        }
    }

    //Updates enemy coords after verifying collision
    private void tickEnemies(){

        for (Enemy thisEnemy  : enemyList) {
            thisEnemy.tick();
        }

    }

    private void getInput(){

        //Contains information on current status of keyboard input
        Boolean[] keyPresses =  view.getKeyPresses();

        player.stop();

        if (keyPresses[KeyEvent.VK_LEFT]) {
            player.moveLeft();        tickSprites();

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
        int i = 0;
        while(i < spriteList.size()){
            if(spriteList.get(i).isVisible() == false){
                spriteList.remove(i);
            }
            else{
                i++;
            }
        }


    }
    private void createClusterFuck(){
        for(int i = 0; i < 10000; i++){
            Random rand = new Random();

            int rand_y = rand.nextInt(1440 + 1);
            int rand_x = rand.nextInt(900 + 1);
            Sprite newSprite = new Sprite(rand_x, rand_y);
            newSprite.setImage("src/img/blackblob.png");

            spriteList.add(newSprite);

        }
    }
}

