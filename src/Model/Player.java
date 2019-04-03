package Model;

import java.awt.event.KeyEvent;

public class Player extends Character {


    public Player(int x, int y){

    }
    public void setSprite(String s){

    }
    public int getDirection(){
        return this.direction;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -4;
            direction = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 4;
            direction = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -4;
            direction = 1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 4;
            direction = 3;
        }
    }
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
