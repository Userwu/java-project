
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;  
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.Border;
class Map {
	//map是地图，number代表雷的数量
	private int[][] map = new int[100][100];
	public int[] dir = {1,0,-1,0,1,1,-1,-1,
						0,1,0,-1,1,-1,1,-1};
	private ArrayList<local> alc = new ArrayList<local>();
	private int len;
	//0代表空，-1代表雷
	//number代表雷数，WIDTH,HEIGHT代表宽和高的格数
	//设定所有的雷所在的位置，规则是在xy位置和与其周围8个格子不会有雷,在每个雷附近
	public Map(int x,int y,int number,int WIDTH,int HEIGHT)
	{
		alc.clear();
		local lc;
		len = 0;
		int flag = 0;
		for(int i = 0; i < 100; i++)
		{
			for(int j = 0; j < 100; j++)
			{
				map[i][j] = 0;
			}
		}
		for(int i = 0; i < WIDTH; i++)
		{
			for(int j = 0; j < HEIGHT; j++)
			{
				flag = 0;
				lc = new local();
				lc.setXY(i,j);
				if(i == x && j == y)
					continue;
				for(int a = 0; a < 8; a++)
				{
					int wx = i + dir[a],wy = j + dir[a + 8];
					if(wx== x &&  wy== y)
						flag = 1;
				}
				if(flag == 0)
					alc.add(lc);
			}
		}
		int dex;
		int[][] temp = new int[10000][2];
		for(int i = 0; i < number; i++)
		{
			dex = (int)(Math.random() * (alc.size()));
			int a = alc.get(dex).x,b = alc.get(dex).y;
			map[a][b] = 11;
			alc.remove(dex);
			temp[i][0] = a;
			temp[i][1] = b;
		}
		for(int i = 0; i < number; i++)
		{
			int a = temp[i][0],b = temp[i][1];
			for(int c = 0; c < 8; c++)
			{
				int wx = a + dir[c], wy = b + dir[c + 8];
				if( wx >= 0 && wx < WIDTH && wy >= 0 && wy <= HEIGHT)
				{
					if(map[wx][wy] != 11)
						map[wx][wy] += 1;
				}
			}
		}
	}
	
