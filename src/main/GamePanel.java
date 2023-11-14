package main;

import entities.Player;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstant.*;
import static utils.Constants.Directions.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
public class GamePanel extends JPanel {
    //FLOATS

    //INTS

    //BOOLEAN


    //CLASSES
    private MouseInputs mouseInputs;
    private KeyboardInputs keyInputs;
    private Game game;


    //OTHERS

    public GamePanel(Game game)  {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        keyInputs = new KeyboardInputs(this);

        setPanelSize();
        addKeyListener(keyInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g)  {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }


}
