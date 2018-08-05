package ponggame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
	private int tileX, tileY, type;
	public Image tileImage;
	public Rectangle rectangle;

	public Tile(int x, int y, int typeInt) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeInt;

		if (type == 8) {
			rectangle = new Rectangle(tileX, tileY, 40, 10);
			tileImage = StartingClass.tileTop;
		} else if (type == 2) {
			rectangle = new Rectangle(tileX, tileY + 30, 40, 10);
			tileImage = StartingClass.tileBottom;
		} else {
			type = 0;
		}
	}

	public boolean update() {
		if (type != 0 && type != 4 && type != 5 && rectangle.intersects(ball.colisionRectangle)) {
			if (ball.circle.intersects(rectangle)) {
				ball.speedY = -ball.speedY;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}
}
