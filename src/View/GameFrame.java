package View;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GUIPanel myGUIPanel;
    public GameFrame(){
        super("Mall Mayhem");
        //EventQueue.invokeLater(() -> {
            myGUIPanel = new GUIPanel();
        //});
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

    public GUIPanel getMyGUIPanel() {
        return myGUIPanel;
    }
}
