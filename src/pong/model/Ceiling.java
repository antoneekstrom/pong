package pong.model;

public class Ceiling {
    private final double ceilingHeight = 0;

    boolean willHit(double posY) {
        return posY <= ceilingHeight;
    }

    public void bounce(Ball ball) {
        ball.setVelocity(ball.getVelocityX(), -ball.getVelocityY());
    }
}