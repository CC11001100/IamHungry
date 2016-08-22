package org.cc.funny.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.cc.funny.gui.MyJFrame;

/**
 * 天上拉粑粑那位
 * @author cc
 *
 */
public class Boss {

	//BOSS的二维坐标
	private int x;
	private int y;
	
	//绘制资源位置
	private String path="org/cc/funny/resource/boss.png";
	private BufferedImage image;
	
	//BOSS运动方向
	private boolean toLeft;
	
	//已经丢出去的食物 o(╯□╰)o
	private List<Food> foods;
	
	private long dock;
	private int last;
	private long turnDock;
	
	private int speed;
	
	public int dropFoodsCount;
	
	public Boss() {
		try {
			image=ImageIO.read(Thread.currentThread().getContextClassLoader().getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		foods=new ArrayList<Food>();
		turnDock=System.currentTimeMillis();
		speed=30;
	}

	private void randomTurn(){
		if(System.currentTimeMillis()-turnDock>=2000){
			toLeft=new Random().nextBoolean();
			turnDock=System.currentTimeMillis();
		}
	}
	
	public void cruise(){
		if(toLeft){
			x-=speed;
			if(x<0) toLeft=!toLeft;		
		}else{
			x=x+speed;
			if(x>MyJFrame.width-image.getWidth()) toLeft=!toLeft;
		}
		randomTurn();
	}
	
	public void clearFoods(){
		foods.clear();
		dropFoodsCount=0;
	}

	public void showMe(Graphics g,Hero hero){
		cruise();
		g.drawImage(image,x,y,null);
		showFoods(g,hero);

		if(MyJFrame.gameover) return ;
		
		if(System.currentTimeMillis()-dock>last){
			dropFoodsCount++;
			dropFood();
			dock=System.currentTimeMillis();
			last=100+new Random().nextInt(1000);
		}
		
		System.out.println(foods.size());
		
	}
	
	private void showFoods(Graphics g,Hero hero){
		for(int i=0;i<foods.size();i++){
			Food e=foods.get(i);
			e.showMe(g);
			e.fall(hero);
			if(e.isOverdue()) foods.remove(e);
		}
	}
	
	public void dropFood(){
		foods.add(new Food(x+image.getWidth()/4,y+image.getHeight()/2,10+dropFoodsCount/2));
	}
	
}
