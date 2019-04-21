package Controller;


import View.*;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
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



    private Level currentLevel;

    //Create arraylists for game objects, is shared with the view
    private List<Sprite> spriteList;

    // Keep an list of enemy
    private List<Enemy> enemyList;

    private List<Projectile> projectileList;

    private boolean isPaused = false;

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
                if(!isPaused) {
                    update();

                }
                ticks++;
                deltaU--;

            }

            if (deltaF >= 1) {
                if(!isPaused) {
                    view.Update();
                }
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

        Level testLevel = new Level("src/maps/intro.xml");

        this.currentLevel = testLevel;
        view.setCurrentLevel(testLevel);

        player = new Player(300, 600);

        spriteList = new ArrayList<Sprite>();
        enemyList = new ArrayList<Enemy>();
        projectileList = new ArrayList<Projectile>();

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

        projectileList = player.getProjectileList();

        moveProjectiles();


        checkCollisions();

        player.tick();

        tickEnemies();

        tickProjectiles();

        cleanupEnemies();

        updateSprites();

        updateGUI();

    }
    private void cleanupEnemies(){
        Iterator<Enemy> enemyIterator = enemyList.iterator();
        while(enemyIterator.hasNext()){
            Enemy thisEnemy = enemyIterator.next();
            //System.out.println(thisEnemy.getHealthPoints());
            if(thisEnemy.getHealthPoints() <= 0 ){
                enemyIterator.remove();
            }

        }

    }
    private void updateSprites(){
        spriteList.clear();
        updateProjectileSprites();
        updateEnemySprites();
        spriteList.add(player.getSprite());
    }

    void tickProjectiles(){
        for(Projectile thisProjectile : projectileList){
            thisProjectile.tick();
        }

    }

    private void updateProjectileSprites(){
        for(Projectile thisProjectile : projectileList){
            spriteList.add(thisProjectile.getSprite());
        }
    }

    private void updateEnemySprites(){
        for(Enemy thisEnemy : enemyList){
            spriteList.add(thisEnemy.getSprite());
        }
    }

    private void moveProjectiles(){

        for(Projectile thisProjectile : projectileList){
            thisProjectile.move();
        }
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
                    player.takeDamage(thisEnemy.getDamage());
                    //int xDir = (player.getX() - thisEnemy.getX()) / Math.abs(player.getX() - thisEnemy.getX());
                    //int yDir = (player.getY() - thisEnemy.getY()) / Math.abs(player.getY() - thisEnemy.getY());

                    //player.knockBack(xDir, yDir, 20);

                    player.setInvulnerable(true);
                    break;
                }
            }
        }
        //CheckEnemyOnEnemyCollisions();
        //Check enemy collision against each other
/*        for(int i = 0; i < enemyList.size(); i++) {
            if (enemyList.get(i).hasCollision()) {
                Rectangle firstEnemyBounds = enemyList.get(i).getFutureBounds();
                for (int j = i + 1; j < enemyList.size(); j++) {
                    Rectangle secondEnemyBound = enemyList.get(j).getFutureBounds();
                    if (firstEnemyBounds.intersects(secondEnemyBound)) {

                        //enemyList.get(i).stop();
                        break;

                    }
                }
            }
        }*/

        //CheckPlayerLevelCollsions();
        List<Rectangle> levelCollisions = currentLevel.getCollisions();
        //Stop the player from walking through tiles on the collision layer
        for(int i = 0; i < levelCollisions.size(); i++){
            Rectangle currentTile = levelCollisions.get(i);
            if(playerBound.intersects(currentTile)){
                player.stop();
            }
        }

        //Check the projectile collisions
        for(Projectile thisProjectile : projectileList){

            int x = thisProjectile.getX();
            int y = thisProjectile.getY();
            int width = thisProjectile.getWidth();
            int height = thisProjectile.getHeight();
            Rectangle projectileBounds = thisProjectile.getBounds();
            if(isOffScreen(x, y, width, height)){
                thisProjectile.takeDamage(99);
            }

            //Check against enemies if the projectile belongs to the player
            if(thisProjectile.isPlayer()){
                for(Enemy thisEnemy : enemyList){
                    Rectangle enemyBounds = thisEnemy.getFutureBounds();
                    if(projectileBounds.intersects(enemyBounds)){
                        System.out.println("ENEMY HIT");
                        thisEnemy.takeDamage(thisProjectile.getDamage());
                        thisProjectile.takeDamage(99);
                    }
                }
            }
            //Checks agains the player if the projectile belongs to the enemy
            if(thisProjectile.isEnemy()){
                if(projectileBounds.intersects(player.getFutureBounds())){
                    player.takeDamage(thisProjectile.getDamage());
                    thisProjectile.takeDamage(99);
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


    private boolean pausePressed;
    private int pauseReleased = 0;
    private void getInput(){

        //Contains information on current status of keyboard input
        Boolean[] keyPresses =  view.getKeyPresses();

        player.stop();

        if(keyPresses[KeyEvent.VK_P]){
            if(pauseReleased == 0){
                isPaused = true;
            }
            if(pauseReleased > 0){
                isPaused = false;
            }


        }
        else{
            if(isPaused){
                pauseReleased++;
            }
            else{
                pauseReleased = 0;
            }

        }
        if(!isPaused) {
            if (keyPresses[KeyEvent.VK_A]) {
                player.moveLeft();
            } else if (keyPresses[KeyEvent.VK_D]) {
                player.moveRight();
            }
            if (keyPresses[KeyEvent.VK_W]) {
                player.moveUp();
            } else if (keyPresses[KeyEvent.VK_S]) {
                player.moveDown();
            }
            if (keyPresses[KeyEvent.VK_SHIFT]) {
                player.sprint();
            }
            if (keyPresses[KeyEvent.VK_SPACE]) {
                player.shoot();
            }
        }



    }

    private boolean isOffScreen(int x, int y, int width, int height){
        int screenWidth = view.getWidth();
        int screenHeight = view.getHeight();
        if((x < 0) || (x + width) > screenWidth || (y + height < 0) || (y > screenHeight)){
            return true;
        }
        return false;

    }
    private void updateGUI(){
        view.setPlayerHealth(player.getHealthPoints());
    }


}