	public int getLen()
	{
		return len;
	}
	class local
	{
		int x,y;
		public void setXY(int x,int y)
		{
			this.x = x;
			this.y = y;
		}
		public local(){};
		public local(int a,int b)
		{
			this.x = a;
			this.y = b;
		}
	}
	public int[][] getMap() {
		return map;
	}
}
// 这是一个实现显示统计消息的类
class Message extends JDialog
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList list1;
	private JPanel p0,p1,p2,p3,p4;
	private JLabel l1,l2,l3,l4,l5,l6,l7;
	private JButton b1,b2;
	private String s[];
	MyFrame mf;
	public Message(MyFrame mf)
	{
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		this.mf = mf;
		this.setTitle("扫雷统计信息");
		s=new String[]{"初级","中级","高级"};
		p0=new JPanel();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		l1=new JLabel();
		l2=new JLabel();
		l3=new JLabel();
		l4=new JLabel();
		l5=new JLabel();
		l6=new JLabel();
		l7=new JLabel();
		
		l1.setText("已玩游戏：");
		l2.setText("已胜游戏：");
		l3.setText("获胜率：");
		l4.setText("最多连胜：");
		l5.setText("最多连败：");
		l6.setText("当前连局：");
		b1=new JButton("关闭");
		b2=new JButton("重置");
		b1.setPreferredSize(new Dimension(150,30));
		b2.setPreferredSize(new Dimension(150,30));
		b1.addActionListener(new ActionListener(){
		@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
			
		});
		list1=new JList<String>(s);
		list1.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					if(list1.getSelectedIndex()==0){
						low();
					}
					else if(list1.getSelectedIndex()==1){
						mid();
					}
					else{
						hig();
					}
				}
			}
			
		});
		setSelection();
		list1.setSelectedIndex(0);
		list1.setVisibleRowCount(3);
		list1.setBackground(Color.white);
		list1.setPreferredSize(new Dimension(160,120));
		p0.add(list1);
		p0.setPreferredSize(new Dimension(170,120));
		
		Border ld=BorderFactory.createTitledBorder("最佳时间");
		p1.setBorder(ld);
		l7.setPreferredSize(new Dimension(200,20));
		p1.add(l7);
		p1.setPreferredSize(new Dimension(220,120));
		
		p2.setLayout(new GridLayout(6,1));
		p2.setPreferredSize(new Dimension(220,150));
		p2.add(l1);
		p2.add(l2);
		p2.add(l3);
		p2.add(l4);
		p2.add(l5);
		p2.add(l6);
		
		FlowLayout flow=new FlowLayout();
		flow.setAlignment(FlowLayout.RIGHT);
		p3.setLayout(flow);
		p3.add(p0);
		p3.add(p1);
		p3.add(p2);
		
	    p4.setLayout(flow);
	    p4.add(b1);
	    p4.add(b2);
	    add(p3,BorderLayout.CENTER);
	    add(p4,BorderLayout.SOUTH);
	    setSize(650,250);
	    setResizable(false);
	    setVisible(false);
	}
	protected void  reset() {
		String []choice={" 是 "," 否  "};
		 int x=JOptionPane.showOptionDialog(null,
				    "你想将所有统计信息重置为零吗？", null,JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE, null, choice,choice[0]);
		 if(x==0){
			 mf.lowWinNumber=0;
			 mf.lowPlayAll=0;
			 mf.lowWinRate="";
			 mf.lowLosingDreak=0;
			 mf.lowWinDreak=0;
			 
			 mf.midWinNumber=0;
			 mf.midPlayAll=0;
			 mf.midWinRate="";
			 mf.midLosingDreak=0;
			 mf.midWinDreak=0;
				
			 mf.higWinNumber=0;
			 mf.higPlayAll=0;
			 mf.higWinRate="";
			 mf.higLosingDreak=0;
			 mf.higWinDreak=0; 
			try {
				BufferedWriter f=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gd/grade.txt")));
				String s=""+mf.lowWinNumber;
				f.write(s);
				f.newLine();
				s=""+mf.lowPlayAll;
				f.write(s);
				f.newLine();
				s=""+mf.lowLosingDreak;
				f.write(s);
				f.newLine();
				s=""+mf.lowWinDreak;
				f.write(s);
				f.newLine();
				s=""+mf.midWinNumber;
				f.write(s);
				f.newLine();
				s=""+mf.midPlayAll;
				f.write(s);
				f.newLine();
				s=""+mf.midLosingDreak;
				f.write(s);
				f.newLine();
				s=""+mf.midWinDreak;
				f.write(s);
				f.newLine();
				s=""+mf.higWinNumber;
				f.write(s);
				f.newLine();
				s=""+mf.higPlayAll;
				f.write(s);
				f.newLine();
				s=""+mf.higLosingDreak;
				f.write(s);
				f.newLine();
				s=""+mf.higWinDreak;
				f.write(s);
				f.close();
				setSelection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		 }
		 if(x==1)return;
		 
	}
	public void low(){
		l2.setText("已胜游戏："+mf.lowWinNumber);
		l1.setText("已玩游戏："+mf.lowPlayAll);
		l5.setText("最多连败："+mf.lowLosingDreak);
		l4.setText("最多连胜："+mf.lowWinDreak);
		if(mf.lowPlayAll==0)l3.setText("获胜率：0%");
		else l3.setText("获胜率："+(100*mf.lowWinNumber/mf.lowPlayAll)+"%");
		l7.setText(mf.lowWinRate);
	}
	public void mid(){
		l2.setText("已胜游戏："+mf.midWinNumber);
		l1.setText("已玩游戏："+mf.midPlayAll);
		l5.setText("最多连败："+mf.midLosingDreak);
		l4.setText("最多连胜："+mf.midWinDreak);
		if(mf.midPlayAll==0)l3.setText("获胜率：0%");
		else l3.setText("获胜率："+(100*mf.midWinNumber/mf.midPlayAll)+"%");
		l7.setText(mf.midWinRate);
	}
	public void hig(){
		l2.setText("已胜游戏："+mf.higWinNumber);
		l1.setText("已玩游戏："+mf.higPlayAll);
		l5.setText("最多连败："+mf.higLosingDreak);
		l4.setText("最多连胜："+mf.higWinDreak);
		if(mf.higPlayAll==0)l3.setText("获胜率：0%");
		else l3.setText("获胜率："+(100*mf.higWinNumber/mf.higPlayAll)+"%");
		l7.setText(mf.higWinRate);
	}
	public void setSelection(){
		if(mf.WIDTH==30&&mf.HEIGHT==16){
			setListIndex2();
			hig();
		}
		else if(mf.WIDTH==16&&mf.HEIGHT==16){
			setListIndex1();
			mid();
		}
		else{
			setListIndex0();
			low();
		}
	}
	public void setListIndex0(){
		list1.setSelectedIndex(0);
	}
	public void setListIndex1(){
		list1.setSelectedIndex(1);
	}
	public void setListIndex2(){
		list1.setSelectedIndex(2);
	}
}
// 这是一个实现设置游戏的类
class Option extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cp = 1;
	JPanel jk = new JPanel();
	private JRadioButton diflow = new JRadioButton("<html>初级<br>10个雷<br>9X9网格</html>",true);
	private JRadioButton difmid = new JRadioButton("<html>中级<br>40个雷<br>16X16网格</html>");
	private JRadioButton difhigh = new JRadioButton("<html>高级<br>99个雷<br>16X30网格</html>");
	private JRadioButton difself= new JRadioButton("自定义");
	private ButtonGroup bg = new ButtonGroup();
	private JLabel labelhigh=new JLabel("高度(9-24):");
	private JLabel labelwidth=new JLabel("宽度(9-30):");
	private JLabel labelnumber=new JLabel("雷数(10-668):");
	private JTextField hightext = new JTextField(5);
	private JTextField widthtext = new JTextField(5);
	private JTextField numbertext = new JTextField(5);
	private JPanel jp = new JPanel();
	JLabel jb = new JLabel();
	private JButton sureButton = new JButton("确认");
	private JButton cancelButton = new JButton("取消");
	Cover c;
	public Option(final MyFrame f)
	{
		hightext.setEnabled(false);
		widthtext.setEnabled(false);
		numbertext.setEnabled(false);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		bg.add(diflow);
		diflow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				cp = 1;
				hightext.setEnabled(false);
				widthtext.setEnabled(false);
				numbertext.setEnabled(false);
				
			}
		});
		bg.add(difmid);
		difmid.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cp = 2;
				hightext.setEnabled(false);
				widthtext.setEnabled(false);
				numbertext.setEnabled(false);
			}
		});
		bg.add(difhigh);
		difhigh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cp = 3;
				hightext.setEnabled(false);
				widthtext.setEnabled(false);
				numbertext.setEnabled(false);
			}
		});
		bg.add(difself);
		difself.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cp = 4;
				hightext.setEnabled(true);
				widthtext.setEnabled(true);
				numbertext.setEnabled(true);
			}
		});
		diflow.setSize(125,70);
		difmid.setSize(125,70);
		difhigh.setSize(125,70);
		difself.setSize(130,70);
		JPanel p=new JPanel();
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		labelwidth.setSize(115,30);
		widthtext.setSize(70,30);
		labelhigh.setSize(115,30);
		hightext.setSize(70,30);
		labelhigh.setSize(115,30);
		numbertext.setSize(70,30);
		jb.setSize(120,30);
		p1.setSize(220,200);
	    p1.setLayout(new GridLayout(3,1));
		p1.add(diflow);
		p1.add(difmid);
		p1.add(difhigh);
		p2.setSize(250,200);
		 GridLayout grid=new GridLayout(5,2);
		 grid.setVgap(5);
	    p2.setLayout(grid);
		p2.add(difself);
		p2.add(jb);
		p2.add(labelwidth);
		p2.add(widthtext);
		p2.add(labelhigh);
		p2.add(hightext);
		p2.add(labelnumber);
		p2.add(numbertext);
		sureButton.setSize(100, 80);
		sureButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(cp == 4)
				{
					if(numbertext.getText().length() < 1 || hightext.getText().length() < 1|| widthtext.getText().length() < 1)
						return ;
					String s = numbertext.getText();
					for(int i = 0; i < s.length(); i++)
						if(s.charAt(i) < '0' || s.charAt(i) > '9')
						{
							JOptionPane.showMessageDialog(null, "雷数为非数字", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					s = widthtext.getText();
					for(int i = 0; i < s.length(); i++)
						if(s.charAt(i) < '0' || s.charAt(i) > '9')
						{
							JOptionPane.showMessageDialog(null, "宽度为非数字", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					s = hightext.getText();
					for(int i = 0; i < s.length(); i++)
						if(s.charAt(i) < '0' || s.charAt(i) > '9')
						{
							JOptionPane.showMessageDialog(null, "高度为非数字", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					int a,b,tp = Integer.parseInt(widthtext.getText(), 10);
					if(tp > 30 || tp < 9)
					{
						JOptionPane.showMessageDialog(null, "宽度值超出范围", "错误", JOptionPane.ERROR_MESSAGE);
							return;
					}
					a = tp;
					tp = Integer.parseInt(hightext.getText(), 10);
					if(tp <9 || tp > 24)
					{
						JOptionPane.showMessageDialog(null, "高度值超出范围", "错误", JOptionPane.ERROR_MESSAGE);
							return;
					}
					b = tp;
					tp = Integer.parseInt(numbertext.getText(), 10);
					if(tp < 10 || tp > a * b - 52)
					{
						JOptionPane.showMessageDialog(null, "雷数值超出范围，请保证雷数值在" + 10 + "-" + (a * b - 52)+"之内", "错误", JOptionPane.ERROR_MESSAGE);
							return;
					}
					f.number = Integer.parseInt(numbertext.getText(), 10);
					f.HEIGHT = Integer.parseInt(hightext.getText(), 10);
					f.WIDTH = Integer.parseInt(widthtext.getText(), 10);
				}
				else if(cp == 1)
				{
					f.number = 10;
					f.HEIGHT = 9;
					f.WIDTH = 9;
				}
				else if(cp == 2)
				{
					f.number = 40;
					f.HEIGHT = 16;
					f.WIDTH = 16;
				}
				else 
				{
					f.number = 99;
					f.HEIGHT = 16;
					f.WIDTH = 30;
				}
				widthtext.setText("");
				hightext.setText("");
				numbertext.setText("");
				f.end = false;f.isFirst = true;
				f.init();
				f.getCover().flag = true;
				f.getCover().init(f);
				f.getTimer().init(f);
				setVisible(false);
			}
		});
		cancelButton.setSize(100, 80);
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				widthtext.setText("");
				hightext.setText("");
				numbertext.setText("");
				setVisible(false);
			}
		});
		p3.setLayout(new GridLayout(1,3));
		JLabel label1=new JLabel("");
		p3.add(label1);
		p3.add(sureButton);
		p3.add(cancelButton);
		p.setLayout(new GridLayout(1,2));
		//设置面板边界
		Border ld= BorderFactory.createTitledBorder("难度");
		p.setBorder(ld);
		p.add(p1);
		p.add(p2);
		add(p,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		setSize(400,280);
		this.setResizable(false);
	}
}
class Register extends JDialog
{
	JLabel nameField = new JLabel("帐户名：");
	JLabel passwordField = new JLabel("密码：");
	
