package ponggame;

import java.awt.Rectangle;

public class PlayerPlate {
	private int centerX = 20;
	private int centerY = 240;

	public static Rectangle rect1, rect2, rect3, rect4, rect5;

	private boolean movingUp;
	private boolean movingDown;

	private int speedY = 3;

	private int points;

	public PlayerPlate() {
		movingUp = false;
		movingDown = false;

		rect1 = new Rectangle(centerX - 8, centerY - 40, 16, 20);
		rect2 = new Rectangle(centerX - 8, centerY - 20, 16, 15);
		rect3 = new Rectangle(centerX - 8, centerY - 5, 16, 10);
		rect4 = new Rectangle(centerX - 8, centerY + 5, 16, 15);
		rect5 = new Rectangle(centerX - 8, centerY + 20, 16, 20);

		points = 0;
	}
	
	public void addPoint() {
		points++;
	}

	public void update() {
		if (movingUp) {
			if (centerY <= 50) {
				centerY = 50;
			} else {
				centerY -= speedY;
			}
		}
		if (movingDown) {
			if (centerY >= 430) {
				centerY = 430;
			} else {
				centerY += speedY;
			}
		}

		rect1.setBounds(centerX - 8, centerY - 40, 16, 20);
		rect2.setBounds(centerX - 8, centerY - 20, 16, 15);
		rect3.setBounds(centerX - 8, centerY - 5, 16, 10);
		rect4.setBounds(centerX - 8, centerY + 5, 16, 15);
		rect5.setBounds(centerX - 8, centerY + 20, 16, 20);

		if (rect1.intersects(ball.colisionRectangle) || rect2.intersects(ball.colisionRectangle)
				|| rect3.intersects(ball.colisionRectangle) || rect4.intersects(ball.colisionRectangle)
				|| rect5.intersects(ball.colisionRectangle)) {
			int speed = (ball.ballMaxSpeed - (ball.ballMaxSpeed % 3)) / 3;
			System.out.println(speed);
			if (ball.circle.intersects(rect1)) {
				ball.speedX = speed;
				ball.speedY = -2 * speed;
				System.out.println("X :" + ball.speedX);
				System.out.println("Y :" + ball.speedY);
			} else if (ball.circle.intersects(rect2)) {
				ball.speedX = 2 * speed;
				ball.speedY = -1 * speed;
				System.out.println("X :" + ball.speedX);
				System.out.println("Y :" + ball.speedY);
			} else if (ball.circle.intersects(rect3)) {
				ball.speedX = 3 * speed;
				ball.speedY = 0;
				System.out.println("X :" + ball.speedX);
				System.out.println("Y :" + ball.speedY);
			} else if (ball.circle.intersects(rect4)) {
				ball.speedX = 2 * speed;
				ball.speedY = 1 * speed;
				System.out.println("X :" + ball.speedX);
				System.out.println("Y :" + ball.speedY);
			} else if (ball.circle.intersects(rect5)) {
				ball.speedX = 1 * speed;
				ball.speedY = 2 * speed;
				System.out.println("X :" + ball.speedX);
				System.out.println("Y :" + ball.speedY);
			}
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setBeginningPosition() {
		centerX = 20;
		centerY = 240;
	}
}
