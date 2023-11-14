package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entitie {
    protected float x, y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;


    public Entitie(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;


    }

    protected  void drawHItbox(Graphics g) {
        g.setColor(Color.PINK);

        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }



    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

}
