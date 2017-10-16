package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import entity.PlanetSystem;

public class GamePanel extends Canvas implements Runnable, KeyListener{
	
	public static int WIDTH = 1280, HEIGHT = 960, scale = 1, UPS = 5000;
	
	public static boolean BOUNCE = true;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage image, background;
	private Graphics2D g;
	
	public static PlanetSystem planetSystem;
	
	public double vel = -1.0462;
	public double weight = 0;//-0.07999999999999925;
	
	public GamePanel(){
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addKeyListener(this);
		
	}
	
	public void start(){
		if(!running){
			init();
			thread = new Thread(this, "GameLoop");
			thread.start();
			running = true;
		}
	}
	
	public void stop(){
		
		if(running){
			running = false;
		}
		
	}
	
	public void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		Images.loadImages();
		background = Images.background;
		
		//planetSystem = PlanetSystem.createRandomSystem();
		planetSystem = PlanetSystem.createSingleSolarSystem();
		//planetSystem = PlanetSystem.createDoubleSolarSystem(vel, weight);
		//planetSystem = PlanetSystem.createBounceTestSystem();
		
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		//ups
		final double ns = 1000000000.0/UPS;//50
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while(running){
			
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			
			draw();
			drawToScreen();
			frames++;
			
			if(System.currentTimeMillis()-timer >= 1000){
				timer += 1000;
//				System.out.println(updates+" ups, "+frames+" fps");
				updates = frames = 0;
			}
			
		}
		
	}
	
	public void update(){
		
		planetSystem.update();
		
	}
	
	public void draw(){
		
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
		planetSystem.draw(g);
		
	}
	
	public void drawToScreen(){
		
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g2 = bs.getDrawGraphics();
		g2.drawImage(image, 0, 0, WIDTH*scale, HEIGHT*scale, null);
		g2.dispose();
		
		bs.show();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			//vel -= 0.0001;
//			weight += 0.01;
//			planetSystem = PlanetSystem.createDoubleSolarSystem(vel, weight);
//			System.out.println(weight);
			planetSystem = PlanetSystem.createRandomSystem();
			//planetSystem = PlanetSystem.createBounceTestSystem();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
