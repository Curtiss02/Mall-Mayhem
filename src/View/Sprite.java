package View;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    //
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private Image image;

    public Sprite(){

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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
