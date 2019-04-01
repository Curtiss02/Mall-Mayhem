package View;

import Controller.GameController;

import java.awt.EventQueue;


public class Main {
    public static void main(String[] args){

        EventQueue.invokeLater(() -> {
            GameFrame mainFrame = new GameFrame();
            GameController controller = new GameController();
            controller.setView(mainFrame.myGUIPanel);

        });



    }
}
