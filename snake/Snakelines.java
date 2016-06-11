import java.net.*;
import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.lang.Math;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.*;
import java.lang.Object.*;
import java.util.ArrayList;
import java.util.Scanner;
// ����һ������
class Snake
{
	// x,y������ͷ��λ��,rate�����ٶȣ�dre������count��������,length�����ȣ�lastrate����ı��ٶȵ���һ�ε��ٶȣ�lastdre����ı䷽������һ�η���flag���ڱ�����Ƿ����ʧ������ˢ��ͼ���ʱ��
	private int x,y,rate,dre,count = 0,length,lastrate = 20,lastdre,flag = 0,f;
	// s ���������
	int[][] s = new int[300][2];
	// �ߵ��ƶ�������Ĳ���sleeptime���̵߳�˯��ʱ��,eat������Ǵ����Ƿ�Ե�ʳ���״̬
	public int changeSnake(int sleeptime,boolean eat)//����0����������2������1�����ƶ��ɹ�
	{
		if(eat == true)
		{
			flag = 1;
		}
		count += sleeptime;
		f = count;
		if(count >= rate)
		{
			count = 0;
			int tempx = x,tempy = y;
			if(x + dre/10 * 15 == s[1][0] && y + dre%10 * 15 == s[1][1])
			{
				dre = lastdre;
			}
			x += dre/10 * 15;//�����ʾ�ֱ���10��-10,1��-1������ı䷽��
			y += dre%10 * 15;
			for(int i = 3; i < length; i++)//�������������������״̬
			{
				if(x == s[i][0] && y == s[i][1])
				{
					return 0;
				}
			}
			if(x > 15 * 30)
				x = 15;
			else if(x < 15)
				x = 15 * 30;
			if(y > 15 * 20)
				y = 15;
			else if(y < 15)
				y = 15 * 20;
			s[0][0] = x;
			s[0][1] = y;
			int a,b,i;
			for(i = 1; i < length - 1; i++)
			{
				a = s[i][0];
				b = s[i][1];
				s[i][0] = tempx;
				s[i][1] = tempy;
				tempx = a;
				tempy = b;
			}
			if(flag == 1)
			{
				// System.out.println(length);
				length++;
				s[i + 1][0] = s[i][0];
				s[i + 1][1] = s[i][1];
				flag = 0;
			}
			s[i][0] = tempx;
			s[i][1] = tempy;
			
			return 1;
		}
		return 2;
	}
	// ��ó���
	public void changeLength()
	{
		length++;
	}
	public void changeLength(int length)
	{
		this.length = length;
	}
	// ����һ���㣬�ж��Ƿ�������
	public int local(int x,int y)
	{
		for(int i = 0; i < length; i++)
		{
			if(s[i][0] == x && s[i][1] == y)
				return 0;
		}
		return 1;
	}
	// �ı䷽��lastdre�洢�ı�ǰ���ٶ�
	public void changeDre(int dre)
	{
		lastdre = this.dre;
		this.dre = dre;
	}
	// �ı��ٶȣ�lastrate����ı�ǰ���ٶ�
	public void changeRate(int rate)
	{
		if(this.rate != rate)
			lastrate = this.rate;
		this.rate = rate;
	}
	// �õ�lastrate
	public int getLastrate()
	{
		return lastrate;
	}
	// �õ���ͷ����
	public int getx()
	{
		return x;
	}
	// �õ���ͷy����
	public int gety()
	{
		return y;
	}
	// �õ��ٶ�
	public int getRate()
	{
		return rate;
	}
	public void changeXY(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	// ���ݴ����ֵ���õ����������
	public int getX(int i)
	{
		return s[i][0];
	}
	public int getY(int i)
	{
		return s[i][1];
	}
	// �õ�����
	public int getDre()
	{
		return dre;
	}
	public void changeLocal(int i,int x,int y)
	{
		s[i][0] = x;
		s[i][1] = y;
	}
	// �õ�����
	public int getLength()
	{
		return length;
	}
	// ��������
	public void draw(Graphics2D g)
	{
		
		for(int i = 0; i < length; i++)
		{
			g.setColor(Color.RED);
			g.drawRect(s[i][0],s[i][1],15,15);
		}
	}
	// ��ʼ������
	public void setSnake()
	{
		flag = 0;
		count = 0;
		int a = (int)(Math.random() * 3);
		switch (a)
		{
			case 0:
				dre = 1;
				break;
			case 1:
				dre = 10;
				break;
			case 2:
				dre = -1;
				break;
			case 3:
				dre = -10;
				break;
		}
		length = 2;
		x = 10 * 15;
		y = 7 * 15;
		flag = 0;
	}
}
// ����һ��ʳ�����
class Dot
{
	// x,y�������꣬flagû�ã�len ����������ǰ�ô洢�ĳ��ȣ�pl����������״̬
	private int x,y,flag = 0,len = 0,pl;
	private boolean eat;
	//����һ�������ȴ洢���е����꣬������ǽ��������ǰ����len ����֪ǰ���ж��ٸ�
	public ArrayList<Dot> wall = new ArrayList<Dot>();
	// ��ʼ�����������еĵ㶪���ȥ
	public void setList()
	{
		for(int i = 1; i <= 29; i++)
		{
			for(int j = 1; j <= 19; j++)
			{
				Dot dot = new Dot();
				dot.x = (i + 1) * 15;
				dot.y = (j + 1) * 15;
				wall.add(dot);
			}
		}
	}
	// �����ߣ���Ϊ�᲻��ˢ�£�������Ҫ����д
	public void addList()
	{
		for(int i = 0; i < len; i++)
		{
			Dot dot = new Dot();
			int b = wall.indexOf(-1);
			if(b == -1)
				break;
			dot = wall.get(b);
			dot.pl = 0;
			wall.remove(-1);
			wall.add(dot);
			len--;
		}
	}
	// ��дequals�жϷ����������ж�״̬
	public boolean equals(int pl)
	{
		// System.out.println("=======");
		if( this.pl == pl)
			return true;
		return false;
	}
	// ��ʼ��ǰ�ó���
	public void changeLen()
	{
		len = 0;
	}
	// Ҳ�Ǽ������꣬��������������ǽ�����᲻�ϵ�ˢ��
	public void checkList(int x,int y,int b)
	{
		for(int i = len; i < wall.size(); i++)
		{
			if(x == wall.get(i).x && y == wall.get(i).y)
			{
				wall.remove(i);
				Dot dot = new Dot();
				dot.x = x;
				dot.y = y;
				dot.pl = b;
				wall.add(0,dot);
				len++;
				return ;
			}
		}
	}
	// �ı�ʳ���״̬
	public void changeEat()
	{
		if(eat == true)
			eat = false;
		else
			eat = true;
	}
	// �õ�x����
	public int getx()
	{
		return x;
	}
	// �õ�y����
	public int gety()
	{
		return y;
	}
	// ��ʼ���㣬��len �Ժ��ʼ������Ϊǰ��洢����ǽ���ߵ�����
	public void setDot()
	{
		flag = 0;
		eat = false;
		int index = (int)( Math.random() * (wall.size() - len)) + len;
		this.x = wall.get(index).x;
		this.y = wall.get(index).y;
		flag = 1;
	}
	// ���Ƶ�
	public void draw(Graphics2D g)
	{
		if(flag == 0)
			return ;
		g.setColor(Color.RED);
		g.fillRect(x,y,15,15);
	}
}
// дһ��ǽ��
class Wall
{
	// ��ά����洢ǽ������
	private int[][] wallxy = new int[100][2];
	int flag = 0;
	// ��ʼ��ǽ����ͼֻ��һ����д�úܼ�
	public void setWall()
	{
		int i;
		wallxy[0][0] = 15 * 18;
		wallxy[0][1] = 75;
		wallxy[10][1] = 75;
		wallxy[10][0] = 75;
		for(i = 1; i < 10; i++)
		{
			wallxy[i][0] = wallxy[i - 1][0] + 15;
			wallxy[i][1] = 75;
		}
		i++;
		for(;i < 20; i++)
		{
			wallxy[i][0] = 75;
			wallxy[i][1] = wallxy[i - 1][1] + 15;
		}
	}
	// ����wall�����꣬����ʹ�õ��Ƿ��ض�ά����
	public int[][] getWall()
	{
		return wallxy;
	}
	// ����ǽ
	public void draw(Graphics2D g)
	{
		for(int i = 0; i < 20; i++)
		{
			g.setColor(Color.black);
			g.fillRect(wallxy[i][0],wallxy[i][1],15,15);
		}
	}
}
// ����һ���ļ��洢�Ͷ�ȡ����
class saveAndRead
{
	public void save(Snake s,Dot dot)
	{
		try(
			// һ���Դ���PrintStream�����
			PrintStream ps = new PrintStream(new FileOutputStream("snakelines.txt")))
		{
			// ����׼����ض���PS�����
			System.setOut(ps);
			System.out.println(s.getLength() + " " + s.getDre() + " " + s.getLastrate());
			System.out.println(s.getX(0) + " " + s.getY(0));
			for(int i = 0; i < s.getLength(); i++)
				System.out.println(s.getX(i) +" " +  s.getY(i));
			System.out.println(s.getLength() - 2);
			System.out.println(dot.getx() + " " + dot.gety());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public Snake readSnake()
	{
		Snake s = new Snake();
		try(
			FileInputStream fis = new FileInputStream("snakelines.txt"))
		{
			// ����׼�����ض���fis������
			System.setIn(fis);
			// ʹ��System.in����Scanner�������ڻ�ȡ��׼����
			Scanner sc = new Scanner(System.in);
			// ��������һ��ֻ�ѻس���Ϊ�ָ���
			//sc.useDelimiter("\n");
			int a = sc.nextInt();
			s.changeLength(a);
			a = sc.nextInt();
			s.changeDre(a);
			System.out.println(a);
			a = sc.nextInt();
			s.changeRate(a);
			int b = s.getLength();
			s.changeXY(sc.nextInt(),sc.nextInt());
			for(int i = 0; i < b; i++)
			{
				s.changeLocal(i,sc.nextInt(),sc.nextInt());
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return s;
	}
}
// ���࣬��Ҫ�ǽ��棬�������̻߳��ƣ��Լ���������
class Snakelines extends JFrame
{
	// ����һ����׼��͸�
	private static final int WIDTH = 500,HEIGHT = 400;
	// ����ɼ�
	private int grade,bestgrade;
	// dre������rate�����ٶȣ�isGo�����ж��Ƿ������SLEEPTIME�����߳�˯��ʱ��
	int dre,Rate,isGo = 1,flag = 1,SLEEPTIME = 20;
	// k�����Ƿ�ٵ��Ĳ�����pp���ڿ��ƽ�����Ϸ�ĶԻ�����ʾ
	private boolean k = false,pp = false;
	// �̳л������࣬������newһ���µĻ�����
	MyCanvas tableArea = new MyCanvas();
	// �Ƿ������Ϸ�ĶԻ���
	JDialog d = new JDialog(this,"�Ƿ������Ϸ",true);
	// Jpanel�������ݵ�����������Button��ť
	JPanel p = new JPanel();
	JButton gameContinue = new JButton("������Ϸ");
	JButton gameOver = new JButton("������Ϸ");
	// ����һ��ѡ���Ƿ��ȡ�洢�ĶԻ���
	JDialog read = new JDialog(this,"��ȡ�浵",true);
	JPanel readd = new JPanel();
	JButton readyes = new JButton("��");
	JButton readno = new JButton("��");
	// ����һ��ѡ���Ƿ�洢�ĶԻ���
	JDialog save = new JDialog(this,"�浵",true);
	JPanel savee = new JPanel();
	JButton saveyes = new JButton("��");
	JButton saveno = new JButton("��");
	// ����һ���Ƿ��ȡ���ݵĶԻ���
	//JDialog Read = new JDialog("")
	// �ٶ�ѡ��ĶԻ���
	JDialog dialogdif = new JDialog(this,"�ٶ�ѡ��",true);
	JPanel paneldif = new JPanel();
	JButton dif20 = new JButton("����ٶ�");
	JButton dif40 = new JButton("���ٶ�");
	JButton dif60 = new JButton("���ٶ�");
	JButton dif80 = new JButton("���ٶ�");
	JButton dif100 = new JButton("����ٶ�");
	// ����һ���˵��������ݵ�������ļ���ѡ��
	JMenuBar mb = new JMenuBar();
	JMenu game = new JMenu("��Ϸ");
	JMenuItem newGame = new JMenuItem("����Ϸ");
	JMenuItem dif = new JMenuItem("�ٶ�ѡ��");
	JMenuItem exit = new JMenuItem("�˳�");
	JMenuItem stop = new JMenuItem("��ͣ/����");
	JMenuItem saveItem = new JMenuItem("�浵");
	// ����һ������ͼ��������˫����
	BufferedImage offersetImage = new BufferedImage(WIDTH + 200, HEIGHT + 50,BufferedImage.TYPE_3BYTE_BGR);
	Snake s;
	Dot dot = new Dot();
	Wall wall = new Wall();
	// ����һ���������ڴ洢ǽ������
	int[][] qq =  new int[20][2];
	// ʹ��AudioClip�����࣬�洢������Ч
	AudioClip[] audioClip = new AudioClip[2];
	// �����Ƕ��̣߳���newһ�µ��̳߳���
	ThreadUpadte gg = new ThreadUpadte();
	saveAndRead sar = new saveAndRead();
	// ������Ч
	public void Audio()
	{
		// ʹ��File�����࣬�洢�����ļ���Ƶ
		File[] file = new File[]{new File("5012.wav"),new File("3139.wav")};
		// �������ļ���·��ת��ΪURL·��
		for(int i = 0; i < 2; i++)
		{
			try
			{
				audioClip[i] = Applet.newAudioClip(file[i].toURI().toURL());
				System.out.println(file[i].toURI().toURL());
			}
			catch(MalformedURLException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	// ������ʹ�ù�������дһ������Ҳ���ԣ����������÷���һ��
	public Snakelines()
	{
		Audio();
		// �õ�ǽ������
		qq = wall.getWall();
		// ���ô��ڴ�С
		this.setBounds(300, 200, WIDTH + 200, HEIGHT + 50);
		// ���ô��ڿɷ����죬�������õ��ǲ���������
		this.setResizable(false);
		readd.add(readyes,BorderLayout.EAST);
		readyes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s = sar.readSnake();
				wall.setWall();
				dot.wall.clear();
				dot.setList();
				read.setVisible(false);
			}
		});
		readd.add(readno,BorderLayout.WEST);
		readno.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// ��ʼ��
				s = new Snake();
				s.setSnake();
				s.changeRate(120);
				wall.setWall();
				dot.wall.clear();
				dot.setList();
				read.setVisible(false);
			}
		});
		read.add(readd);
		read.setBounds(400,300,150,100);
		read.setVisible(false);
		read.setResizable(false);
		savee.add(saveyes,BorderLayout.EAST);
		saveyes.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sar.save(s,dot);
				save.setVisible(false);
			}
		});
		savee.add(saveno,BorderLayout.WEST);
		saveno.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				save.setVisible(false);
			}
		});
		save.add(savee);
		save.setBounds(400,300,150,100);
		read.setVisible(false);
		save.setResizable(false);
		try
		{
			FileInputStream fis = new FileInputStream(
			"snakelines.txt");
			if(fis.read() != -1)
			{
				read.setVisible(true);
			}
			else 
			{
				// ��ʼ��
				s = new Snake();
				s.setSnake();
				s.changeRate(120);
				wall.setWall();
				dot.wall.clear();
				dot.setList();
			}
		
		}
		catch (IOException ie)
		{
			ie.printStackTrace();
		}
		// wҲ�Ǵ洢ǽ��������һ��ʼд�ظ��ˣ�����û�и�
		int[][] w = wall.getWall();
		// ��ǽ��������뵽�������������ȥ
		for(int i = 0; i < 20; i++)
		{
			dot.checkList(w[i][0],w[i][1],1);
		}
		// ��ʼ��ʳ��
		dot.setDot();
		// ������������
		p.add(gameContinue,BorderLayout.EAST);
		p.add(gameOver,BorderLayout.WEST);
		d.add(p);
		d.setBounds(400,300,200,80);
		d.setResizable(false);
		dialogdif.setBounds(400,300,150,250);
		paneldif.add(dif40);
		// ����¼�������
		dif40.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(40);
				dialogdif.setVisible(false);
				pp = false;
			}
		});
		paneldif.add(dif60);
		dif60.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(80);dialogdif.setVisible(false);
				pp = false;
			}
		});
		paneldif.add(dif80);
		dif80.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(120);dialogdif.setVisible(false);
				pp = false;
			}
		});
		paneldif.add(dif100);
		dif100.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(200);dialogdif.setVisible(false);
				pp = false;
			}
		});
		dialogdif.add(paneldif);
		dialogdif.setVisible(false);
		// Ϊ������Ϸ�����ť��ӳ�ʼ������
		gameContinue.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				d.setVisible(false);
				s.setSnake();
				
				flag = 1;
				grade = 0;
				dot.wall.clear();
				dot.setList();
				int[][] w = wall.getWall();
				for(int i = 0; i < 20; i++)
				{
					dot.checkList(w[i][0],w[i][1],1);
				}
				dot.setDot();
				pp = false;
				SLEEPTIME = 20;
			}
		});
		// ������Ϸֱ���˳�
		gameOver.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		stop.setAccelerator(KeyStroke.getKeyStroke('Z',InputEvent.CTRL_MASK));
		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(s.getLastrate() > 1000 || pp == false)
				{
					pp = true;
					s.changeRate(100000);
				}else
					s.changeRate(s.getLastrate());
			}
		});
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(10000);
				save.setVisible(true);
				System.exit(0);
			}
		});
		newGame.setAccelerator(KeyStroke.getKeyStroke('N',
			InputEvent.CTRL_MASK));
		// ����Ϸ��ӳ�ʼ�����ܣ��������ÿ�ݼ���ctrl+n
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.setSnake();
				flag = 1;
				grade = 0;
				dot.wall.clear();
				dot.setList();
				int[][] w = wall.getWall();
				for(int i = 0; i < 20; i++)
				{
					dot.checkList(w[i][0],w[i][1],1);
				}
				dot.setDot();
				pp = false;
			}
		});
		dif.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(s.getLastrate() > 1000 || pp == false)
				{
					pp = true;
					s.changeRate(100000);
				}else
					s.changeRate(s.getLastrate());
				dialogdif.setVisible(true);
			}
		});
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				s.changeRate(10000);
				save.setVisible(true);
				s.changeRate(s.getLastrate());
			}
		});
		game.add(newGame);
		game.add(stop);
		game.add(dif);
		game.addSeparator();
		game.add(saveItem);
		game.addSeparator();
		game.add(exit);
		mb.add(game);
		this.setJMenuBar(mb);
		tableArea.setBounds(0,0,700,420);
		// ��ӻ���
		this.add(tableArea);
		// ��ӷ�����Ϳո�������ɿ���ͬ״̬���¼�������
		KeyAdapter keyProcessor = new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{	
				dre = s.getDre();
				
				switch ( ke.getKeyCode())
				{
					case KeyEvent.VK_UP:
						if(dre!= 1 && dre != -1)
						{
							s.changeDre(-1);
						}
						break;
					case KeyEvent.VK_DOWN:
						if(dre != -1 && dre != 1)
						{
							s.changeDre(1);
						}
						break;
					case KeyEvent.VK_LEFT:
						if(dre != -10 && dre != 10)
						{
							s.changeDre(-10);
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(dre != -10 && dre != 10)
						{
							s.changeDre(10);
						}
						break;
					case KeyEvent.VK_SPACE:
						s.changeRate(20);
				}
			}
			public void keyReleased(KeyEvent ke)
			{
				if(ke.getKeyCode() == KeyEvent.VK_SPACE)
				{
					s.changeRate(s.getLastrate());
				}
			}
		};
		this.addKeyListener(keyProcessor);
		tableArea.addKeyListener(keyProcessor);
		this.setTitle("̰����");
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				s.changeRate(10000);
				save.setVisible(true);
			}
		});
		this.setVisible(true);
		new Thread(gg).start();
	}
	// ͨ���ӿ�ʵ�����̵߳���
	class ThreadUpadte implements Runnable 
	{
		//boolean pl;
		public void run() 
		{
			// ʵ�ֲ�ͣ�Ļ���
			while (true) 
			{
				try 
				{
					// �߳�����
					Thread.sleep(SLEEPTIME);
					// ÿ�β��ϵ���k��ֵΪfalse��ʼ��
					k = false;
					// ����Ե�ʳ��
					if(s.getx() == dot.getx() && s.gety() == dot.gety())
					{
						dot.changeEat();
						dot.setDot();
						grade++;
						k = true;
						audioClip[0].play();
						dot.addList();
						for(int i = 0; i < s.getLength(); i++)
						{
							dot.checkList(s.getX(i),s.getY(i),-1);
						}
					}
					// �õ��ƶ�֮���״̬
					isGo = s.changeSnake(SLEEPTIME,k);
					if(isGo == 1)
					{
						for(int i = 0; i < 20; i++)
						{
							
							if(qq[i][0] == s.getx() && qq[i][1] == s.gety())
							{
								isGo = 0;
								break;
							}
						}
					}
					// ���»���
					tableArea.repaint();
					if(isGo == 0)
					{
						audioClip[1].play();
						SLEEPTIME = 10000;
						d.setVisible(true);
					}
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	class MyCanvas extends Canvas
	{
		// update����ʵ�ֵ��Ǹ���
		public void update(Graphics g)
		{
			// ʼ�����ڻ���ͼ���ϻ���
			Graphics2D g2d = (Graphics2D) offersetImage.getGraphics();
			g2d.setColor(Color.white);
			g2d.fillRect(0,0,500 + 200,400 + 150);
			g2d.setColor(Color.black);
			g2d.drawRect(15,15,15 * 30 + 200,15 * 20);
			g2d.setColor(Color.black);
			g2d.drawLine(15+15 * 30,15,15 + 15 * 30,15 + 15 * 20);
			g2d.setColor(Color.blue);
			g2d.setFont(new Font("Times", Font.BOLD,20));
			g2d.drawString("��ǰ����Ϊ:\n    " + String.valueOf(s.getLength() - 2),20 + 450,100);
			if(isGo == 0)
			{
				g2d.setColor(new Color(255, 0, 0));
				g2d.setFont(new Font("Times" , Font.BOLD, 30));
				g2d.drawString("��Ϸ����",150,200);
				flag = 0;
			}
			else if(flag == 1)
			{
				s.draw(g2d);
				dot.draw(g2d);
				wall.draw(g2d);
			}
			else
			{
				g2d.setColor(new Color(255, 0, 0));
				g2d.setFont(new Font("Times" , Font.BOLD, 30));
				g2d.drawString("��Ϸ����",150,200);
			}
			// ������ͼƬ�ŵ�������ȥ
			g.drawImage(offersetImage, 0, 0, null);
			g.dispose();
		}
		// ��д���Ʒ���
		public void paint(Graphics g)
		{
			update(g);
		}
	}
	public static void main(String[] args)
	{
		// ������ǹ�������ʹ��
		new Snakelines();
	}
}