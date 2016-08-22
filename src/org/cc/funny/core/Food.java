package org.cc.funny.core;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cc.funny.gui.MyJFrame;

public class Food {

	private int x;
	private int y;
	
	private String path="org/cc/funny/resource/food.png";
	private BufferedImage image;
	
	private boolean overdue;
	
	private int power;
	
	private int speed;
	
	public Food(int x, int y,int speed) {
		this();
		this.x = x;
		this.y = y;
		this.speed=speed;
	}

	public int getY(){
		return y;
	}
	
	public boolean isOverdue(){
		return overdue;
	}
	
	private boolean crash(Hero hero){
		Rectangle r1=new Rectangle(hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
		Rectangle r2=new Rectangle(x,y,image.getWidth(),image.getHeight());
		return r1.intersects(r2);
	}
	
	public void fall(Hero hero){
		if(crash(hero)){
			hero.changeLife(1);
			hero.eatFoods++;
			overdue=true;
		}else{
			//just 自顾自的drop drop drop...
			power+=3;
			y=y+speed+power;
			//如果超过界限,那么减掉生命值
			if(y>MyJFrame.height){
				hero.changeLife(-100);
				overdue=true;
			}
		}
	}
	
	private Food() {
		try {
			image=ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMe(Graphics g){
		g.drawImage(image,x,y,null);
	}
	
}
