package Model;

import View.Sprite;

public abstract class Projectile extends Character {

        protected int damage;
        protected boolean isPlayer;
        protected boolean isEnemy;
        protected int speed;



        public Projectile(double x, double y, double xDir, double yDir){
                this.x = x;
                this.y = y;
                this.healthPoints = 1;
                xDirection = xDir;
                yDirection = yDir;
        }

        public boolean isPlayer() {
                return isPlayer;
        }

        public boolean isEnemy() {
                return isEnemy;
        }

        public int getDamage(){
            return damage;
        }

        @Override
        public void tick(){

                y += dy;
                x += dx;
                sprite.setX(x);
                sprite.setY(y);
        }
        public void move(){
                dy = speed * yDirection;
                dx = speed * xDirection;
        }

}
