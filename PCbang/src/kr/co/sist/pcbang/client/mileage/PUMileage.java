package kr.co.sist.pcbang.client.mileage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import kr.co.sist.pcbang.client.main.PUMainController;

@SuppressWarnings("serial")
public class PUMileage extends JFrame implements KeyListener, Runnable {

	private int x, y, f_width, f_height, cnt, mile, playerStatus;
	private boolean KeyUp, KeyDown, KeyLeft, KeyRight, thFlag;
	private Thread th;
	private Toolkit tk;
	private Image[] playerImg, playerImg2, playerImg3, playerImg4;
	private Image BackGround_img, buffImage;
	private Graphics buffg;
	private PUMileageDAO a_dao;
	private PUMainController pumc;
	private String id;

	public PUMileage(PUMainController pumc) {

		this.pumc = pumc;
		a_dao = PUMileageDAO.getInstance();
		tk = Toolkit.getDefaultToolkit();
		playerStatus = 0;
		thFlag = false;
		KeyUp = false;
		KeyDown = false;
		KeyLeft = false;
		KeyRight = false;

		id = pumc.getId();
		setMileage();
		base();
		start();
		setTitle("�ڡڡ� " + id + " �ڡڡ�");
		setSize(f_width, f_height);

		Dimension screen = tk.getScreenSize();

		int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2);

