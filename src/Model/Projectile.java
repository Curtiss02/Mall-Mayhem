package Model;

public abstract class Projectile extends Character {

        protected int damage;
        protected boolean isPlayer;
        protected boolean isEnemy;


        public boolean isPlayer() {
                return isPlayer;
        }

        public boolean isEnemy() {
                return isEnemy;
        }

        public int getDamage(){
            return damage;
        }

        public abstract void move();

}
