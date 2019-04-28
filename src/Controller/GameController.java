package Controller;


import Audio.MusicPlayer;
import Audio.SoundPlayer;
import Model.Character;
import View.*;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Iterator;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;



public class GameController {

    //Setup game timer, each game tick the game is refreshed and certain logic is calculated


    private final int FPS = 60;
    private int UPS = 120;

    //Defines whether the game is running or not, used for the main game loop
    private boolean running;

    private Player player;

    //Must load the view into the controller
    private GUIPanel view;

    private int score = 0;
    private int globalTimer = 300;


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

    private SoundPlayer soundPlayer = new SoundPlayer();
    private MusicPlayer musicPlayer = new MusicPlayer();

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

                if(!isPaused && GUIPanel.State == GUIPanel.STATE.GAME) {
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
                if(GUIPanel.State == GUIPanel.STATE.GAME && globalTimer > 0){
                    globalTimer -= 1;
                    view.setTimer(globalTimer);
                }
                if(globalTimer == 0){
                    gameOver();

                }
                frames = 0;
                ticks = 0;
                timer += 1000;

            }
        }
    }


    private void init(){
        //Create a new player

        musicPlayer.playMusic("sounds/mallmusic.wav");
        spriteList = new ArrayList<>();
        levelList = new ArrayList<>();
        levelList.add(new StartLevel());
        levelList.add(new Level2());
        levelList.add(new Level3());
        levelList.add(new BossLevel());

        musicPlayer = new MusicPlayer();
        levelIndex = 0;
        currentLevel = levelList.get(levelIndex);
        enemyList = currentLevel.getEnemyList();
        player = new Player(currentLevel.getPlayerStart().x,currentLevel.getPlayerStart().y);
        pickupList = currentLevel.getPickupList();
        currentMap = currentLevel.getMap();




        view.setSpriteList(spriteList);
        view.setCurrentMap(currentMap);

    }



    // Will evetually include function which will tick() trough every currentl used entity
    private void update(){


        projectileList = Character.getProjectileList();

        levelSpecificChecks();

        moveEnemies();

        moveProjectiles();

        checkCollisions();

        player.tick();

        if(player.getHealthPoints() <= 0){
            gameOver();
        }

        tickEnemies();

        tickProjectiles();

        cleanupEnemies();

        updateSprites();

        if(SettingsMenu.soundOn == 1) {
            playSounds();
        }

        checkMusic();

        updateGUI();

        if(endGameTimer > 1200){
            System.exit(0);
        }

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

        checkProjectileCollisions();

        checkPlayerMapCollisions();






        checkPlayerPickupCollision();





        checkPlayerEnemyCollisions();


        checkEnemyLevelCollisions();







    }

    private void checkPlayerMapCollisions(){
        Rectangle playerBound = player.getFutureBounds();
        //Check player and level transition tile collisions
        for(Rectangle thisTile : currentMap.getNextLevelCollision()){
            if(playerBound.intersects(thisTile)){
                nextLevel();
                break;
            }
        }
        for(Rectangle thisTile : currentMap.getPrevLevelCollisions()){
            if(playerBound.intersects(thisTile)){
                prevLevel();
                break;
            }
        }

        List<Rectangle> levelCollisions = currentMap.getCollisions();

        //Check bounds in x and y direction to figue out which direction the collision occurs to smooth movement
        Rectangle playerXBounds = player.getFutureBoundsX();
        Rectangle playerYBounds = player.getFutureBoundsY();
        //Stop the player from walking through tiles on the collision layer
        for(int i = 0; i < levelCollisions.size(); i++){
            Rectangle currentTile = levelCollisions.get(i);
            if(playerBound.intersects(currentTile)){
                if(playerXBounds.intersects(currentTile)){
                    player.stopX();
                }
                if(playerYBounds.intersects(currentTile)){
                    player.stopY();
                }
            }
        }
    }

    private void checkPlayerPickupCollision(){

        Rectangle playerBound = player.getFutureBounds();
        //Check player-pickup collisions
        Iterator<Pickup> pickupIterator = pickupList.iterator();
        while(pickupIterator.hasNext()){
            Pickup thisPickup = pickupIterator.next();
            if(playerBound.intersects(thisPickup.getCollisionBox())){
                switch (thisPickup.getType()){
                    case HEALTH:
                        player.addHealth(10);
                        break;
                    case SUPERSHOT:
                        player.setSuperShot(true);
                        break;
                }
                pickupIterator.remove();

            }
        }
    }

    private void checkPlayerEnemyCollisions(){
        Rectangle playerBound = player.getFutureBounds();
        //If the playe currently has collision disabled, we dont need to check
        if(!player.isInvulnerable()) {
            for (Enemy thisEnemy : enemyList) {
                Rectangle enemyBound = thisEnemy.getFutureBounds();
                if (playerBound.intersects(enemyBound)) {

                    //Should probably not stop player in event of enemy collision, just take damage + invuln for small time
                    //Temporary just for now
                    player.takeDamage(thisEnemy.getDamage());
                    score -= thisEnemy.getDamage();
                    //int xDir = (player.getX() - thisEnemy.getX()) / Math.abs(player.getX() - thisEnemy.getX());
                    //int yDir = (player.getY() - thisEnemy.getY()) / Math.abs(player.getY() - thisEnemy.getY());

                    //player.knockBack(xDir, yDir, 20);

                    player.setInvulnerable(true);
                    break;
                }
            }
        }
    }

    private void checkEnemyLevelCollisions(){
        List<Rectangle> levelCollisions = currentMap.getCollisions();

        for(Enemy thisEnemy : enemyList){
            Rectangle enemyBounds = thisEnemy.getFutureBounds();
            Rectangle enemyXBounds = thisEnemy.getFutureBoundsX();
            Rectangle enemyYBounds = thisEnemy.getFutureBoundsY();
            for(Rectangle currentTile : levelCollisions){
                if(enemyBounds.intersects(currentTile)) {
                    if (enemyXBounds.intersects(currentTile)) {
                        thisEnemy.stopX();

                    }
                    if (enemyYBounds.intersects(currentTile)) {
                        thisEnemy.stopY();
                    }
                    thisEnemy.setStuck(true);
                    break;

                }
                else{

                    thisEnemy.setStuck(false);

                }
            }
        }
    }

    private void checkProjectileCollisions(){
        //Check the projectile collisions
        Rectangle playerBound = player.getFutureBounds();
        for(Projectile thisProjectile : projectileList){

            double x = thisProjectile.getX();
            double y = thisProjectile.getY();
            int width = thisProjectile.getWidth();
            int height = thisProjectile.getHeight();
            Rectangle projectileBounds = thisProjectile.getBounds();
            if(isOffScreen((int)x, (int)y, width, height)){

                thisProjectile.takeDamage(99);
            }

            //Check against enemies if the projectile belongs to the player
            if(thisProjectile.isPlayer()){
                for(Enemy thisEnemy : enemyList){
                    Rectangle enemyBounds = thisEnemy.getFutureBounds();
                    if(projectileBounds.intersects(enemyBounds)){
                        if(thisEnemy.isInvulnerable() == false){
                            thisEnemy.takeDamage(thisProjectile.getDamage());
                            score += thisEnemy.getDamage()*2;
                        }
                        thisProjectile.takeDamage(99);
                        break;
                    }
                }
            }
            //Checks agains the player if the projectile belongs to the enemy
            if(thisProjectile.isEnemy()){

                if(projectileBounds.intersects(playerBound)){
                    if(player.isInvulnerable() == false){
                        player.takeDamage(thisProjectile.getDamage());
                        player.knockBack(thisProjectile.getXDirection(), thisProjectile.getYDirection(), 5);
                    }
                    thisProjectile.takeDamage(99);
                    break;
                }
            }
        }
    }

    private void moveEnemies(){
        Enemy.setPlayerX(player.getX());
        Enemy.setPlayerY(player.getY());
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
        if(keyPresses[KeyEvent.VK_ESCAPE]){
            System.exit(0);
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
            if (keyPresses[KeyEvent.VK_PAGE_DOWN]){
                skipToEnd();
            }

        }




    }

    private void playSounds(){
        List<String> soundList = Character.getSoundList();
        for(String s : soundList){
            soundPlayer.playSound(s);
        }
        soundList.clear();
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






    private void nextLevel(){

        levelIndex++;
        updateonLevelChange();
    }
    private void prevLevel(){
        levelIndex--;
        updateonLevelChange();
        player.setX(currentLevel.getPlayerExit().x);
        player.setY(currentLevel.getPlayerExit().y);

    }
    private void setLevel(int level, boolean isExit){
        levelIndex = level;
        updateonLevelChange();
    }
    private void updateonLevelChange(){
        currentLevel = levelList.get(levelIndex);
        currentMap = currentLevel.getMap();
        enemyList = currentLevel.getEnemyList();
        pickupList = currentLevel.getPickupList();
        player.setX(currentLevel.getPlayerStart().x);
        player.setY(currentLevel.getPlayerStart().y);
        projectileList.clear();
        view.setCurrentMap(currentMap);
    }

    private boolean level3Flag = false;

    private void levelSpecificChecks(){
        if(levelIndex == 2){
            if (!level3Flag && enemyList.isEmpty()){
                pickupList.add(new Pickup(600,600, Pickup.TYPE.SUPERSHOT));
                level3Flag = true;
            }
        }
        if(levelIndex == 3){
            if(enemyList.isEmpty()) {
                gameWon();

            }

        }
    }

    private boolean skipFlag = false;

    private void skipToEnd(){
        if(!skipFlag) {
            levelIndex = 3;
            player.setSuperShot(true);
            player.addHealth(100);
            updateonLevelChange();
            skipFlag = true;
        }

    }

    private int endGameTimer = 0;


    private int finalScore;
    private void scoreScreen(){
        player.setInvulnerable(true);
        view.setScore(finalScore);
        view.enableScoreScreen();



    }
    private boolean gameoverdoneOnce = false;

    private void gameWon(){
        if(!gameoverdoneOnce) {
            globalTimer = -1;
            finalScore = (int) (score * (5 + (0.00F * globalTimer)));
            HighScoreMenu.addHighScore(finalScore);
            view.setGameWon(true);
            scoreScreen();
            gameoverdoneOnce = true;
        }
    }

    private void gameOver(){
        if(!gameoverdoneOnce) {
            globalTimer = -1;
            view.setTimer(0);
            view.setGameOver(true);
            finalScore = (int) (score * (5));
            soundPlayer.playSound("sounds/lose.wav");
            HighScoreMenu.addHighScore(finalScore);
            scoreScreen();
            player.setY(99999);
            player.setX(99999);
            player.getSprite().setVisible(false);
            gameoverdoneOnce = true;
        }

    }

    private void checkMusic(){
        if(SettingsMenu.musicOn == 0){
            musicPlayer.pause();
        }
        else{
            musicPlayer.resume();
        }
    }





}

