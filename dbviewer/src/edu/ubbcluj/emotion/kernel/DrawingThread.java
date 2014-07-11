package edu.ubbcluj.emotion.kernel;

import edu.ubbcluj.emotion.swing.Canvas;

public class DrawingThread extends Thread {
	
	private Canvas canvas;
	
	private volatile boolean stop = false;
	private volatile int millis = 70;
	
	public DrawingThread(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void run() {
		stop = false;
		while (!stop) {
			canvas.repaint();
			canvas.nextImage();
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void interrupt() {
		stop = true;
	}
	
	public void setSpeed(int millis) {
		this.millis = millis;
	}
}
