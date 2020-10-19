package pong.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

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

    private double leftPaddleSpeed = 0, rightPaddleSpeed = 0;

    private double prevTime = 0;

    public Pong() {
        ball = new Ball(GAME_WIDTH / 2, GAME_HEIGHT / 2);

        final double paddleY = (GAME_HEIGHT / 2) - (Paddle.PADDLE_HEIGHT / 2);
        leftPaddle = new Paddle(30, paddleY);
        rightPaddle = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH - 30, paddleY);
    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        double deltaTime = (now - prevTime) * Math.pow(10, -9);

        updatePaddles(deltaTime);
        updateBall(deltaTime);

        prevTime = now;
    }

    private void updatePaddles(double deltaTime) {
        double nextPosLeft = leftPaddle.getY() + (leftPaddleSpeed * deltaTime);
        if (isWithinHeightBounds(nextPosLeft, nextPosLeft + Paddle.PADDLE_HEIGHT)) {
            leftPaddle.translateY(leftPaddleSpeed);
        }

        double nextPosRight = rightPaddle.getY() + (rightPaddleSpeed * deltaTime);
        if (isWithinHeightBounds(nextPosRight, nextPosRight + Paddle.PADDLE_HEIGHT)) {
            rightPaddle.translateY(rightPaddleSpeed);
        }
    }

    private void updateBall(double deltaTime) {
        double currentPosX = ball.getX();
        double currentPosY = ball.getY();
        double nextPosX = currentPosX + Ball.BASE_SPEED * BALL_SPEED_FACTOR * deltaTime;
        double nextPosY = currentPosY + Ball.BASE_SPEED * BALL_SPEED_FACTOR * deltaTime;

        boolean willCollide = doesCollide(leftPaddle, rightPaddle, ball);

        if (willCollide) {
            double angle = calcDirection(currentPosX, currentPosY, nextPosX, nextPosY);

        }

        //ball.bounce(direction)
    }

    private boolean doesCollide(Paddle left, Paddle right, Ball ball) {
        boolean collideBounds = !isWithinHeightBounds(ball.getX(), ball.getX() + Ball.HEIGHT);
        boolean collideLeft = left.getRect().contains(ball.getX(), ball.getY());
        boolean collideRight = right.getRect().contains(ball.getX(), ball.getY());
        return collideBounds && collideLeft && collideRight;
    }

    private boolean isWithinHeightBounds(double yMin, double yMax) {
        return isWithinRange(yMin, 0, GAME_HEIGHT) && isWithinRange(yMax, 0, GAME_HEIGHT);
    }

    private boolean isWithinRange(double val, double min, double max) {
        return (val >= min && val <= max);
    }

    private double calcDirection(double currentPosX, double currentPosY, double nextPosX, double nextPosY) {
        double hypotenuse = Math.sqrt(Math.pow(nextPosX - currentPosX, 2) + Math.pow(nextPosY - currentPosY, 2));
        double opposite = Math.sqrt(Math.pow(nextPosY - currentPosY, 2));
        double angle = Math.sin(hypotenuse/opposite);
        return angle;
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
        rightPaddleSpeed = dy;
    }

    public void setSpeedLeftPaddle(double dy) {
        leftPaddleSpeed = dy;
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
}
