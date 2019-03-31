package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPanel extends JPanel {

    //Define game window width and height
    private final int GAME_WIDTH = 1400;
    private final int GAME_HEIGHT = 900;

    //Setup GUI Timer. Used for refreshing the screen
    private Timer GUITimer;
    //Sets refresh rate, 16ms ~= 60fps
    private final int FRAME_DELAY = 16;
    private int frameNum = 0;



    public GUIPanel(){

        this.setVisible(true);
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        setBackground(Color.white);
        //Panel must be focusable in order to accept user input
        setFocusable(true);

        GUITimer = new Timer(FRAME_DELAY, gameTimer);
        GUITimer.start();

    }




    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawFrames(g);
        Toolkit.getDefaultToolkit().sync();



    }
    //This is the function that is called every frame, is used for updating GUI elements
    ActionListener gameTimer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
            if (frameNum > 60){
                frameNum = 0;
            }
            else{
                frameNum++;
            }
        }
    };

    private void drawFrames(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.drawString(Integer.toString(frameNum), getWidth()/2, getHeight()/2);
    }


}
