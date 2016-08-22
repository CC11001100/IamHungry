package org.cc.funny.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cc.funny.core.Boss;
import org.cc.funny.core.Hero;

public class MyJFrame extends JFrame {

	public static int width=1024;
	public static int height=1000;
	
	private Hero hero;
	private Boss boss;
	
	private String backgroundImagePath="org/cc/funny/resource/background.jpg";
	private BufferedImage background;
	
	public static boolean gameover;
	private boolean gamestart;
	
	public MyJFrame() {
		
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		height=h-32;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		setLocation((w-width)/2,(h-height)/2-(h-height)*61/100/2);
		
		hero=new Hero();
		boss=new Boss();
		
		try {
			background=ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(backgroundImagePath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		setTitle("饿了么");
		setSize(width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if((gameover || !gamestart)&& KeyEvent.VK_SPACE!=e.getKeyCode()) return;
				if(!gamestart && KeyEvent.VK_SPACE==e.getKeyCode()){
					gamestart=true;
					return;
				}
				if(KeyEvent.VK_A==e.getKeyCode()){
					hero.left();
				}else if(KeyEvent.VK_D==e.getKeyCode()){
					hero.right();
				}else if(KeyEvent.VK_W==e.getKeyCode()){
					hero.jump();
				}else if(KeyEvent.VK_SPACE==e.getKeyCode()){
					gameover=false;
					hero=new Hero(hero.getX(),hero.getY());
//					boss=new Boss();
				}
			}
		});
		
		addWindowStateListener(new WindowStateListener() {
			
			@Override
			public void windowStateChanged(WindowEvent e) {
				Window w=e.getWindow();
				width=w.getWidth();
				height=w.getHeight();
			}
		});
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		BufferedImage buffer=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		Graphics g1=buffer.getGraphics();
		g1.drawImage(background,0,0,width,height,null);
		
		if(!gamestart){
			boss.clearFoods();
			Font font=g1.getFont();
			Color color=g1.getColor();
			g1.setFont(new Font("Courier New",Font.BOLD,100));
			g1.setColor(Color.RED);
			g1.drawString("I'm Hungry",width/2-4*100/2-100,height/2);
			g1.setFont(new Font("黑体",Font.PLAIN,20));
			g1.setColor(Color.BLACK);
			g1.drawString("  A ←      D →",width/2-4*100/2-30,height/2+30);
			g1.drawString("  按下空格键开始游戏",width/2-4*100/2-30,height/2+50);
			g1.drawRect(width/2-4*100/2-150,height/2-100,700,200);
			g1.drawRect(width/2-4*100/2-152,height/2-102,704,204);
			g1.setColor(color);
			g1.setFont(font);
		}
		
		if(gameover){
			Font font=g1.getFont();
			Color color=g1.getColor();
			g1.setFont(new Font("Courier New",Font.BOLD,100));
			g1.setColor(Color.RED);
			g1.drawString("GAME OVER",width/2-4*100/2-45,height/2);
			g1.setFont(new Font("黑体",Font.PLAIN,20));
			g1.setColor(Color.BLACK);
			g1.drawString("由于没能得到及时喂养,so,你是饿死的...  ╮(╯▽╰)╭",width/2-4*100/2-30,height/2+30);
			g1.drawString("本次共吃到粑粑"+hero.eatFoods+"坨"+(hero.eatFoods>100?"(此处应有饱嗝表情)":""),width/2-4*100/2-30,height/2+51);
			g1.drawString("表示不服？空格再来啊~",width/2-4*100/2-30,height/2+72);
			g1.drawRect(width/2-4*100/2-100,height/2-100,650,200);
			g1.drawRect(width/2-4*100/2-102,height/2-102,654,204);
			g1.setColor(color);
			g1.setFont(font);
			//所以这个时候应该讲BOSS的动作改为擦屁屁嘲讽效果就更好了...    :-D
			boss.clearFoods();
		}
		boss.showMe(g1,hero);
		hero.showMe(g1);
		
		
		g.drawImage(buffer,0,0,null);
	}
	
}
