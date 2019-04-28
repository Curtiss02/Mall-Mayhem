package View;

import Controller.GameController;

import java.awt.EventQueue;




public class Main {
    public static void main(String[] args){


        GameFrame mainFrame = new GameFrame();
        GUIPanel mainPanel = mainFrame.getMyGUIPanel();
        GameController mainController = new GameController();
        mainController.setView(mainPanel);
        mainController.start();



    }
}
