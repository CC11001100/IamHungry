package org.cc.funny.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cc.funny.gui.MyJFrame;

public class Hero {

	private String name;
	private int speed;
	private boolean alive;
	
	private int x;
	private int y;
	
	private String path="org/cc/funny/resource/hero.png";
	private BufferedImage image;
	
	private int life;
	
	public int eatFoods;
	
	public Hero(int x,int y){
		this();
		this.x=x;
		this.y=y;
	}
	
	public Hero() {
		try {
			image=ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			System.out.println(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		name="CC";
		speed=image.getWidth()/2;
		alive=true;
		life=101;
		
		x = MyJFrame.width/2-image.getWidth()/2;
		y = MyJFrame.height-image.getHeight()/2;
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	public void changeLife(int v){
		life+=v;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void left(){
		if(x>0) x-=speed;
	}
	
	public void right(){
		if(x<MyJFrame.width-image.getWidth()) x+=speed;
	}
	
	public void jump(){
		y-=image.getHeight()/100*80;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		y+=image.getHeight()/100*80;
	}
	
	public void showMe(Graphics g){
		
		g.drawImage(image,x,y,null);
		
		Font font=g.getFont();
		Color color=g.getColor();
		
		g.setFont(new Font("楷体",Font.PLAIN,25));
		g.setColor(Color.GREEN);
		g.drawString("饥饿值:"+life,MyJFrame.width-95-Integer.toString(life).length()*13,MyJFrame.height-10);
		
		g.setFont(font);
		g.setColor(color);
		
		if(life<=0){
			alive=false;
			MyJFrame.gameover=true;
		}
		
	}
	
}