	JTextField nameText = new JTextField(20);
	JTextField passwordText = new JTextField(20);
	
	JButton sure = new JButton("确认");
	JButton cancel = new JButton("取消");
	
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();
	
	public Register()
	{
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		this.setTitle("注册");
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(400,280);
		jp1.setSize(200,200);
		jp1.setLocation(8,10);
		nameText.setSize(180,20);
		nameText.setLocation(105,50);
		passwordText.setSize(180,20);
		passwordText.setLocation(105,150);
		jp3.setSize(300,30);
		jp3.setLocation(100,220);
		jp1.setLayout(new GridLayout(2,1));
		jp3.setLayout(new GridLayout(1,2));
		
		jp1.add(nameField);
		jp1.add(passwordField);
		this.add(nameText);
		this.add(passwordText);
		jp3.add(sure);
		sure.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		jp3.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		this.add(jp1);
		this.add(jp3);
	}
}
class Login extends JDialog
{
	JButton log = new JButton("登录");
	JButton register = new JButton("注册新账户");
	JButton cancel = new JButton("取消");
	
	JLabel nameField = new JLabel("帐户名：");
	JLabel passwordField = new JLabel("密码：");
	
	JTextField nameText = new JTextField(20);
	JTextField passwordText = new JTextField(20);
	
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();
	
