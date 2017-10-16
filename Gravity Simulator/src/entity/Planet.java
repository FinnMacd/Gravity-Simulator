package entity;

import java.awt.Color;
import java.awt.Graphics;

import main.GamePanel;
import physics.Physics;

public class Planet {
	
	private double mass;
	private int width;
	private Vector2f location;
	private Vector2f nextLocation;
	private Vector2f velocity;
	private Vector2f force;
	public boolean bounced = false;
	
	private Color color;
	
	public Planet(double mass, Vector2f location, Vector2f velocity, Color color){
		this.mass = mass;
		this.location = location;
		this.velocity = velocity;
		this.color = color;
		width = (int)(6 + Math.sqrt(mass) * 4);
	}
	
	public void update(){
		
		force = new Vector2f(0,0);
		
		for(Planet p:GamePanel.planetSystem.getPlanets()){
			
			if(p == this)continue;
			
			Vector2f r = Physics.getDistance(this, p);
			
			if(GamePanel.BOUNCE == true && r.getLength() < width + p.getWidth())continue;
			
			Vector2f pForce = Physics.getGForce(mass, p.getMass(), r);
			
			if((r.getX()/Math.abs(r.getX())) != (pForce.getX()/Math.abs(pForce.getX())))pForce.setX(pForce.getX()*-1);
			if((r.getY()/Math.abs(r.getY())) != (pForce.getY()/Math.abs(pForce.getY())))pForce.setY(pForce.getY()*-1);
			
			force = force.add(pForce);
			
		}
		
		Vector2f accel = force.div((float)mass);
		
		velocity.setX(velocity.getX() + accel.getX()*0.01f);
		velocity.setY(velocity.getY() + accel.getY()*0.01f);
		
		nextLocation = new Vector2f(location.getX() + velocity.getX()*0.01f,
									location.getY() + velocity.getY()*0.01f);
		
	}
	
	public void draw(Graphics g){
		
		g.setColor(color);
		g.fillOval((int)location.getX()-(width/2), (int)location.getY()-(width/2), width, width);
		
		g.setColor(Color.red);
		//g.drawLine((int)location.getX(), (int)location.getY(), (int)(location.getX() + velocity.getX()*50), (int)(location.getY() + velocity.getY()*50));
		
	}
	
	public void setLocation(){
		location = nextLocation;
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public Vector2f getLocation() {
		return location;
	}

	public void setLocation(Vector2f location) {
		this.location = location;
	}
	
	public Vector2f getMomentum(){
		return velocity.mul((float)mass);
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
