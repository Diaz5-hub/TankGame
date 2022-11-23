package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//lets window properly close when user clicks ("x") button
        window.setResizable(false); //not resizable
        window.setTitle("2D Tank");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();  //used for this window to be sized to fit the preferred size and layouts of its subcomp(GamePanel)


        window.setLocationRelativeTo(null); //window be displayed at center of the screen
        window.setVisible(true);    //so we can see this window

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
