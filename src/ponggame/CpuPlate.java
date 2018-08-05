package ponggame;

import java.awt.Rectangle;

public class CpuPlate {
	private int centerX = 780;
	private int centerY = 240;

	private boolean movingUp = false;
	private boolean movingDown = false;

	public static Rectangle rect1, rect2, rect3, rect4, rect5;

	private int speedY = 3;

	private int points;

	public CpuPlate() {
		rect1 = new Rectangle(centerX - 8, centerY - 40, 16, 20);
		rect2 = new Rectangle(centerX - 8, centerY - 20, 16, 15);
		rect3 = new Rectangle(centerX - 8, centerY - 5, 16, 10);
		rect4 = new Rectangle(centerX - 8, centerY + 5, 16, 15);
		rect5 = new Rectangle(centerX - 8, centerY + 20, 16, 20);

		points = 0;
	}

	public void update() {
		movingUp = false;
		movingDown = false;

		if (centerY != StartingClass.ball.getCenterY()) {
			if (centerY - StartingClass.ball.getCenterY() > 0) {
				movingUp = true;
			} else {
				movingDown = true;
			}
		}
		if (movingUp) {
			centerY -= speedY;
		}

		if (movingDown) {
			centerY += speedY;
		}

		if (centerY >= 430) {
			centerY = 430;
			movingDown = false;
		}

		if (centerY <= 50) {
			centerY = 50;
			movingUp = false;
		}

		rect1.setBounds(centerX - 8, centerY - 40, 16, 20);
		rect2.setBounds(centerX - 8, centerY - 20, 16, 15);
		rect3.setBounds(centerX - 8, centerY - 5, 16, 10);
		rect4.setBounds(centerX - 8, centerY + 5, 16, 15);
		rect5.setBounds(centerX - 8, centerY + 20, 16, 20);

		if (ball.speedX > 0) {
			if (rect1.intersects(ball.colisionRectangle) || rect2.intersects(ball.colisionRectangle)
					|| rect3.intersects(ball.colisionRectangle) || rect4.intersects(ball.colisionRectangle)
					|| rect5.intersects(ball.colisionRectangle)) {
				int speed = (ball.ballMaxSpeed - (ball.ballMaxSpeed % 3)) / 3;
				System.out.println(speed);
				if (ball.circle.intersects(rect1)) {
					ball.speedX = -speed;
					ball.speedY = -2 * speed;
					System.out.println("X :" + ball.speedX);
					System.out.println("Y :" + ball.speedY);
				} else if (ball.circle.intersects(rect2)) {
					ball.speedX = -2 * speed;
					ball.speedY = -1 * speed;
					System.out.println("X :" + ball.speedX);
					System.out.println("Y :" + ball.speedY);
				} else if (ball.circle.intersects(rect3)) {
					ball.speedX = -3 * speed;
					ball.speedY = 0;
					System.out.println("X :" + ball.speedX);
					System.out.println("Y :" + ball.speedY);
				} else if (ball.circle.intersects(rect4)) {
					ball.speedX = -2 * speed;
					ball.speedY = 1 * speed;
					System.out.println("X :" + ball.speedX);
					System.out.println("Y :" + ball.speedY);
				} else if (ball.circle.intersects(rect5)) {
					ball.speedX = -1 * speed;
					ball.speedY = 2 * speed;
					System.out.println("X :" + ball.speedX);
					System.out.println("Y :" + ball.speedY);
				}
			}
		}
	}

	public void addPoint() {
		points++;
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

	public void moveToStartingPosition() {
		if (centerY != 240) {
			if (centerY > 240) {
				centerY--;
			} else if (centerY < 240) {
				centerY++;
			}
		}

	}

	public void setBeginningPosition() {
		centerX = 780;
		centerY = 240;
	}
}
