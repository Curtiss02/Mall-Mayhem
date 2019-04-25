package Controller;


import View.*;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;


public class GameController {

    //Setup game timer, each game tick the game is refreshed and certain logic is calculated
    private Timer gameTimer;
    //Sets refresh rate, 16ms ~= 60fps
    private final int TICK_DELAY = 16;
    private final int FPS = 60;
    private int UPS = 120;

    //Defines whether the game is running or not, used for the main game loop
    private boolean running;

    private Player player;

    //Must load the view into the controller
    private GUIPanel view;



    private Map currentMap;

    //Create arraylists for game objects, is shared with the view
    private List<Sprite> spriteList;

    // Keep an list of enemy
    private List<Enemy> enemyList;

    private List<Projectile> projectileList;

    private List<Pickup> pickupList;

    private List<Level> levelList;
    private int levelIndex;
    private Level currentLevel;

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





        spriteList = new ArrayList<Sprite>();
        levelList = new ArrayList<>();


        levelList.add(new StartLevel());
        levelIndex = 0;
        currentLevel = levelList.get(levelIndex);
        enemyList = currentLevel.getEnemyList();
        player = new Player(currentLevel.getPlayerStart().x,currentLevel.getPlayerStart().y);
        projectileList = player.getProjectileList();
        pickupList = currentLevel.getPickupList();
        currentMap = currentLevel.getMap();



        view.setSpriteList(spriteList);
        view.setCurrentMap(currentMap);

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
        updatePickupSprites();
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

    private void updatePickupSprites(){
        for(Pickup thisPickup : pickupList){
            spriteList.add(thisPickup.getSprite());
        }
    }

    private void moveProjectiles(){

        for(Projectile thisProjectile : projectileList){
            thisProjectile.move();
        }
    }
    private void checkCollisions(){

        Rectangle playerBound = player.getFutureBounds();



        //Check player and level transition tile collisions
        for(Rectangle thisTile : currentMap.getNextLevelCollision()){
            if(playerBound.intersects(thisTile)){
                nextLevel();
            }
        }






        //If the playe currently has collision disabled, we dont need to check
        if(!player.isInvulnerable()) {
            for (Enemy thisEnemy : enemyList) {
                Rectangle enemyBound = thisEnemy.getFutureBounds();
                if (playerBound.intersects(enemyBound)) {

                    //Should probably not stop player in event of enemy collision, just take damage + invuln for small time
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
        List<Rectangle> levelCollisions = currentMap.getCollisions();
        //Stop the player from walking through tiles on the collision layer
        for(int i = 0; i < levelCollisions.size(); i++){
            Rectangle currentTile = levelCollisions.get(i);
            if(playerBound.intersects(currentTile)){
                player.stop();
            }
        }

        //Check enemy level collisions
        for(Enemy thisEnemy : enemyList){
            Rectangle enemyBounds = thisEnemy.getFutureBounds();
            for(Rectangle currentTile : levelCollisions){
                if(enemyBounds.intersects(currentTile)){
                    thisEnemy.stop();

                    thisEnemy.setStuck(true);

                    break;

                }
                else{

                    thisEnemy.setStuck(false);

                }
            }
        }

        //Check the projectile collisions
        for(Projectile thisProjectile : projectileList){

            double x = thisProjectile.getX();
            double y = thisProjectile.getY();
            int width = thisProjectile.getWidth();
            int height = thisProjectile.getHeight();
            Rectangle projectileBounds = thisProjectile.getBounds();
            if(isOffScreen((int)x, (int)y, width, height)){
                System.out.println("OFF");
                thisProjectile.takeDamage(99);
            }

            //Check against enemies if the projectile belongs to the player
            if(thisProjectile.isPlayer()){
                for(Enemy thisEnemy : enemyList){
                    Rectangle enemyBounds = thisEnemy.getFutureBounds();
                    if(projectileBounds.intersects(enemyBounds)){
                        if(thisEnemy.isInvulnerable() == false){
                            thisEnemy.takeDamage(thisProjectile.getDamage());
                        }
                        thisProjectile.takeDamage(99);
                    }
                }
            }
            //Checks agains the player if the projectile belongs to the enemy
            if(thisProjectile.isEnemy()){

                if(projectileBounds.intersects(playerBound)){
                    if(player.isInvulnerable() == false){
                        player.takeDamage(thisProjectile.getDamage());
                    }
                    thisProjectile.takeDamage(99);
                }
            }
        }
    }

    private void moveEnemies(){
        for(Enemy thisEnemy : enemyList){
            thisEnemy.stop();
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


    private void IntroLevel() {

    }

    private void level2(){
        spriteList.clear();
        enemyList.clear();
        projectileList.clear();
        player.setX(1);
        player.setY(1);

    }

    private void nextLevel(){
        levelIndex++;
        currentLevel = levelList.get(levelIndex);
        currentMap = currentLevel.getMap();
        enemyList = currentLevel.getEnemyList();
        pickupList = currentLevel.getPickupList();
        view.setCurrentMap(currentMap);
    }






}

