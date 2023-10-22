package main;

import java.util.Random;

public class Game implements Runnable {
    private Window window;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private int frames = 0;
    private int updates = 0;


    public Game() {
        gamePanel = new GamePanel();
        window = new Window(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        gamePanel.updateGame();
    }

    @Override
    public void run() {

        double timePerUpdate = 1000000000.0 / UPS_SET;
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastCheck = System.currentTimeMillis();
        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {

                gamePanel.repaint();
                deltaF--;
                frames++;

            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                System.out.println("FPS: " + frames + "/ UPDATES: " + updates);
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }
}