	Register r = new Register();
	
	public Login()
	{
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		this.setTitle("登录");
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(400,280);
		jp1.setSize(200,200);
		jp1.setLocation(8,10);
		nameText.setSize(180,20);
		nameText.setLocation(105,50);
		passwordText.setSize(180,20);
		passwordText.setLocation(105,150);
		jp3.setSize(400,30);
		jp3.setLocation(0,220);
		jp1.setLayout(new GridLayout(2,1));
		jp3.setLayout(new GridLayout(1,3));
		
		jp1.add(nameField);
		jp1.add(passwordField);
		this.add(nameText);
		this.add(passwordText);
		jp3.add(log);
		log.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		jp3.add(register);
		register.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				r.setVisible(true);
				setVisible(false);
			}
		});
		jp3.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		this.add(jp1);
		this.add(jp3);
	}
}
class gameEnd extends JDialog
{
	JButton exit = new JButton("退出");
	JButton restart = new JButton("重新开始这局游戏");
	JButton replay = new JButton("再玩一局");
	JLabel lb = new JLabel();
	JPanel jp = new JPanel();
	Cover cover;
	private boolean isWin;
	public gameEnd(MyFrame mf)
	{
		this.setLayout(new BorderLayout());
		this.setSize(350,150);
		this.setResizable(false);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		init(mf);
		this.setVisible(true);
	}
	public void setIsWin(boolean isWin)
	{
		this.isWin = isWin;
	}
	public void init(MyFrame mf)
	{
		this.add(lb,BorderLayout.CENTER);
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		jp.add(replay);
		replay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mf.isFirst = true;
				mf.end = false;
				mf.getCover().init(mf);
				mf.getTimer().init(mf);
				setVisible(false);
			}
		});
		jp.add(exit);
		if(mf.isWin == false)
		{
			lb.setText("                  你输了");
			jp.add(restart);
			restart.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					mf.isFirst = false;
					mf.end = false;
					mf.getCover().init(mf);
					mf.getTimer().init(mf);
					setVisible(false);
				}
			});
		}
		else if(mf.isWin == true)
			lb.setText("                你赢了");
		this.add(jp,BorderLayout.SOUTH);
		// int lowWinNumber,lowPlayAll,lowLosingDreak,lowWinDreak;
		// int midWinNumber,midPlayAll,midLosingDreak,midWinDreak;
		// int higWinNumber,higPlayAll,higLosingDreak,higWinDreak;
		// String lowWinRate,midWinRate,higWinRate;
		if(mf.WIDTH == 9 && mf.HEIGHT == 9)
		{
			mf.lowPlayAll++;
			if(mf.isWin == true)
				mf.lowWinNumber++;
		}
		else if(mf.WIDTH == 16 && mf.HEIGHT == 16)
		{
			mf.midPlayAll++;
			if(mf.isWin == true)
				mf.midWinNumber++;
		}
		else if(mf.WIDTH == 30 && mf.HEIGHT == 16)
		{
			mf.higPlayAll++;
			if(mf.isWin == true)
				mf.higWinNumber++;
		}
		try {
			BufferedWriter f=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("gd/grade.txt")));
			String s=""+mf.lowWinNumber;
			f.write(s);
			f.newLine();
			s=""+mf.lowPlayAll;
			f.write(s);
			f.newLine();
			s=""+mf.lowLosingDreak;
			f.write(s);
			f.newLine();
			s=""+mf.lowWinDreak;
			f.write(s);
			f.newLine();
			s=""+mf.midWinNumber;
			f.write(s);
			f.newLine();
			s=""+mf.midPlayAll;
			f.write(s);
			f.newLine();
			s=""+mf.midLosingDreak;
			f.write(s);
			f.newLine();
			s=""+mf.midWinDreak;
			f.write(s);
			f.newLine();
			s=""+mf.higWinNumber;
			f.write(s);
			f.newLine();
			s=""+mf.higPlayAll;
			f.write(s);
			f.newLine();
			s=""+mf.higLosingDreak;
			f.write(s);
			f.newLine();
			s=""+mf.higWinDreak;
			f.write(s);
			f.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
