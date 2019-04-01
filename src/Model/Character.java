package Model;
import View.Sprite;

public class Character {
    int x, y;
    int width, height;
    Sprite sprite;
    enum direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
