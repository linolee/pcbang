package kr.co.sist.pcbang.client.mileage;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class PUMileageStore extends JFrame implements KeyListener, Runnable {

	private int f_width;
	private int f_height;

	private int x, y;

	private boolean KeyUp = false;
	private boolean KeyDown = false;
	private boolean KeyLeft = false;
	private boolean KeyRight = false;
//	private boolean KeySpace = false;

	private int cnt;

//	private int player_Speed; // ������ ĳ���Ͱ� �����̴� �ӵ��� ����
	private int player_Status = 0;

	private Thread th;

	private Toolkit tk = Toolkit.getDefaultToolkit();

	private Image[] Player_img, Player_img2, Player_img3, Player_img4;
	private Image BackGround_img; // ���ȭ�� �̹���

	private Image buffImage;
	private Graphics buffg;
	
	private PUMileageDAO a_dao;
	
	private int mile;
	
	
	
	
	public PUMileageStore() {
		super("Mileage Store");
		
		
		a_dao=PUMileageDAO.getInstance();
		userName();
		
		
		init();
		start();

//		setTitle("���ϸ���");
		// ȭ�� ũ��
		setSize(f_width, f_height);

		Dimension screen = tk.getScreenSize();

		int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2);

		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
	}
	
	public void userName() {
		try {
			mile = a_dao.getMileage("houuking");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void init() {
		x = 395;  // ĳ������ ��ġ
		y = 370;
		f_width = 840;
		f_height = 840;

		String path = this.getClass().getResource("/").getPath()+"kr/co/sist/pcbang/client/mileage/";
//		URL path = (getClass().getClassLoader().getResource("e/"));
//		String path=ClassLoader.getSystemClassLoader().getResource(".").getPath();
//		URL path=getClass().getResource("background.png");
//		URL path=getClass().getResource("game");
		System.out.println(path);
		Player_img = new Image[4];
		for (int i = 0; i < Player_img.length; ++i) {
			Player_img[i] = new ImageIcon(path+"img/char_f"+i+".png").getImage();
		}
		Player_img2 = new Image[4];
		for (int i = 0; i < Player_img2.length; ++i) {
			Player_img2[i] = new ImageIcon(path+"img/char_l"+i+".png").getImage();
		}
		Player_img3 = new Image[4];
		for (int i = 0; i < Player_img3.length; ++i) {
			Player_img3[i] = new ImageIcon(path+"img/char_r"+i+".png").getImage();
		}
		Player_img4 = new Image[4];
		for (int i = 0; i < Player_img4.length; ++i) {
			Player_img4[i] = new ImageIcon(path+"img/char_u"+i+".png").getImage();
		}
		

//		BackGround_img = new ImageIcon(path+"images/background.png").getImage();
		BackGround_img = new ImageIcon(path+"img/background.png").getImage();

		
		//////// BGM
//		  try
//	        {
//	            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path+"bgm/baram.wav"));
//	            Clip clip = AudioSystem.getClip();
//	            clip.stop();
//	            clip.open(ais);
//	            clip.start();
//	        }
//	        catch (Exception ex)
//	        {
//	        }
		

	}

	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);

		th = new Thread(this);
		th.start();

	}

	public void run() {
		try {
			while (true) {
				KeyProcess();
				charMove();

				getItem();
				repaint();

				Thread.sleep(20);
				cnt++;
			}
		} catch (Exception e) {
		}
	}

	public void charMove() {
		if (KeyLeft) {
			player_Status = 1;
		}
		if (KeyRight) {
			player_Status = 2;
		}
		if (KeyUp) {
			player_Status = 3;
		}
	}


	public void paint(Graphics g) {
		buffImage = createImage(f_width, f_height);
		buffg = buffImage.getGraphics();

		update(g);
	}

	public void update(Graphics g) {

		Draw_Background(); // ��� �̹��� �׸��� �޼ҵ� ����
		Draw_Player(); // �÷��̾ �׸��� �޼ҵ� �̸� ����

		Draw_StatusText();// ���� ǥ�� �ؽ�Ʈ�� �׸��� �޼ҵ� ����

		g.drawImage(buffImage, 0, 0, this);
	}

	public void Draw_Background() {

		buffg.clearRect(0, 0, f_width, f_height);

			buffg.drawImage(BackGround_img, 0, 0, this);
	}

	public void Draw_Player() {

		switch (player_Status) {

		case 0: // ����
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(Player_img[0], x, y, this);
				
			} 
			else if((cnt / 5 % 4 == 1)) {
				buffg.drawImage(Player_img[1], x, y, this);
			}
			else if((cnt / 5 % 4 == 2)) {
				buffg.drawImage(Player_img[2], x, y, this);
			}
			else if((cnt / 5 % 4 == 3)) {
				buffg.drawImage(Player_img[3], x, y, this);
			}

			break;

		case 1: // �������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(Player_img2[0], x, y, this);
				
			} 
			else if((cnt / 5 % 4 == 1)) {
				buffg.drawImage(Player_img2[1], x, y, this);
			}
			else if((cnt / 5 % 4 == 2)) {
				buffg.drawImage(Player_img2[2], x, y, this);
			}
			else if((cnt / 5 % 4 == 3)) {
				buffg.drawImage(Player_img2[3], x, y, this);
			}
			break;
			
		case 2: // ���������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(Player_img3[0], x, y, this);
				
			} 
			else if((cnt / 5 % 4 == 1)) {
				buffg.drawImage(Player_img3[1], x, y, this);
			}
			else if((cnt / 5 % 4 == 2)) {
				buffg.drawImage(Player_img3[2], x, y, this);
			}
			else if((cnt / 5 % 4 == 3)) {
				buffg.drawImage(Player_img3[3], x, y, this);
			}
			break;
		case 3: // �������� �̵�
			if ((cnt / 5 % 4) == 0) {
				buffg.drawImage(Player_img4[0], x, y, this);
				
			} 
			else if((cnt / 5 % 4 == 1)) {
				buffg.drawImage(Player_img4[1], x, y, this);
			}
			else if((cnt / 5 % 4 == 2)) {
				buffg.drawImage(Player_img4[2], x, y, this);
			}
			else if((cnt / 5 % 4 == 3)) {
				buffg.drawImage(Player_img4[3], x, y, this);
			}

			break;

		}

	}
	
	public void falseKey() {
		KeyUp=false;
		KeyDown=false;
		KeyLeft=false;
		KeyRight=false;
		x=395;
		y=370;
		player_Status=0;
	}
	
	// Ư�� ������ �̵��� ���� ��ȯ
	public void getItem() {
			
			Random r=new Random();
			int addTime=r.nextInt(100);
			
			try {
				
			for(int i=290;i<390;i++) {
				if(x==0 && y==i) {
					
					if(a_dao.getMileage("houuking")>=500) {
						
						a_dao.updateMileage(addTime, 500, "houuking");
					JOptionPane.showMessageDialog(this, addTime+"�� ��÷ !!! ��������");
					userName();
					falseKey();
					} else{
						JOptionPane.showMessageDialog(null, "���ϸ��� ����");
						falseKey();
						
					}
					
					
				}
				if(x==790 && y==i) {
					
					if(a_dao.getMileage("houuking")>=1000) {
					
					a_dao.updateMileage(60, 1000, "houuking");
					JOptionPane.showMessageDialog(this, "1�ð� �߰� !");
					userName();
					falseKey();
				} else {
					JOptionPane.showMessageDialog(null, "���ϸ��� ����");
					falseKey();
				}
			}
			}} catch (SQLException e) {
				e.printStackTrace();
			}
			 
			for(int i=350;i<440;i++) {
				
				if(x==i && y==20) {
					JOptionPane.showMessageDialog(this, "PKDmsfmofvmld,ADLPKDmsfmvmld,ADLPKDmsfmofvmld,ADLPKDmsfmofvmld,ADLPKDmsfmofvmld,ADL");
					falseKey();
				}
				if(x==i && y==750) {
					JOptionPane.showMessageDialog(this, "����");
					System.exit(0);
				}
			}
			
		}
	

	public void Draw_StatusText() { // ���� üũ�� �ؽ�Ʈ�� �׸��ϴ�.

		buffg.setFont(new Font("Defualt", Font.BOLD, 15));
//��Ʈ ������ �մϴ�.  �⺻��Ʈ, ����, ������ 20

		buffg.drawString("���� ���ϸ��� : "+mile+"��", 600, 70);
//		try {
//			buffg.drawString("���� ���ϸ��� : "+a_dao.getMileage("houuking")+"��", 600, 70);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		buffg.drawString("500���ϸ��� �̱� ", 50, 400);

		buffg.drawString("1000���ϸ��� -> 1�ð�", 600, 400);
		
		buffg.drawString("����", 390, 80);
		
		buffg.drawString("�ⱸ", 400, 770);

		
//	String path2 = this.getClass().getResource("/").getPath()+"game/";
//	URL path2 = this.getClass().getClassLoader().getResource("game.java");
//	String path2 = this.getClass().getab
//		URL path = getClass().getClassLoader().getResource("game/");
//	URL path2 = getClass().getClassLoader().getResource("game/images/");
//		String path=ClassLoader.getSystemClassLoader().getResource(".").getPath();
//		////////////////////////////
//		buffg.drawString("��� : "+path+"images/char_f1.png", 100, 270);
//		buffg.drawString("��� : "+path2, 100, 370);
//		////////////////////////////
		
	}

	public void KeyProcess() {
		if (KeyUp == true) {
			if (y > 20)
				y -= 5;
//ĳ���Ͱ� �������� ȭ�� ���� �� �Ѿ�� �մϴ�.

			player_Status = 0;
//�̵�Ű�� �������� �÷��̾� ���¸� 0���� �����ϴ�.
		}

		if (KeyDown == true) {
			
			
			/////////////////////////////////////// �̵����� ���밪���� �����ؾ��� //////////////////////////////////////////////////////////////////////////////
			if (y +90 < f_height)
//				if (y + Player_img[0].getHeight(null) < f_height)
				y += 5;
//ĳ���Ͱ� �������� ȭ�� �Ʒ��� �� �Ѿ�� �մϴ�.

			player_Status = 0;
//�̵�Ű�� �������� �÷��̾� ���¸� 0���� �����ϴ�.
		}

		if (KeyLeft == true) {
			if (x > 0)
				x -= 5;
//ĳ���Ͱ� �������� ȭ�� �������� �� �Ѿ�� �մϴ�.

			player_Status = 0;
//�̵�Ű�� �������� �÷��̾� ���¸� 0���� �����ϴ�.
		}

		if (KeyRight == true) {
//			if (x + Player_img[0].getWidth(null) < f_width)
			/////////////////////////////////////// �̵����� ���밪���� �����ؾ��� //////////////////////////////////////////////////////////////////////////////
				if (x+50 < f_width)
				x += 5;
//ĳ���Ͱ� �������� ȭ�� ���������� �� �Ѿ�� �մϴ�.

			player_Status = 0;
//�̵�Ű�� �������� �÷��̾� ���¸� 0���� �����ϴ�.
//			System.out.println(x);
			
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

//		case KeyEvent.VK_SPACE:
//			KeySpace = true;
//			break;
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

//		case KeyEvent.VK_SPACE:
//			KeySpace = false;
//			break;

		}
	}

	public void keyTyped(KeyEvent e) {
	}

}

