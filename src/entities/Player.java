package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstant.*;
import static utils.HelpMethods.CanMoveHere;
import static utils.HelpMethods.GetEntityXPosNextToWall;
import static utils.HelpMethods.GetEntityYUnderRoof;
import static utils.HelpMethods.IsEntityOnFloor;
//import static utils.LoadSave.*;
public class Player extends Entitie {

    //Private
    private BufferedImage[][] animation;
    private int animationJ = 0;
    private int aniTick = 0, aniSpeed = 15;
    private int playerAction = IDLE;
    private float playerSpeed = 2.0f;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallAfterCollision = 0.5f * Game.SCALE;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private boolean inAir = false;
    private int[][] lvldata;


    public Player(float x, float y, int width, int heigth) {
        super(x, y, width, heigth);
        loadAnimation1();
        initHitbox(x, y, 20* Game.SCALE, 27*Game.SCALE);
    }

    public void update() {
        updatePostion();
        updateAnimationTick();
        setAnimation();


    }

    public void render(Graphics g) {
        g.drawImage(animation[playerAction][animationJ], (int)( hitbox.x - xDrawOffset),(int)( hitbox.y - yDrawOffset), width, height, null);
        drawHItbox(g);

    }

    private void loadAnimation1() {
        animation = new BufferedImage[9][6];
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                animation[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

    }

    public void loadLevelData(int[][] lvldata) {

        this.lvldata = lvldata;
        if (!IsEntityOnFloor(hitbox, lvldata)) {
            inAir = true;
        }
    }

    public void updateAnimationJ() {
        int tam = GetSpriteAmount(playerAction);
        animationJ++;
        if (animationJ >= tam) animationJ = 0;
    }

    private void updateAnimationTick() {

        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;

            animationJ++;
            if(animationJ >= GetSpriteAmount(playerAction)) {
                animationJ = 0;
                updateAnimationJ();
                attacking = false;
            }

        }

    }


    public void setAnimation() {
        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;

        }

        if (inAir) {
            if (airSpeed < 0) playerAction = JUMPING;
            else playerAction = FALLING;
        }

        if (attacking) {
            playerAction = ATTACK1;

        }

        if (startAnimation != playerAction) animationJ = 0;
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    private void updatePostion() {
        moving = false;
        if (jump) jump();
        if (!left && !right && !inAir) return;

        float xSpeed = 0;

        if (left) {
            xSpeed = -playerSpeed;

        }

        if (right) {
            xSpeed = playerSpeed;

        }

        if(inAir) {

            if(CanMoveHere(hitbox.x, hitbox.y  + airSpeed, hitbox.width, hitbox.height, lvldata)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);

            }
            else {
                hitbox.y = GetEntityYUnderRoof(hitbox, airSpeed);

                if(airSpeed > 0) {
                    resetAir();
                }
                else {
                    airSpeed = fallAfterCollision;
                }
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }


        moving = true;
        //}
    }

    private void jump() {
        if(inAir) {
            return;
        }

        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, lvldata)) {
          hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void resetDirBooleans() {
        right = false;
        left = false;
        up = false;
        down = false;
    }


    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }
}

