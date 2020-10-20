package pong.model;

public class Floor {
    private final double floorHeight;

    Floor(double height) {
        this.floorHeight = height;
    }

    boolean willHit(double posY) {
        return posY >= floorHeight;
    }

    public void bounce(Ball ball) {
        ball.setVelocity(ball.getVelocityX(), -ball.getVelocityY());
    }
}
