package org.cc.funny.gui;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		final JFrame jframe=new MyJFrame();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					jframe.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		
	}
	
}
