package pong.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static pong.model.Pong.*;

/*
 * A Ball for the Pong game
 * A model class
 */
public class Ball implements IPositionable {

    public static final double WIDTH = 40;
    public static final double HEIGHT = 40;
    public static final double BASE_SPEED = 250;

    private double hits = 0;
    private double vx = 0, vy = 0;
    private double x, y;

    private Rectangle2D rect;

    Ball(double x, double y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
    }

    public Point2D computeNextPos(double deltaTime) {
        double nextPosX = getX() + vx * deltaTime;
        double nextPosY = getY() + vy * deltaTime;

        return new Point2D.Double(nextPosX, nextPosY);
    }

    public void reset() {
        setPos(GAME_WIDTH / 2, GAME_HEIGHT / 2);
        double angle = Math.random() * 360;
        setVelocity(BASE_SPEED, BASE_SPEED * Math.sin(angle));
    }

    public void bounce() {

    }

    private double getBallSpeedModifier() {
        return (hits + 1) * BALL_SPEED_FACTOR;
    }

    public Rectangle2D getRect(double x, double y) {
        rect.setRect(x, y, WIDTH, HEIGHT);
        return rect;
    }

    public Rectangle2D getRect() {
        return getRect(getX(), getY());
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public double getVelocityX() {
        return vx;
    }

    public double getVelocityY() {
        return vy;
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
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }
}
