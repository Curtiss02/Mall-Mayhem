package Model;

public abstract class Projectile extends Character {

        private int damage;
        private boolean isPlayer;
        private boolean isEnemy;

        public int getDamage(){
            return damage;
        }

        public abstract void move();

}