// 这是一个游戏界面的类
class Cover extends JPanel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyFrame f;
	// 定义雷的个数，长多少格，宽多少格
	private int number,WIDTH,HEIGHT;
	// 定义一个标记游戏表面状态的二维数组
	private int[][] tablemap = new int[100][100];
	// 定义一个存储面板内的状态的二维数组
	private int[][] map = new int[100][100];
	int[][] temp = new int[100][100];
	int[][] tpmap = new int[100][100];
	// 当前选中点的坐标
	private int selectedX = -1;
	private int selectedY = -1;
	private int time = 0,w,pl = 2;
	// 爆炸起始点
	private int xstart,ystart;
	// 定义关于每个砖块的宽和长
	private final int ratex = 37,ratey = 37;
	// 定义一个游戏是否结束的标志
	boolean end = false,isWin = false;
	private int tempNumber,trueMine = 0;
	// 定义一个是否是第一次点击的标志
	boolean isFirst = true,isPressed = false,flag = false,bothPressed = false;
	public int[] dir = {1,0,-1,0,1,1,-1,-1,
						0,1,0,-1,1,-1,1,-1};
	private int SLEEPTIME = 50;
	// 定义一个队列包含的是map里面的坐标以及值，用在BFS搜索
	Queue<local> queue = new LinkedList<local>(); 
	Queue<local> queue2 = new LinkedList<local>(); 
	Thread th;
	// 游戏画面中的图片文件
	File file[] = new File[]{
			new File("image/0.jpg"),new File("image/1.jpg"),new File("image/2.jpg"),new File("image/3.jpg"),new File("image/4.jpg"),
			new File("image/5.jpg"),new File("image/6.jpg"),new File("image/7.jpg"),new File("image/8.jpg"),new File("image/9.jpg"),
			new File("image/10.jpg"),new File("image/11.jpg"),new File("image/12.jpg"),new File("image/13.jpg"),new File("image/14.jpg"),
			new File("image/15.jpg"),new File("image/16.jpg"),new File("image/17.jpg"),new File("image/18.jpg"),new File("image/19.jpg"),
			new File("image/20.jpg"),new File("image/21.png"),new File("image/22.png"),new File("image/background.jpg")
	};
	// 定义一个包含所有图片的链表~
	ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
	private int nextr;
	// 把所有的图片丢进链表
	public void poll()
	{
		try
		{
			for(int i = 0; i < 24; i++)
			{
				img.add(ImageIO.read(file[i]));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	// 初始化游戏界面的基本参数
	public void init(MyFrame f)
	{
		this.f = f;
		this.isFirst = f.isFirst;
		this.end = f.end;
		this.isWin = false;
		this.number = f.number;
		tempNumber = number;
		pl = 2;
		selectedX = -1;
		selectedY = -1;
		this.WIDTH = f.WIDTH;
		this.HEIGHT = f.HEIGHT;
		this.trueMine = 0;
		this.setBounds(0,0,ratex * WIDTH,ratey * HEIGHT);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		isPressed = false;
		bothPressed = false;
		SLEEPTIME = 50;
		xstart = -1;
		ystart = -1;
		for (int i = 0 ; i < WIDTH ; i++)
		{
			for ( int j = 0 ; j < HEIGHT ; j++)
			{
				if(isFirst == true)
					map[i][j] = 0;
				temp[i][j] = 0;
				tpmap[i][j] = 0;
			}
		}
		for(int i = 0; i < WIDTH; i++)
			for(int j = 0; j < HEIGHT; j++)
				tablemap[i][j] = 9;
		th = new Thread(this);
		th.start();
	}
	public Cover(MyFrame f)
	{
		poll();
		init(f);
		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(end == false && e.getModifiersEx()==(e.BUTTON3_DOWN_MASK + e.BUTTON1_DOWN_MASK))  
				{
					selectedX = (int)(e.getX() / ratex);
					selectedY = (int)(e.getY() / ratey);
					bothPressed = true;
					isPressed = false;
					return ;
				}
				if(end == false && e.getButton() == MouseEvent.BUTTON1)
				{
					selectedX = (int)(e.getX() / ratex);
					selectedY = (int)(e.getY() / ratey);
					if(tablemap[selectedX][selectedY] == 13)
						return ;
					isPressed = true;
				}
				else if(end == true)
				{
					SLEEPTIME = 0;
				}
			}
			public void mouseReleased(MouseEvent e)
			{
				if(bothPressed == true)
					pl--;
				if(end == false && bothPressed == true)
				{
					int xPos = (int)(e.getX()  / ratex);
					int yPos = (int)(e.getY() / ratey);
					if(tablemap[xPos][yPos] == map[xPos][yPos])
					{
						int q = map[xPos][yPos],b = 0;
						for(int j = 0; j < 8; j++)
						{
							int sx = xPos + dir[j],sy = yPos + dir[j + 8];
							if( sx >= 0 && sx < WIDTH && sy >= 0 && sy < HEIGHT)
								if(tablemap[sx][sy] == 13)
									b++;
						}
						if(b == map[xPos][yPos])
						{
							for(int j = 0; j < 8; j++)
							{
								int sx = xPos + dir[j],sy = yPos + dir[j + 8];
								if( sx >= 0 && sx < WIDTH && sy >= 0 && sy < HEIGHT)
								{
									if(tablemap[sx][sy] == 9)
									{
										tablemap[sx][sy] = map[sx][sy];
									}
									if(tablemap[sx][sy] == 11)
									{
										xstart = sx;
										ystart = sy;
										end = true;
										isWin = false;
										f.getTimer().stop();
									}
									if(tablemap[sx][sy] == 0)
									{
										int[][] temp = new int[100][100];
										queue.clear();
										queue.offer(new local(xPos,yPos,0));
										temp[xPos][yPos] = 1;
										local lc ;
										for(int m = 0; m < 8; m++)
										{
											int cx = xPos + dir[m],cy = yPos + dir[m + 8];
											if( cx >= 0 && cx < WIDTH && cy >= 0 && cy < HEIGHT
												&& map[cx][cy] >= 1 && map[cx][cy] < 9)
											{
												tablemap[cx][cy] = map[cx][cy];
											}
										}
										while((lc = queue.poll()) != null)
										{
											for(int n = 0; n < 4; n++)
											{
												int wx = lc.x + dir[n],wy = lc.y + dir[n + 8];
												if( wx >= 0 && wx < WIDTH && wy >= 0 && wy < HEIGHT
													&& map[wx][wy] == 0 && temp[wx][wy] != 1)
												{
													tablemap[wx][wy] = 0;
													temp[wx][wy] = 1;
													queue.offer(new local(wx,wy,0));
													for(int l = 0; l < 8; l++)
													{
														int cx = wx + dir[l],cy = wy + dir[l + 8];
														if( cx >= 0 && cx < WIDTH && cy >= 0 && cy < HEIGHT
															&& map[cx][cy] >= 1 && map[cx][cy] < 9 && tablemap[cx][cy] != 13)
														{
															tablemap[cx][cy] = map[cx][cy];
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				else if(end == false && bothPressed == false)
				{
					isPressed = false;
					int xPos = (int)(e.getX()  / ratex);
					int yPos = (int)(e.getY() / ratey);
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(tablemap[xPos][yPos] == 13)
							return ;
						tablemap[xPos][yPos] = map[xPos][yPos];
						if(isFirst == true)
						{
							map = new Map(xPos,yPos,number,WIDTH,HEIGHT).getMap();
							isFirst = false;
						}
						
						if(tablemap[xPos][yPos] == 11)
						{
							xstart = xPos;
							ystart = yPos;
							end = true;
							isWin = false;
							f.getTimer().stop();
						}
					}
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(tablemap[xPos][yPos] == 9)
						{
							tablemap[xPos][yPos] = 13;
							if(map[xPos][yPos] == 11)
								trueMine++;
							if(trueMine == number)
							{
								isWin = true;
								flag = true;
								f.getGameend(isWin);
							}
							tempNumber--;
						}
						else if(tablemap[xPos][yPos] == 13)
						{
							tablemap[xPos][yPos] = 14;
							if(map[xPos][yPos] == 11)
								trueMine--;
						}
						else if(tablemap[xPos][yPos] == 14)
						{
							tempNumber++;
							tablemap[xPos][yPos] = 9;
							if(map[xPos][yPos] == 11)
								trueMine--;
						}
						f.getTimer().setNumber(tempNumber);
					}
					if(tablemap[xPos][yPos] == 0)
					{
						int[][] temp = new int[100][100];
						queue.clear();
						queue.offer(new local(xPos,yPos,0));
						temp[xPos][yPos] = 1;
						local lc ;
						for(int j = 0; j < 8; j++)
						{
							int sx = xPos + dir[j],sy = yPos + dir[j + 8];
							if( sx >= 0 && sx < WIDTH && sy >= 0 && sy < HEIGHT
								&& map[sx][sy] >= 1 && map[sx][sy] < 9)
							{
								tablemap[sx][sy] = map[sx][sy];
							}
						}
						while((lc = queue.poll()) != null)
						{
							for(int i = 0; i < 4; i++)
							{
								int wx = lc.x + dir[i],wy = lc.y + dir[i + 8];
								if( wx >= 0 && wx < WIDTH && wy >= 0 && wy < HEIGHT
									&& map[wx][wy] == 0 && temp[wx][wy] != 1)
								{
									tablemap[wx][wy] = 0;
									temp[wx][wy] = 1;
									queue.offer(new local(wx,wy,0));
									for(int j = 0; j < 8; j++)
									{
										int sx = wx + dir[j],sy = wy + dir[j + 8];
										if( sx >= 0 && sx < WIDTH && sy >= 0 && sy < HEIGHT
											&& map[sx][sy] >= 1 && map[sx][sy] < 9 && tablemap[sx][sy] != 13)
										{
											tablemap[sx][sy] = map[sx][sy];
										}
									}
								}
							}
						}
					}
				}
				if(pl == 0)
				{
					bothPressed = false;
					pl = 2;
				}
			}
			// 当鼠标退出面板后，复位选中点坐标
			public void mouseExited(MouseEvent e)
			{
				if(end == false)
				{
					isPressed = false;
					selectedX = -1;
					selectedY = -1;
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				if(end == false)
				{
					selectedX = (int)(e.getX() / ratex);
					selectedY = (int)(e.getY() / ratey);
					if(bothPressed == false)
						isPressed = true;
					else if(bothPressed == true)
						bothPressed = true;
				}
			}
			// 当鼠标移动时，改变选中点的坐标
			public void mouseMoved(MouseEvent e)
			{
				if(end == false)
				{
					selectedX = (int)(e.getX() / ratex);
					selectedY = (int)(e.getY() / ratey);
				}
			}
		});
	}
	class local
	{
		public int x,y,value;
		public local(int x,int y,int value)
		{
			this.x = x;
			this.y = y;
			this.value = value;
		}
	}
	public int getTempNumber()
	{
		return tempNumber;
	}
	public void run()
	{
		while(end == false)
		{
			if(flag == true)
			{
				flag = false;
				return ;
			}
			try
			{
				Thread.sleep(SLEEPTIME);
				repaint();
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		if(end == true)
		{
			local lc = new local(0,0,0);
			try
			{
				int r = 0;
				nextr = 0;
				queue.clear();
				queue2.clear();
				queue.offer(new local(xstart,ystart,0));
				temp[xstart][ystart] = 1;
				for(int i = 0; i < WIDTH; i++)
				{
					for(int j = 0; j < HEIGHT; j++)
					{
						
						if(map[i][j] == 11 && tablemap[i][j] == 13)
						{
							tablemap[i][j] = 20;
						}
						else if(map[i][j] == 11 && tablemap[i][j] == 14)
						{
							tablemap[i][j] = 22;
						}
						else if(map[i][j] == 11)
						{
							tablemap[i][j] = map[i][j];
						}
						tpmap[i][j] = tablemap[i][j];
					}
				}
				repaint();
				
				local lc2;
				w = 0;
				lc = queue.peek();
				while(true)
				{
					r = Math.max(Math.abs(lc.x - xstart),Math.abs(lc.y - ystart));
					if(r == w)
					{
						while((lc2 = queue.poll()) != null)
						{
							if(map[lc2.x][lc2.y] == 11)
							{
								tablemap[lc2.x][lc2.y] = 17;
							}
							queue2.offer(lc2);
						}
						repaint();
						Thread.sleep(SLEEPTIME);
						for(int i = 0; i < WIDTH; i++)
						{
							for(int j = 0; j < HEIGHT; j++)
							{
								if(tablemap[i][j] == 17)
								{
									tablemap[i][j] = 18;
								}
							}
						}
						repaint();
						Thread.sleep(SLEEPTIME);
						for(int i = 0; i < WIDTH; i++)
						{
							for(int j = 0; j < HEIGHT; j++)
							{
								
								if(tpmap[i][j] == 20 && tablemap[i][j] == 18)
								{
									tablemap[i][j] = 16;
								}
								else if(tpmap[i][j] == 22 && tablemap[i][j] == 18)
								{
									tablemap[i][j] = 21;
								}
								else if(tablemap[i][j] == 18)
								{
									tablemap[i][j] = 12;
								}
								if(i == xstart && j == ystart)
								{
									tablemap[i][j] = 19;
								}
							}
						}
						repaint();
						Thread.sleep(SLEEPTIME);
						w++;
					}
					while((lc = queue2.poll()) != null)
					{
						for(int i = 0; i < 8; i++)
						{
							int wx = lc.x + dir[i],wy = lc.y + dir[i + 8];
							if( wx >= 0 && wx < WIDTH && wy >= 0 && wy < HEIGHT 
								&& temp[wx][wy] != 1)
							{
								temp[wx][wy] = 1;
								queue.offer(new local(wx,wy,0));
							}
						}
					}
					lc = queue.peek();
					if(lc == null)
					{
						break;
					}
				}
			f.getGameend(isWin);
			SLEEPTIME = 50;
			}
			catch (InterruptedException e) 
			{
				
				e.printStackTrace();
			}
		}
	}
	// 重写JPanel的paint方法，实现绘画
	public void paintComponent(Graphics g)
	{
		// 遍历数组，绘制画面。
		for (int i = 0 ; i < WIDTH ; i++)
		{
			for ( int j = 0 ; j < HEIGHT ; j++)
			{
				g.drawImage(img.get(tablemap[i][j]) , i * ratex
					, j * ratey,null);
				if(tablemap[i][j] == 9 && i == selectedX && j == selectedY)
				{
					
					if(isPressed == true)
					{
						g.drawImage(img.get(0) , i * ratex
					, j * ratey,null);
					}
					else
					{
						g.drawImage(img.get(10) , i * ratex
					, j * ratey,null);
					}
				}
			}
		}
		if(bothPressed == true)
		{
			if(tablemap[selectedX][selectedY] == 9)
				g.drawImage(img.get(0),selectedX * ratex,selectedY * ratey,null);
			for(int j = 0; j < 8; j++)
			{
				int sx = selectedX + dir[j],sy = selectedY + dir[j + 8];
				if( sx >= 0 && sx < WIDTH && sy >= 0 && sy < HEIGHT && tablemap[sx][sy] == 9)
				{
					g.drawImage(img.get(0) , sx * ratex
					, sy * ratey,null);
				}
			}
		}
	}
}
// 这是一个底部计时器与记录剩余雷数的类
class Timer extends JLabel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int WIDTH,HEIGHT,time,number;
	BufferedImage img;
	Graphics g;
	private boolean p = false;
	Cover cover;
	public Timer(MyFrame f)
	{
		this.setLayout(null);
		init(f);
		new Thread(this).start();
	}
	public void init(MyFrame f)
	{
		try
		{
			img = ImageIO.read(new File("image/background.jpg"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		this.WIDTH = f.WIDTH * 37;
		this.HEIGHT = 200;
		this.number = f.number;
		time = 0;
		p = false;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public void stop()
	{
		p = true;
	}
	public void run()
	{
		while(true)
		{
			try
			{
				repaint();
				Thread.sleep(1000);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			if(p == false)
			{
				time++;
			}
		}
	}
	public void paint(Graphics g)
	{
		g.drawImage(img,0,0,WIDTH,HEIGHT,null);
		g.setColor(Color.red);
		g.setFont(new Font("Times" , Font.BOLD, 30));
		g.drawString("时间:" + String.valueOf(time) + "        雷数" + String.valueOf(number),50,80);
	}
}

// 这是主窗口类也是一个数据总类，所有的数据基本存储在这里，更新与读取从这里进行
public class MyFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar mb = new JMenuBar();
	JMenu game = new JMenu("游戏");
	JMenuItem newGameItem = new JMenuItem("新游戏");
	JMenuItem messageItem = new JMenuItem("统计消息");
	JMenuItem optionItem = new JMenuItem("选项");
	JMenuItem loginItem = new JMenuItem("登录/注册");
	JMenuItem exitItem = new JMenuItem("退出");
	JLabel gameUI = new JLabel();
	JLabel timeUI = new JLabel();
	MyAdapter adapter = new MyAdapter();
	BufferedImage img;
	Message message;
	Option option;
	Cover cover;
	Timer timer;
	Login login;
	gameEnd gameend;
	// 数据区
	//主要是曾经的记录，初级，中级，高级完成的各种数据
	int lowWinNumber,lowPlayAll,lowLosingDreak,lowWinDreak;
	int midWinNumber,midPlayAll,midLosingDreak,midWinDreak;
	int higWinNumber,higPlayAll,higLosingDreak,higWinDreak;
	String lowWinRate,midWinRate,higWinRate;
	//结束
	// 定义窗口的宽和长
	public int TABLE_WIDTH = 700,TABLE_HEIGHT = 400;
	// 定义雷的个数，长多少格，宽多少格
	public int number = 40,WIDTH = 16,HEIGHT = 16;
	private int ratex = 37,ratey = 37;
	public boolean end = false,isFirst = true,isWin = false;
	// 初始化窗口界面
	public MyFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("扫雷");
		message = new Message(MyFrame.this);
		option = new Option(MyFrame.this);
		cover =  new Cover(MyFrame.this);
		timer = new Timer(MyFrame.this);
		login = new Login();
		newGameItem.addActionListener(adapter);
		messageItem.addActionListener(adapter);
		optionItem.addActionListener(adapter);
		exitItem.addActionListener(adapter);
		loginItem.addActionListener(adapter);
		game.add(newGameItem);
		game.add(messageItem);
		game.add(loginItem);
		game.add(optionItem);
		game.add(exitItem);
		mb.add(game);
		this.setJMenuBar(mb);
		this.setLayout(null);
		gameUI.add(cover);
		this.add(gameUI);
		this.add(timer);
		this.setVisible(true);
		this.setResizable(false);
		read();
		init();
	}
	public Cover getCover()
	{
		return cover;
	}
	public Timer getTimer()
	{
		return timer;
	}
	public void getGameend(boolean isWin)
	{
		this.isWin = isWin;
		gameend = new gameEnd(MyFrame.this);
	}
	public void init()
	{
		gameUI.setBounds(0,0,ratex * WIDTH + 50,ratey * HEIGHT + 50);
		timer.setBounds(0,ratey * HEIGHT,ratex * WIDTH + 50,200);
		TABLE_HEIGHT = ratey * HEIGHT + 200;
		TABLE_WIDTH = ratex * WIDTH + 5;
		this.setBounds(100,100,TABLE_WIDTH,TABLE_HEIGHT);
	}
	class MyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == newGameItem)
			{
				init();
				cover.init(MyFrame.this);
			}
			else if(e.getSource() == messageItem)
			{
				message.setVisible(true);
			}
			else if(e.getSource() == optionItem)
			{
				option.setVisible(true);
			}
			else if(e.getSource() == loginItem)
			{
				login.setVisible(true);
			}
			else if(e.getSource() == exitItem)
			{
				System.exit(0);
			}
		}
	}
	public static void main(String[] args)
	{
		new MyFrame();
	}
	public void read(){//将初级、中级、高级难度的统计信息从文件读入程序
		String seprator  = File.separator;
		String filename = "grade.txt";
		String directory = "gd" + seprator;
		//  其中mydir1是在这个类Test00.java所在那个文件夹下
		//   也可以写成这样
		//  String directory = "mydir1/mydir2";      
		//  /(正斜杠) 在Windows与Linux下都可以当作路径符
		File f = new File(directory,filename);      
			 //到目前为止内存有了一个对象f，但是硬盘上还没有文件
		if(f.exists()) {      //如果已经存在f文件了
        System.out.println("文件名：" + f.getAbsolutePath());   //打印绝对路径
        System.out.println("文件大小：" + f.length());
      } else {
         f.getParentFile().mkdirs();      
            //getParentFile()返回File类型的父路径，mkdirs()创建所有的路径
         try {
            f.createNewFile();         //至此真正在硬盘上创建了myTest.txt文件。
         } catch(IOException ioe) {      
            ioe.printStackTrace();
         }
      }
		try
		{
			BufferedReader bfin=new BufferedReader(new InputStreamReader(new FileInputStream("gd/grade.txt")));
			String  s=bfin.readLine();
			if(s!=null)
			{
				lowWinNumber=Integer.parseInt(s);
				lowPlayAll=Integer.parseInt(bfin.readLine());
				lowLosingDreak=Integer.parseInt(bfin.readLine());
				lowWinDreak=Integer.parseInt(bfin.readLine());
				midWinNumber = Integer.parseInt(bfin.readLine());
				midPlayAll = Integer.parseInt(bfin.readLine());
				midLosingDreak = Integer.parseInt(bfin.readLine());
				midWinDreak=Integer.parseInt(bfin.readLine());
				higWinNumber = Integer.parseInt(bfin.readLine());
				higPlayAll = Integer.parseInt(bfin.readLine());
				higLosingDreak = Integer.parseInt(bfin.readLine());
				higWinDreak=Integer.parseInt(bfin.readLine());
			}
			bfin.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}