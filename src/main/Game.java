package main;

import entities.Player;
import gamestates.GameState;
import gamestates.Playing;
import gamestates.Menu;
import levels.Level;
import levels.LevelManager;

import java.awt.*;
import java.util.Random;

public class Game implements Runnable {
    private Window window;
    private GamePanel gamePanel;
    private Thread gameThread;
    private Player player;
    private LevelManager levelManager;
    private Playing playing;
    private Menu menu;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private int frames = 0;
    private int updates = 0;


    //public
    public final static float SCALE = 1.5f;
    public final static int TILE_DEFAULT_SIZE = 32;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = (TILES_IN_WIDTH * TILES_SIZE);
    public final static int GAME_HEIGHT = (TILES_IN_HEIGHT * TILES_SIZE);


    public Game() {
        initClases();

        gamePanel = new GamePanel(this);
        window = new Window(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClases() {
            menu = new Menu(this);
            playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        switch (GameState.state) {

            case PLAYING -> {
                playing.update();
            }
            case MENU -> {
               menu.update();
            }

        }

    }

    public void render(Graphics g) {

        switch (GameState.state) {

            case PLAYING -> {
                playing.draw(g);
            }
            case MENU -> {
                menu.draw(g);
            }

        }
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
    public void windowFocusLost() {

        if (GameState.state == GameState.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {return menu;}
    public Playing getPlaying() {return playing;}


}
