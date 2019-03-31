package View;

import javax.swing.*;

public class GameFrame extends JFrame {

    private GUIPanel myGUIPanel;
    public GameFrame(){
        super("Mall Mayhem");
        myGUIPanel = new GUIPanel();

        start();

    }
    public void start(){
        setVisible(true);
        buildGUI();
    }
    public void buildGUI(){
        add(myGUIPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }
}
