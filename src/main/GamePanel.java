package main;

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
public class GamePanel extends JPanel {
    //FLOATS
    private float deltaX = 100;
    private float deltaY = 100;


    //INTS
    private int animationJ = 0;
    private int aniTick = 0, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;

    //BOOLEAN
    private boolean moving = false;

    //CLASSES
    private MouseInputs mouseInputs;
    private BufferedImage img;
    private BufferedImage[][] animation;


    //OTHERS
    private long lastCheck = 0;

    public GamePanel()  {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimation1();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    private void setPanelSize() {
        Dimension size = new Dimension(1200, 600);
        setMaximumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    private void loadAnimation1() {
        animation = new BufferedImage[9][6];

        for(int i = 0; i < 9; i++)  {
            for (int j = 0; j < 6; j++) {
                animation[i][j] = img. getSubimage(j * 64, i *40, 64, 40);
            }
        }

    }

    private void importImg() {
        try {
            img = ImageIO.read(new File("res/player_sprites.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePostion();
    }

    public void updateAnimationJ() {
        int tam = GetSpriteAmount(playerAction);
        animationJ++;
        if (animationJ >= tam) animationJ = 0;
    }

    private void updateAnimationTick() {

    aniTick++;

    if(aniTick >= aniSpeed) {
        aniTick = 0;
        updateAnimationJ();
    }

    }

    public void setDirection(int x) {
        this.playerDirection = x;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        }
        else {
            playerAction = IDLE;

        }
    }

    private void updatePostion() {
        if(moving) {
            switch (playerDirection) {
                case LEFT:
                    deltaX -= 3;
                    break;
                case RIGHT:
                    deltaX += 3;
                    break;
                case UP:
                    deltaY -= 3;
                    break;
                case DOWN:
                    deltaY += 3;
                    break;
            }

        }
    }

    public void paintComponent(Graphics g)  {
        super.paintComponent(g);

        g.drawImage(animation[playerAction][animationJ], (int) deltaX , (int) deltaY, null);


    }


}
