package pong.model;

import java.awt.geom.*;
import static pong.model.Pong.GAME_HEIGHT;

/*
 * A Paddle for the Pong game
 * A model class
 *
 */
public class Paddle implements IPositionable {

    public static final double PADDLE_WIDTH = 10;
    public static final double PADDLE_HEIGHT = 60;
    public static final double PADDLE_SPEED = 250;

    private double speed = 0;
    private double x = 0, y = 0;
    private final Rectangle2D rect;

    public Paddle(double x, double y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle2D.Double(x, y, PADDLE_WIDTH, PADDLE_WIDTH);
    }

    public void setY(double dy) {
        this.y = dy;
    }

    public void bounce(Ball ball) {
        double angleY = (ball.getRect().getCenterY() - getRect().getCenterY()) / PADDLE_HEIGHT;
        angleY *= 1.5; // för att öka vinkeln som bollen hamnar på, annars blir den lite mesig

        double dirX = -Math.signum(ball.getVelocityX());
        double speed = Ball.BASE_SPEED * ball.getBallSpeedModifier();

        ball.setVelocity(dirX * speed, speed * angleY);
        ball.incrementHits();
    }

    public Rectangle2D getRect() {
        rect.setRect(getX(), getY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        return rect;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
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
