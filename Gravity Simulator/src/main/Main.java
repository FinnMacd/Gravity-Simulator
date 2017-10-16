package main;

import javax.swing.JFrame;

public class Main {
	
	private JFrame frame;
	
	private String title = "Physics Simulator";
	
	private GamePanel game;
	
	public static void main(String[] args) {
		
		new Main();

	}
	
	public Main(){
		
		frame = new JFrame(title);
		game = new GamePanel();
		
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		game.start();
		
	}
	
}
