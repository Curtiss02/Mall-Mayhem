package View;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Sprite {
    //
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private ImageIcon image;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
    }

    public Sprite(double x, double y){
        this.x = (int)x;
        this.y = (int)y;
        isVisible = true;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setX(double x){
        this.x = (int)x;
    }

    public void setY(double y){
        this.y = (int)y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Image getImage() {
        return image.getImage();
    }

    //Will draw the image stored in the sprite onto the graphics canvas passed through
    //Should oly be accessed from View
    public void drawSprite(Graphics2D g){


        g.drawImage(image.getImage(), x, y, null);
    }


    //Loads the image specified in s to a BufferedImage
    public void setImage(String s){

        image = new ImageIcon(s);

        width = image.getIconWidth();
        height = image.getIconHeight();
    }
}