		exitClass ex = new exitClass();
		addWindowListener(ex);

		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
	}

	public void setMileage() {
		try {
			mile = a_dao.getMileage(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void base() {
		x = 320;
		y = 120;
		f_width = 671;
		f_height = 506;

		playerImg = new Image[4];
		for (int i = 0; i < playerImg.length; ++i) {
			playerImg[i] = new ImageIcon(getClass().getResource("char_f" + i + ".png")).getImage();
		}
		playerImg2 = new Image[4];
		for (int i = 0; i < playerImg2.length; ++i) {
			playerImg2[i] = new ImageIcon(getClass().getResource("char_l" + i + ".png")).getImage();
		}
		playerImg3 = new Image[4];
		for (int i = 0; i < playerImg3.length; ++i) {
			playerImg3[i] = new ImageIcon(getClass().getResource("char_r" + i + ".png")).getImage();
		}
		playerImg4 = new Image[4];
		for (int i = 0; i < playerImg4.length; ++i) {
			playerImg4[i] = new ImageIcon(getClass().getResource("char_u" + i + ".png")).getImage();
		}
		BackGround_img = new ImageIcon(getClass().getResource("background.png")).getImage();
	}

	public void start() {
		addKeyListener(this);
		th = new Thread(this);
		th.start();
	}

	public void run() {
		try {
			while (!thFlag) {
				KeyProcess();
				charMove();
				moveChar();
				repaint();
				Thread.sleep(20);
				cnt++;
			}
		} catch (Exception e) {
		}
	}

	public void charMove() {
		if (KeyLeft) {
			playerStatus = 1;
		}
		if (KeyRight) {
			playerStatus = 2;
		}
		if (KeyUp) {
			playerStatus = 3;
		}
	}

	public void paint(Graphics g) {
		buffImage = createImage(f_width, f_height);
		buffg = buffImage.getGraphics();

		update(g);
	}

	public void update(Graphics g) {
		drawBackGround();
		drawPlayer();
		drawText();

		g.drawImage(buffImage, 0, 0, this);
	}

	public void drawBackGround() {
		buffg.clearRect(0, 0, f_width, f_height);

		buffg.drawImage(BackGround_img, 0, 0, this);
	}

	public void drawPlayer() {
		switch (playerStatus) {

		case 0: // ����
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(playerImg[0], x, y, this);
			} else if ((cnt / 5 % 4 == 1)) {
				buffg.drawImage(playerImg[1], x, y, this);
			} else if ((cnt / 5 % 4 == 2)) {
				buffg.drawImage(playerImg[2], x, y, this);
			} else if ((cnt / 5 % 4 == 3)) {
				buffg.drawImage(playerImg[3], x, y, this);
			}
			break;

		case 1: // �������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(playerImg2[0], x, y, this);
			} else if ((cnt / 5 % 4 == 1)) {
				buffg.drawImage(playerImg2[1], x, y, this);
			} else if ((cnt / 5 % 4 == 2)) {
				buffg.drawImage(playerImg2[2], x, y, this);
			} else if ((cnt / 5 % 4 == 3)) {
				buffg.drawImage(playerImg2[3], x, y, this);
			}
			break;

		case 2: // ���������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(playerImg3[0], x, y, this);
			} else if ((cnt / 5 % 4 == 1)) {
				buffg.drawImage(playerImg3[1], x, y, this);
			} else if ((cnt / 5 % 4 == 2)) {
				buffg.drawImage(playerImg3[2], x, y, this);
			} else if ((cnt / 5 % 4 == 3)) {
				buffg.drawImage(playerImg3[3], x, y, this);
			}
			break;
		case 3: // �������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(playerImg4[0], x, y, this);
			} else if ((cnt / 5 % 4 == 1)) {
				buffg.drawImage(playerImg4[1], x, y, this);
			} else if ((cnt / 5 % 4 == 2)) {
				buffg.drawImage(playerImg4[2], x, y, this);
			} else if ((cnt / 5 % 4 == 3)) {
				buffg.drawImage(playerImg4[3], x, y, this);
			}
			break;
		}
	}

	public void resetKey() {
		KeyUp = false;
		KeyDown = false;
		KeyLeft = false;
		KeyRight = false;
		x = 320;
		y = 120;

		playerStatus = 0;
	}

	public void moveChar() {
		Random r = new Random();
		int addTime = r.nextInt(60);

		try {

			for (int i = 70; i < 150; i++) {
				if (x == 0 && y == i) {

					if (a_dao.getMileage(id) >= 500) {
						a_dao.updateMileage((addTime), 500, id);
						pumc.setRestTime(pumc.getRestTime() + (addTime));

						if (addTime <= 10 && addTime>0) {
							JOptionPane.showMessageDialog(this, (addTime) + "�� ��÷ �Ф�");
						} else if (addTime > 11 && addTime <= 30) {
							JOptionPane.showMessageDialog(this, (addTime) + "�� ��÷ !");
						} else if (addTime >= 31 && addTime <= 60) {
							JOptionPane.showMessageDialog(this, "�١ڡ١� " + (addTime) + "�� ��÷ �١ڡ١�");
						} else if(addTime==0) {
							JOptionPane.showMessageDialog(this, "���̟D����������������");
						}

						setMileage();
						resetKey();
					} else {
						JOptionPane.showMessageDialog(null, "���ϸ����� �����մϴ�");
						resetKey();
					}
				}
				if (x == 640 && y == i) {

					if (a_dao.getMileage(id) >= 1000) {

						a_dao.updateMileage(60, 1000, id);

						pumc.setRestTime(pumc.getRestTime() + 60);

						JOptionPane.showMessageDialog(this, "1�ð� �߰� !");
						setMileage();
						resetKey();
					} else {
						JOptionPane.showMessageDialog(null, "���ϸ����� �����մϴ�");
						resetKey();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 270; i < 360; i++) {

			if (x == i && y == 445) {
				x = 3000;
				y = 3000;
				thFlag = true;
				this.dispose();
			}
		}
	}

	public void drawText() {
		buffg.setFont(new Font("�޸հ��", Font.BOLD, 15));

		buffg.drawString("���� ���ϸ��� : " + mile + "��", 500, 50);

		buffg.drawString("500���ϸ��� �̱� ", 40, 200);

		buffg.drawString("1000���ϸ��� -> 1�ð�", 500, 200);

		buffg.drawString("�ݱ�", 250, 500);
	}

	public void KeyProcess() {
		if (KeyUp == true) {
			if (y > 20)
				y -= 5;

			playerStatus = 0;
		}

		if (KeyDown == true) {

			if (y + playerImg[0].getHeight(null) < f_height)
				y += 5;

			playerStatus = 0;
		}

		if (KeyLeft == true) {
			if (x > 0)
				x -= 5;

			playerStatus = 0;
		}

		if (KeyRight == true) {
			if (x + playerImg[0].getWidth(null) < f_width)
				x += 5;

			playerStatus = 0;
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			KeyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = true;
			break;
		case KeyEvent.VK_LEFT:
			KeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			KeyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			KeyDown = false;
			break;
		case KeyEvent.VK_LEFT:
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public class exitClass extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			thFlag = true;
			dispose();
		}
	}

}