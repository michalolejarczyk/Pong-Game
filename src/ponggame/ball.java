package ponggame;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class ball {
	public static final int ballMaxSpeed = 9;
	public static final int ballMinSpeed = 6;
	public static int centerX = 384;
	public static int centerY = 240;

	public static int speedX = -4;
	public static int speedY = 0;

	private Random randomGenerator = new Random();

	public static Shape circle = new Ellipse2D.Double(centerX, centerY, 30, 30);
	public static Rectangle colisionRectangle = new Rectangle(0, 0, 0, 0);

	private boolean directionPlusX = true;
	private boolean directionPlusY = true;

	public void update() {

		if (directionPlusX) {
			centerX += speedX;
		} else {
			centerX -= speedX;
		}

		if (directionPlusY) {
			centerY += speedY;
		} else {
			centerY -= speedY;
		}
		circle = new Ellipse2D.Double(centerX, centerY, 30, 30);
		colisionRectangle.setBounds(centerX - 10, centerY - 10, 50, 50);
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public void setBallSpeedInXdirectionWithRandomXYSpeedAndYDirection(boolean plusXDirection, int requiredBallSpeed) {
		int speedX = randomGenerator.nextInt(requiredBallSpeed) + 1;
		int speedY = requiredBallSpeed - speedX;

		if (!plusXDirection) {
			this.speedX = -speedX;
			if (randomGenerator.nextBoolean()) {
				this.speedY = speedY;
			} else {
				this.speedY = -speedY;
			}
		} else {
			this.speedX = speedX;
			if (randomGenerator.nextBoolean()) {
				this.speedY = speedY;
			} else {
				this.speedY = -speedY;
			}
		}
		System.out.println("speedX: " + speedX);
		System.out.println("speedY: " + speedY);
	}

	public void setCenterYRandomValue() {
		centerY = randomGenerator.nextInt(400) + 40;		
	}

}
