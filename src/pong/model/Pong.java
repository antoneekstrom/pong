package pong.model;


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

    private Ball ball;
    private Paddle leftPaddle, rightPaddle;

    private double leftPaddleSpeed = 0, rightPaddleSpeed = 0;

    public Pong() {
        ball = new Ball(GAME_WIDTH / 2, GAME_HEIGHT / 2);

        final double paddleY = (GAME_HEIGHT / 2) - (Paddle.PADDLE_HEIGHT / 2);

        leftPaddle = new Paddle(30, paddleY);
        rightPaddle = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH - 30, paddleY);
    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        leftPaddle.translateY(leftPaddleSpeed);
        rightPaddle.translateY(rightPaddleSpeed);
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
