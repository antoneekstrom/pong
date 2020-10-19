package pong.model;

import java.awt.*;

import static pong.model.Pong.GAME_HEIGHT;

/*
 * A Paddle for the Pong game
 * A model class
 *
 */
public class Paddle implements IPositionable {

    public static final double PADDLE_WIDTH = 10;
    public static final double PADDLE_HEIGHT = 60;
    public static final double PADDLE_SPEED = 5;

    private double x = 0, y = 0;
    private final Rectangle rect;

    public Paddle(double x, double y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle((int)x, (int)y, (int)PADDLE_WIDTH, (int)PADDLE_WIDTH);
    }

    public void translateY(double dy) {
        this.y += dy;
    }

    public Rectangle getRect() {
        rect.setLocation((int)getX(), (int)getY());
        return rect;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public double getHeight() {
        return PADDLE_HEIGHT;
    }
}
