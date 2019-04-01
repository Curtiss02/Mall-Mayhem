package Controller;


import View.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    //Setup game timer, each game tick the game is refreshed and certain logic is calculated
    private Timer gameTimer;
    //Sets refresh rate, 16ms ~= 60fps
    private final int TICK_DELAY = 16;
    private int frameNum = 0;
    GUIPanel view;

    public GameController(){

        gameTimer = new Timer(TICK_DELAY, tick);
        gameTimer.start();

    }

    public void setView(GUIPanel view) {
        this.view = view;
    }

    //This is the function that is called every tick, is used for updating GUI elements and processing game information
    ActionListener tick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            view.tick();


        }
    };
}
