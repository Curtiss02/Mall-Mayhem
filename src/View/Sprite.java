package View;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;

public class Sprite {
    //
    private int x, y;
    private int width, height;
    private boolean isVisible;
    private BufferedImage image;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        isVisible = true;
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

    //Will draw the image stored in the sprite onto the graphics canvas passed through
    //Should oly be accessed from View
    public void drawSprite(Graphics2D g){
        g.drawImage(image, x, y, null);
    }

    //Loads the image specified in s to a BufferedImage
    public void setImage(String s){

        try {
            image = ImageIO.read(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
