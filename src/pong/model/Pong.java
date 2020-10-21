package pong.model;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/*
 * Logic for the Pong Game
 * Model class representing the "whole" game
 * Nothing visual here
 *
 */
public class Pong {

    public static final double GAME_WIDTH = 600;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.02;
    public static final long HALF_SEC = 500_000_000;


    // TODO More attributes
    private int pointsLeft;
    private int pointsRight;

    private final Ball ball;
    private final Paddle leftPaddle, rightPaddle;
    private final Ceiling ceiling;
    private final Floor floor;

    private double prevTime = 0;

    public Pong() {
        floor = new Floor(GAME_HEIGHT);
        ceiling = new Ceiling();

        ball = new Ball(0, 0);
        ball.reset();

        final double paddleY = (GAME_HEIGHT / 2) - (Paddle.PADDLE_HEIGHT / 2);
        leftPaddle = new Paddle(30, paddleY);
        rightPaddle = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH - 30, paddleY);

        prevTime = System.nanoTime();
    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        double deltaTime = (now - prevTime) * Math.pow(10, -9);

        updatePaddles(deltaTime);
        updateBall(now ,deltaTime);

        prevTime = now;
    }

    private void updatePaddles(double deltaTime) {
        double nextPosLeft = leftPaddle.getY() + (leftPaddle.getSpeed() * deltaTime);
        if (isWithinHeightBounds(nextPosLeft, nextPosLeft + Paddle.PADDLE_HEIGHT)) {
            leftPaddle.setY(nextPosLeft);
        }

        double nextPosRight = rightPaddle.getY() + (rightPaddle.getSpeed() * deltaTime);
        if (isWithinHeightBounds(nextPosRight, nextPosRight + Paddle.PADDLE_HEIGHT)) {
            rightPaddle.setY(nextPosRight);
        }
    }

    private void updateBall(long now, double deltaTime) {
        updateScore();

        Point2D nextPos = ball.computeNextPos(deltaTime);
        ball.setPos(nextPos.getX(), nextPos.getY());

        double timeSinceLastHit = (now - timeForLastHit) * Math.pow(10, -9);
        if (timeSinceLastHit >= 0.05) {
            if (collide(ball)) {
                timeForLastHit = now;
            }
        }

//        if (timeSinceLastHit >= 1 && doesCollide(nextPos.getX(), nextPos.getY())) {
//            double angle = calcDirection(ball.getX(), ball.getY(), nextPos.getX(), nextPos.getY());
//            ball.setVelocity(ball.getVelocityX() * Math.cos(angle), ball.getVelocityY() * Math.sin(angle));
//            timeForLastHit = now;
//        }
//        else {
//            ball.setPos(nextPos.getX(), nextPos.getY());
//        }
    }

    void updateScore() {
        if(ball.getX() >= GAME_WIDTH) {
            incPointsLeft();
            ball.reset();
        } else if (ball.getX() <= 0) {
            incPointsRight();
            ball.reset();
        }
    }

    private boolean collide(Ball ball) {
        boolean hitFloor = floor.willHit(ball.getY() + Ball.HEIGHT);
        boolean hitCeiling = ceiling.willHit(ball.getY());

        boolean hitLeftPaddle = leftPaddle.getRect().intersects(ball.getRect());
        boolean ballNotBehindLeftPaddle = ball.getX() > leftPaddle.getX();

        boolean hitRightPaddle = rightPaddle.getRect().intersects(ball.getRect());
        boolean ballNotBehindRightPaddle = ball.getX() + Ball.WIDTH < rightPaddle.getX() + Paddle.PADDLE_WIDTH;

        if (hitLeftPaddle && ballNotBehindLeftPaddle) {
            leftPaddle.bounce(ball);
        }
        else if (hitRightPaddle && ballNotBehindRightPaddle) {
            rightPaddle.bounce(ball);
        }

        if (hitCeiling) {
            ceiling.bounce(ball);
        }
        else if (hitFloor) {
            ceiling.bounce(ball);
        }

        return hitFloor || hitCeiling || hitLeftPaddle || hitRightPaddle;
    }

    private double calcDirection(double currentPosX, double currentPosY, double nextPosX, double nextPosY) {
        double hypotenuse = Math.sqrt(Math.pow(nextPosX - currentPosX, 2) + Math.pow(nextPosY - currentPosY, 2));
        double opposite = Math.sqrt(Math.pow(nextPosY - currentPosY, 2));
        double angle = Math.sin(hypotenuse/
                opposite);
        return angle;
    }

    private boolean isWithinHeightBounds(double yMin, double yMax) {
        return isWithinRange(yMin, 0, Pong.GAME_HEIGHT) && isWithinRange(yMax, 0, Pong.GAME_HEIGHT);
    }

    private boolean isWithinRange(double val, double min, double max) {
        return (val >= min && val <= max);
    }

    // --- Used by GUI  ------------------------

    public List<IPositionable> getPositionables() {
        List<IPositionable> drawables = new ArrayList<>();
        drawables.add(ball);
        drawables.add(leftPaddle);
        drawables.add(rightPaddle);
        return drawables;
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public int getPointsRight() {
        return pointsRight;
    }


    public void setSpeedRightPaddle(double dy) {
        rightPaddle.setSpeed(dy);
    }

    public void setSpeedLeftPaddle(double dy) {
        leftPaddle.setSpeed(dy);
    }

    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void incPointsLeft() {
        this.pointsLeft++;
    }

    public void incPointsRight() {
        this.pointsRight++ ;
    }
}
