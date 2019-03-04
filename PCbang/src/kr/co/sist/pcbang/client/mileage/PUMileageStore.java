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
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.SQLException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.main.PUMainController;
import kr.co.sist.pcbang.client.main.PUMainView;

@SuppressWarnings("serial")
public class PUMileageStore extends JFrame implements KeyListener, Runnable {

	private int f_width;
	private int f_height;

	private int x, y;

	private boolean KeyUp = false;
	private boolean KeyDown = false;
	private boolean KeyLeft = false;
	private boolean KeyRight = false;

	private int cnt;

	private int player_Status = 0;

	private Thread th;

	private Toolkit tk = Toolkit.getDefaultToolkit();

	private Image[] Player_img, Player_img2, Player_img3, Player_img4;
	private Image BackGround_img; // 배경화면 이미지

	private Image buffImage;
	private Graphics buffg;
	
	private PUMileageDAO a_dao;
	private PUMainView pumvo;
	
	private int mile;
	
//	private PUMainView pumv;
//	private PUMainController pumc;
	
	public PUMileageStore() {
		
		a_dao=PUMileageDAO.getInstance();
		pumvo=PUMainView.getInstance();
		
		userName();
		
		String id = pumvo.id;
//		System.out.println(id);
		
//		System.out.println(id);
		init();
		start();

		setTitle("마일리지");
		// 화면 크기
		setSize(f_width, f_height);

		Dimension screen = tk.getScreenSize();

		int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2);

		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
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
		x = 320;  // 캐릭터의 위치
		y = 120;
		f_width =671;
		f_height = 506;

		String path = this.getClass().getResource("/").getPath()+"kr/co/sist/pcbang/client/mileage/";
//		URL path = (getClass().getClassLoader().getResource("e/"));
//		String path=ClassLoader.getSystemClassLoader().getResource(".").getPath();
//		URL path=getClass().getResource("background.png");
//		URL path=getClass().getResource("game");
		
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

//		player_Speed = 5; // 유저 캐릭터 움직이는 속도 설정
		
		//////// BGM
		  try
	        {
	            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path+"bgm/baram.wav"));
	            Clip clip = AudioSystem.getClip();
	            clip.stop();
	            clip.open(ais);
	            clip.start();
	        }
	        catch (Exception ex)
	        {
	        }
		

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

		Draw_Background(); // 배경 이미지 그리기 메소드 실행
		Draw_Player(); // 플레이어를 그리는 메소드 이름 변경

		Draw_StatusText();// 상태 표시 텍스트를 그리는 메소드 실행

		g.drawImage(buffImage, 0, 0, this);
	}

	public void Draw_Background() {

		buffg.clearRect(0, 0, f_width, f_height);

			buffg.drawImage(BackGround_img, 0, 0, this);
	}

	public void Draw_Player() {

		switch (player_Status) {

		case 0: // 평상시
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

		case 1: // 왼쪽으로 이동
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
			
		case 2: // 오른쪽으로 이동
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
		case 3: // 위쪽으로 이동
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
		x=320;
		y=120;
		
		player_Status=0;
	}
	
	// 특정 영역에 이동시 값을 반환
	public void getItem() {
			
			Random r=new Random();
			int addTime=r.nextInt(100);
			
			try {
				
			for(int i=70;i<150;i++) {
				if(x==0 && y==i) {
					
					if(a_dao.getMileage("houuking")>=500) {
						System.out.println(1);
						System.out.println(addTime);
						a_dao.updateMileage(addTime, 500, "houuking");
						System.out.println(2);
					JOptionPane.showMessageDialog(this, addTime+"분 당첨 !!! ㅊㅋㅊㅋ");
					System.out.println(3);
					userName();
					falseKey();
					} else{
						JOptionPane.showMessageDialog(null, "마일리지 부족");
						falseKey();
						
					}
					
					
				}
				if(x==625 && y==i) {
					
					if(a_dao.getMileage("houuking")>=1000) {
					
					a_dao.updateMileage(60, 1000, "houuking");
					JOptionPane.showMessageDialog(this, "1시간 추가 !");
					userName();
					falseKey();
				} else {
					JOptionPane.showMessageDialog(null, "마일리지 부족");
					falseKey();
				}
			}
			}} catch (SQLException e) {
				e.printStackTrace();
			}
			 
			
			for(int i=270;i<360;i++) {
				
				if(x==i && y==420) {
					JOptionPane.showMessageDialog(this, "ㅂㅂ");
					x=100;
					y=100;
					this.dispose();
//					return;
//					System.exit(0);
				}
				
			}
			
		}
	

	public void Draw_StatusText() { // 상태 체크용 텍스트를 그립니다.

		buffg.setFont(new Font("Defualt", Font.BOLD, 15));
//폰트 설정을 합니다.  기본폰트, 굵게, 사이즈 20

		buffg.drawString("보유 마일리지 : "+mile+"원", 500, 50);
//		try {
//			buffg.drawString("보유 마일리지 : "+a_dao.getMileage("houuking")+"원", 600, 70);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		buffg.drawString("500마일리지 뽑기 ", 40, 200);

		buffg.drawString("1000마일리지 -> 1시간", 500, 200);
		
//		buffg.drawString("도움말", 390, 80);
		
		buffg.drawString("출구", 250, 500);

		
//	String path2 = this.getClass().getResource("/").getPath()+"game/";
//	URL path2 = this.getClass().getClassLoader().getResource("game.java");
//	String path2 = this.getClass().getab
//		URL path = getClass().getClassLoader().getResource("game/");
//	URL path2 = getClass().getClassLoader().getResource("game/images/");
//		String path=ClassLoader.getSystemClassLoader().getResource(".").getPath();
//		////////////////////////////
//		buffg.drawString("경로 : "+path+"images/char_f1.png", 100, 270);
//		buffg.drawString("경로 : "+path2, 100, 370);
//		////////////////////////////
		
	}

	public void KeyProcess() {
		if (KeyUp == true) {
			if (y > 20)
				y -= 5;
//캐릭터가 보여지는 화면 위로 못 넘어가게 합니다.

			player_Status = 0;
//이동키가 눌려지면 플레이어 상태를 0으로 돌립니다.
		}

		if (KeyDown == true) {
			
			
			/////////////////////////////////////// 이동값을 절대값으로 변경해야함 //////////////////////////////////////////////////////////////////////////////
			if (y +90 < f_height)
//				if (y + Player_img[0].getHeight(null) < f_height)
				y += 5;
//캐릭터가 보여지는 화면 아래로 못 넘어가게 합니다.

			player_Status = 0;
//이동키가 눌려지면 플레이어 상태를 0으로 돌립니다.
		}

		if (KeyLeft == true) {
			if (x > 0)
				x -= 5;
//캐릭터가 보여지는 화면 왼쪽으로 못 넘어가게 합니다.

			player_Status = 0;
//이동키가 눌려지면 플레이어 상태를 0으로 돌립니다.
		}

		if (KeyRight == true) {
//			if (x + Player_img[0].getWidth(null) < f_width)
			/////////////////////////////////////// 이동값을 절대값으로 변경해야함 //////////////////////////////////////////////////////////////////////////////
				if (x+50 < f_width)
				x += 5;
//캐릭터가 보여지는 화면 오른쪽으로 못 넘어가게 합니다.

			player_Status = 0;
//이동키가 눌려지면 플레이어 상태를 0으로 돌립니다.
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

		}
	}

	public void keyTyped(KeyEvent e) {
	}

}

