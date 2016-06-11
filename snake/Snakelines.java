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
// 建立一个蛇类
class Snake
{
	// x,y代表蛇头的位置,rate代表速度，dre代表方向，count用来计数,length代表长度，lastrate代表改变速度的上一次的速度，lastdre代表改变方向后的上一次方向，flag用于标记蛇是否吃了失误（用于刷新图像的时候）
	private int x,y,rate,dre,count = 0,length,lastrate = 20,lastdre,flag = 0,f;
	// s 代表的是蛇
	int[][] s = new int[300][2];
	// 蛇的移动，传入的参数sleeptime是线程的睡眠时间,eat代表的是传入是否吃到食物的状态
	public int changeSnake(int sleeptime,boolean eat)//返回0代表死亡，2不动，1代表移动成功
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
			x += dre/10 * 15;//方向表示分别是10，-10,1，-1，方便改变方向
			y += dre%10 * 15;
			for(int i = 3; i < length; i++)//如果碰到蛇身，返回死亡状态
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
	// 获得长度
	public void changeLength()
	{
		length++;
	}
	public void changeLength(int length)
	{
		this.length = length;
	}
	// 传入一个点，判断是否处于蛇身
	public int local(int x,int y)
	{
		for(int i = 0; i < length; i++)
		{
			if(s[i][0] == x && s[i][1] == y)
				return 0;
		}
		return 1;
	}
	// 改变方向，lastdre存储改变前的速度
	public void changeDre(int dre)
	{
		lastdre = this.dre;
		this.dre = dre;
	}
	// 改变速度，lastrate代表改变前的速度
	public void changeRate(int rate)
	{
		if(this.rate != rate)
			lastrate = this.rate;
		this.rate = rate;
	}
	// 得到lastrate
	public int getLastrate()
	{
		return lastrate;
	}
	// 得到蛇头坐标
	public int getx()
	{
		return x;
	}
	// 得到蛇头y坐标
	public int gety()
	{
		return y;
	}
	// 得到速度
	public int getRate()
	{
		return rate;
	}
	public void changeXY(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	// 根据传入的值来得到蛇身的坐标
	public int getX(int i)
	{
		return s[i][0];
	}
	public int getY(int i)
	{
		return s[i][1];
	}
	// 得到方向
	public int getDre()
	{
		return dre;
	}
	public void changeLocal(int i,int x,int y)
	{
		s[i][0] = x;
		s[i][1] = y;
	}
	// 得到长度
	public int getLength()
	{
		return length;
	}
	// 绘制蛇身
	public void draw(Graphics2D g)
	{
		
		for(int i = 0; i < length; i++)
		{
			g.setColor(Color.RED);
			g.drawRect(s[i][0],s[i][1],15,15);
		}
	}
	// 初始化蛇身
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
// 创建一个食物的类
class Dot
{
	// x,y代表坐标，flag没用，len 代表链表中前置存储的长度，pl代表这个点的状态
	private int x,y,flag = 0,len = 0,pl;
	private boolean eat;
	//创建一个链表，先存储所有的坐标，后将蛇身，墙丢入链表前方，len 来得知前面有多少个
	public ArrayList<Dot> wall = new ArrayList<Dot>();
	// 初始化链表，把所有的点丢入进去
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
	// 丢入蛇，因为会不断刷新，所以需要重新写
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
	// 重写equals判断方法，用于判断状态
	public boolean equals(int pl)
	{
		// System.out.println("=======");
		if( this.pl == pl)
			return true;
		return false;
	}
	// 初始化前置长度
	public void changeLen()
	{
		len = 0;
	}
	// 也是加入坐标，但是这个加入的是墙，不会不断的刷新
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
	// 改变食物的状态
	public void changeEat()
	{
		if(eat == true)
			eat = false;
		else
			eat = true;
	}
	// 得到x坐标
	public int getx()
	{
		return x;
	}
	// 得到y坐标
	public int gety()
	{
		return y;
	}
	// 初始化点，从len 以后初始化，因为前面存储的是墙和蛇的坐标
	public void setDot()
	{
		flag = 0;
		eat = false;
		int index = (int)( Math.random() * (wall.size() - len)) + len;
		this.x = wall.get(index).x;
		this.y = wall.get(index).y;
		flag = 1;
	}
	// 绘制点
	public void draw(Graphics2D g)
	{
		if(flag == 0)
			return ;
		g.setColor(Color.RED);
		g.fillRect(x,y,15,15);
	}
}
// 写一个墙类
class Wall
{
	// 二维数组存储墙的坐标
	private int[][] wallxy = new int[100][2];
	int flag = 0;
	// 初始化墙，地图只有一个，写得很简单
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
	// 返回wall的坐标，这里使用的是返回二维数组
	public int[][] getWall()
	{
		return wallxy;
	}
	// 绘制墙
	public void draw(Graphics2D g)
	{
		for(int i = 0; i < 20; i++)
		{
			g.setColor(Color.black);
			g.fillRect(wallxy[i][0],wallxy[i][1],15,15);
		}
	}
}
// 创建一个文件存储和读取的类
class saveAndRead
{
	public void save(Snake s,Dot dot)
	{
		try(
			// 一次性创建PrintStream输出流
			PrintStream ps = new PrintStream(new FileOutputStream("snakelines.txt")))
		{
			// 将标准输出重定向到PS输出流
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
			// 将标准输入重定向到fis输入流
			System.setIn(fis);
			// 使用System.in创建Scanner对象，用于获取标准输入
			Scanner sc = new Scanner(System.in);
			// 增加下面一行只把回车作为分隔符
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
// 主类，主要是界面，另外用线程绘制，以及画布调用
class Snakelines extends JFrame
{
	// 定义一个标准宽和高
	private static final int WIDTH = 500,HEIGHT = 400;
	// 定义成绩
	private int grade,bestgrade;
	// dre代表方向，rate代表速度，isGo用于判断是否结束，SLEEPTIME代表线程睡眠时间
	int dre,Rate,isGo = 1,flag = 1,SLEEPTIME = 20;
	// k代表是否迟到的参数，pp用于控制结束游戏的对话框显示
	private boolean k = false,pp = false;
	// 继承画布的类，这里是new一个新的画布类
	MyCanvas tableArea = new MyCanvas();
	// 是否借宿游戏的对话框
	JDialog d = new JDialog(this,"是否结束游戏",true);
	// Jpanel容器收容的是下面两个Button按钮
	JPanel p = new JPanel();
	JButton gameContinue = new JButton("继续游戏");
	JButton gameOver = new JButton("结束游戏");
	// 定义一个选择是否读取存储的对话框
	JDialog read = new JDialog(this,"读取存档",true);
	JPanel readd = new JPanel();
	JButton readyes = new JButton("是");
	JButton readno = new JButton("否");
	// 定义一个选择是否存储的对话框
	JDialog save = new JDialog(this,"存档",true);
	JPanel savee = new JPanel();
	JButton saveyes = new JButton("是");
	JButton saveno = new JButton("否");
	// 创建一个是否读取数据的对话框
	//JDialog Read = new JDialog("")
	// 速度选择的对话框
	JDialog dialogdif = new JDialog(this,"速度选择",true);
	JPanel paneldif = new JPanel();
	JButton dif20 = new JButton("最高速度");
	JButton dif40 = new JButton("高速度");
	JButton dif60 = new JButton("中速度");
	JButton dif80 = new JButton("低速度");
	JButton dif100 = new JButton("最低速度");
	// 定义一个菜单条，收容的是下面的几个选项
	JMenuBar mb = new JMenuBar();
	JMenu game = new JMenu("游戏");
	JMenuItem newGame = new JMenuItem("新游戏");
	JMenuItem dif = new JMenuItem("速度选择");
	JMenuItem exit = new JMenuItem("退出");
	JMenuItem stop = new JMenuItem("暂停/继续");
	JMenuItem saveItem = new JMenuItem("存档");
	// 定义一个缓冲图像，这里是双缓冲
	BufferedImage offersetImage = new BufferedImage(WIDTH + 200, HEIGHT + 50,BufferedImage.TYPE_3BYTE_BGR);
	Snake s;
	Dot dot = new Dot();
	Wall wall = new Wall();
	// 定义一个数组用于存储墙的坐标
	int[][] qq =  new int[20][2];
	// 使用AudioClip抽象类，存储两个音效
	AudioClip[] audioClip = new AudioClip[2];
	// 这里是多线程，先new一新的线程出来
	ThreadUpadte gg = new ThreadUpadte();
	saveAndRead sar = new saveAndRead();
	// 加载音效
	public void Audio()
	{
		// 使用File抽象类，存储两个文件音频
		File[] file = new File[]{new File("5012.wav"),new File("3139.wav")};
		// 将两个文件的路径转化为URL路径
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
	// 这里是使用构造器，写一个方法也可以，构造器调用方便一点
	public Snakelines()
	{
		Audio();
		// 得到墙的坐标
		qq = wall.getWall();
		// 设置窗口大小
		this.setBounds(300, 200, WIDTH + 200, HEIGHT + 50);
		// 设置窗口可否拉伸，这里设置的是不可以拉伸
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
				// 初始化
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
				// 初始化
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
		// w也是存储墙，这里是一开始写重复了，后来没有改
		int[][] w = wall.getWall();
		// 将墙的坐标加入到点类里面的链表去
		for(int i = 0; i < 20; i++)
		{
			dot.checkList(w[i][0],w[i][1],1);
		}
		// 初始化食物
		dot.setDot();
		// 加入各种组件；
		p.add(gameContinue,BorderLayout.EAST);
		p.add(gameOver,BorderLayout.WEST);
		d.add(p);
		d.setBounds(400,300,200,80);
		d.setResizable(false);
		dialogdif.setBounds(400,300,150,250);
		paneldif.add(dif40);
		// 添加事件监听器
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
		// 为继续游戏这个按钮添加初始化功能
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
		// 结束游戏直接退出
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
		// 新游戏添加初始化功能，上面设置快捷键，ctrl+n
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
		// 添加画布
		this.add(tableArea);
		// 添加方向键和空格键按下松开不同状态的事件监听器
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
		this.setTitle("贪吃蛇");
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
	// 通过接口实现新线程的类
	class ThreadUpadte implements Runnable 
	{
		//boolean pl;
		public void run() 
		{
			// 实现不停的绘制
			while (true) 
			{
				try 
				{
					// 线程休眠
					Thread.sleep(SLEEPTIME);
					// 每次不断的让k的值为false初始化
					k = false;
					// 如果吃到食物
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
					// 得到移动之后的状态
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
					// 重新绘制
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
		// update方法实现的是覆盖
		public void update(Graphics g)
		{
			// 始终是在缓冲图像上绘制
			Graphics2D g2d = (Graphics2D) offersetImage.getGraphics();
			g2d.setColor(Color.white);
			g2d.fillRect(0,0,500 + 200,400 + 150);
			g2d.setColor(Color.black);
			g2d.drawRect(15,15,15 * 30 + 200,15 * 20);
			g2d.setColor(Color.black);
			g2d.drawLine(15+15 * 30,15,15 + 15 * 30,15 + 15 * 20);
			g2d.setColor(Color.blue);
			g2d.setFont(new Font("Times", Font.BOLD,20));
			g2d.drawString("当前分数为:\n    " + String.valueOf(s.getLength() - 2),20 + 450,100);
			if(isGo == 0)
			{
				g2d.setColor(new Color(255, 0, 0));
				g2d.setFont(new Font("Times" , Font.BOLD, 30));
				g2d.drawString("游戏结束",150,200);
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
				g2d.drawString("游戏结束",150,200);
			}
			// 将缓冲图片放到画布上去
			g.drawImage(offersetImage, 0, 0, null);
			g.dispose();
		}
		// 重写绘制方法
		public void paint(Graphics g)
		{
			update(g);
		}
	}
	public static void main(String[] args)
	{
		// 这里就是构造器的使用
		new Snakelines();
	}
}