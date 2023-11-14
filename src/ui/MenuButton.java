package ui;

import gamestates.GameState;
import utils.Constants;
import utils.LoadSave;
import utils.Constants.UI.Buttons;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MenuButton {
    private int xPos, yPos, rowIndex;
    private int xOffsetCenter = Buttons.B_WIDTH / 2;
    private int index = 0;
    private boolean mouseOver, mousePressed;
    private GameState state;
    private BufferedImage[] images;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.rowIndex = rowIndex;
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;
        LoadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, Buttons.B_WIDTH, Buttons.B_HEIGHT);
    }

    private void LoadImgs() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * Constants.UI.Buttons.B_WIDTH_DEFAULT, rowIndex * Buttons.B_HEIGHT_DEFAULT, Buttons.B_WIDTH_DEFAULT , Buttons.B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, Buttons.B_WIDTH, Buttons.B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
