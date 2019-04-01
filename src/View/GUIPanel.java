package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPanel extends JPanel {

    //Define game window width and height
    private final int GAME_WIDTH = 1400;
    private final int GAME_HEIGHT = 900;
    private int frameNum = 0;




    public GUIPanel(){

        this.setVisible(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        setBackground(Color.white);
        //Panel must be focusable in order to accept user input
        setFocusable(true);


    }




    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        drawFrames(g);
        Toolkit.getDefaultToolkit().sync();



    }


    private void drawFrames(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.drawString(Integer.toString(frameNum), getWidth()/2, getHeight()/2);
    }
    public void tick() {
        repaint();
        if (frameNum > 60) {
            frameNum = 0;
        } else {
            frameNum++;
        }
    }

}
