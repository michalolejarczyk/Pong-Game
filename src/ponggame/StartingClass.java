package ponggame;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class StartingClass extends Applet implements Runnable, KeyListener {

	enum GameState {
		Menu, Running, EndGame, settingCpuStartingPosition, cpuWon, playerWon
	}

	GameState state = GameState.Menu;

	private final int maxPointsInGame = 3;

	private Date timer;

	public PlayerPlate playerPlate;
	private CpuPlate cpuPlate;
	public static ball ball;

	private Font font = new Font(null, Font.BOLD, 30);
	private Graphics second;
	private URL base;
	private Image playerPlateImage, cpuPlateImage, ballImage, image;
	public static Image tileTop, tileBottom;

	private ArrayList<Tile> tileArray = new ArrayList<Tile>();

	private BasicStroke basicStrokeDashedLine;

	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Pong");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		float[] d1 = { 22, 41 };

		basicStrokeDashedLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, d1, 0);

		tileTop = getImage(base, "../src/data/tiletop.png");
		tileBottom = getImage(base, "../src/data/tilebottom.png");
		playerPlateImage = getImage(base, "../src/data/playerplate.png");
		ballImage = getImage(base, "../src/data/ball2.png");
		cpuPlateImage = getImage(base, "../src/data/cpuplate.png");

	}

	@Override
	public void start() {
		playerPlate = new PlayerPlate();
		ball = new ball();
		cpuPlate = new CpuPlate();

		try {
			loadMap("../src/data/map.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {
				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tileArray.add(t);
				}

			}
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			if (state == GameState.Menu) {
				repaint();
			} else if (state == GameState.settingCpuStartingPosition) {
				playerPlate.update();
				updateTiles();

				cpuPlate.moveToStartingPosition();
				repaint();

				holdTime(2);

			} else if (state == GameState.Running) {
				playerPlate.update();
				updateTiles();
				ball.update();
				cpuPlate.update();
				repaint();

				checkPointStatus();

			}

			try {
				Thread.sleep(17);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void holdTime(int secondToHold) {
		Date currentTime = new Date();

		int numberOfSecondsLeft = (int) ((currentTime.getTime() - timer.getTime()) / 1000);
		if (numberOfSecondsLeft > secondToHold) {
			state = GameState.Running;

		}
	}

	private void checkPointStatus() {
		if (ball.getCenterX() < -40) {
			cpuPlate.addPoint();
			ball.setCenterX(384);
			ball.setCenterYRandomValue();
			ball.setBallSpeedInXdirectionWithRandomXYSpeedAndYDirection(false, ball.ballMinSpeed);
			state = GameState.settingCpuStartingPosition;
			timer = new Date();
		} else if (ball.getCenterX() > 840) {
			playerPlate.addPoint();
			ball.setCenterX(384);
			ball.setCenterYRandomValue();
			ball.setBallSpeedInXdirectionWithRandomXYSpeedAndYDirection(true, ball.ballMinSpeed);
			state = GameState.settingCpuStartingPosition;
			timer = new Date();
		}

		if (cpuPlate.getPoints() > maxPointsInGame) {
			state = GameState.cpuWon;
		} else if (playerPlate.getPoints() > maxPointsInGame) {
			state = GameState.playerWon;
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			System.out.println("Enter pressed.");
			if (state == GameState.Menu) {
				state = GameState.settingCpuStartingPosition;
				timer = new Date();
				nullifyAllSettings();
			} else if (state == GameState.cpuWon || state == GameState.playerWon) {
				state = GameState.Menu;
			}
			break;
		case KeyEvent.VK_UP:
			playerPlate.setMovingUp(true);
			break;
		case KeyEvent.VK_DOWN:
			playerPlate.setMovingDown(true);
			break;
		case KeyEvent.VK_LEFT:
			ball.setSpeedX(0);
			ball.setSpeedY(0);
			break;
		}

	}

	private void nullifyAllSettings() {
		playerPlate.setPoints(0);
		playerPlate.setBeginningPosition();
		cpuPlate.setPoints(0);
		cpuPlate.setBeginningPosition();
		ball.setCenterYRandomValue();
		ball.setBallSpeedInXdirectionWithRandomXYSpeedAndYDirection(true, ball.ballMinSpeed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			System.err.println("Enter released.");
			break;
		case KeyEvent.VK_UP:
			playerPlate.setMovingUp(false);
			break;
		case KeyEvent.VK_DOWN:
			playerPlate.setMovingDown(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running || state == GameState.settingCpuStartingPosition) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			paintTiles(g);

			paintCenterLine(g);

			g.drawImage(playerPlateImage, playerPlate.getCenterX() - 8, playerPlate.getCenterY() - 40, this);
			g.drawImage(cpuPlateImage, cpuPlate.getCenterX() - 8, cpuPlate.getCenterY() - 40, this);
			g.drawImage(ballImage, ball.getCenterX(), ball.getCenterY(), this);
			g.setColor(Color.cyan);
			// g.drawRect(PlayerPlate.rect1.x, PlayerPlate.rect1.y, PlayerPlate.rect1.width,
			// PlayerPlate.rect1.height);
			// g.drawRect(PlayerPlate.rect2.x, PlayerPlate.rect2.y, PlayerPlate.rect2.width,
			// PlayerPlate.rect2.height);
			// g.drawRect(PlayerPlate.rect3.x, PlayerPlate.rect3.y, PlayerPlate.rect3.width,
			// PlayerPlate.rect3.height);
			// g.drawRect(PlayerPlate.rect4.x, PlayerPlate.rect4.y, PlayerPlate.rect4.width,
			// PlayerPlate.rect4.height);
			// g.drawRect(PlayerPlate.rect5.x, PlayerPlate.rect5.y, PlayerPlate.rect5.width,
			// PlayerPlate.rect5.height);
			// g.drawRect(CpuPlate.rect1.x, CpuPlate.rect1.y, CpuPlate.rect1.width,
			// CpuPlate.rect1.height);
			// g.drawRect(CpuPlate.rect2.x, CpuPlate.rect2.y, CpuPlate.rect2.width,
			// CpuPlate.rect2.height);
			// g.drawRect(CpuPlate.rect3.x, CpuPlate.rect3.y, CpuPlate.rect3.width,
			// CpuPlate.rect3.height);
			// g.drawRect(CpuPlate.rect4.x, CpuPlate.rect4.y, CpuPlate.rect4.width,
			// CpuPlate.rect4.height);
			// g.drawRect(CpuPlate.rect5.x, CpuPlate.rect5.y, CpuPlate.rect5.width,
			// CpuPlate.rect5.height);
			// g.drawRect(ball.colisionRectangle.x, ball.colisionRectangle.y,
			// ball.colisionRectangle.width,
			// ball.colisionRectangle.height);

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(playerPlate.getPoints()), 40, 40);
			g.drawString(Integer.toString(cpuPlate.getPoints()), 440, 40);

		} else if (state == GameState.Menu) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Press Enter To Start", 180, 240);
		} else if (state == GameState.cpuWon) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("CPU is the winner", 180, 240);
			g.drawString("Press Enter to continue", 180, 300);
		} else if (state == GameState.playerWon) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Player is the winner", 180, 240);
			g.drawString("Press Enter to continue.", 180, 300);
		}
	}

	private void paintCenterLine(Graphics g) {
		Graphics2D Gr2D = (Graphics2D) g;

		Gr2D.setStroke(basicStrokeDashedLine);
		Gr2D.setPaint(Color.yellow);
		Gr2D.drawLine(399, 9, 399, 471);
	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tileArray.size(); i++) {
			Tile t = (Tile) tileArray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
			// g.setColor(Color.cyan);
			// g.drawRect(t.rectangle.x, t.rectangle.y, t.rectangle.width,
			// t.rectangle.height);
		}
	}

	private void updateTiles() {
		for (int i = 0; i < tileArray.size(); i++) {
			Tile t = (Tile) tileArray.get(i);
			if (t.update()) {
				break;
			}
		}
	}

}
