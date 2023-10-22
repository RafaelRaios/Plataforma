package main;

import javax.swing.*;

public class Window extends JFrame {
    public Window(GamePanel gamePanel) {
        this.setSize(400, 400);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.add(gamePanel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }


}
