package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;
import physics.Physics;

public class PlanetSystem {
	
	private ArrayList<Planet> planets;
	
	public PlanetSystem(ArrayList<Planet> planets){
		this.planets = planets;
	}
	
	public static PlanetSystem createRandomSystem(){
		
		Random gen = new Random();
		
		long seed = gen.nextLong();
		
		gen.setSeed(seed);
		
		System.out.println(seed);
		
		ArrayList<Planet> planets = new ArrayList<Planet>();
		
		Vector2f momentum = new Vector2f(0,0);
		
		for(int i = 0; i < 2 + gen.nextInt(30); i++){
			
			Vector2f vel = new Vector2f((float)(gen.nextDouble()*2-1),(float)(gen.nextDouble()*2-1));
			
			float mass = 0.1f + (gen.nextInt(100)/10)*2;
			
			momentum = momentum.add(vel.mul(mass));
			
			planets.add(new Planet(mass,new Vector2f((float)(400 + gen.nextInt(481)),(float)(300 + gen.nextInt(361))),vel,new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256))));
			//planets.add(new Planet(1,new Vector2f((float)(220 + gen.nextInt(201)),(float)(40 + gen.nextInt(201))),new Vector2f(0,0),new Color(110,0,110)));
			
		}
		
		float mass = 0.1f + (gen.nextInt(100)/10);
		
		Vector2f vel = momentum.getOposite().div(mass);
		
		planets.add(new Planet(mass,new Vector2f((float)(400 + gen.nextInt(481)),(float)(300 + gen.nextInt(361))),vel,new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256))));
		
		return new PlanetSystem(planets);
		
	}
	
	public static PlanetSystem createSingleSolarSystem(){
		
		ArrayList<Planet> planets = new ArrayList<Planet>();
				
		planets.add(new Planet(10, new Vector2f(400, GamePanel.HEIGHT/2), new Vector2f(0,6.4f), new Color(0,200,60)));
		
		planets.add(new Planet(1, new Vector2f(430, GamePanel.HEIGHT/2), new Vector2f(0,1), new Color(200,200,200)));
		
		planets.add(new Planet(1, new Vector2f(1000, GamePanel.HEIGHT/2), new Vector2f(0,-4.7533f), new Color(200,10,10)));
		
		planets.add(new Planet(0.01, new Vector2f(980, GamePanel.HEIGHT/2), new Vector2f(0,-7.0543f), new Color(200,200,250)));
		
		Vector2f momentum = new Vector2f(0,0);
		
		for(Planet p: planets){
			momentum = momentum.add(p.getVelocity().mul((float)p.getMass()));
		}
		
		double mass = 100;
		
		planets.add(new Planet(mass, new Vector2f(GamePanel.WIDTH/2, GamePanel.HEIGHT/2), momentum.getOposite().div((float)mass), new Color(200,200,0)));
		
		return new PlanetSystem(planets);
		
	}
	
	public static PlanetSystem createDoubleSolarSystem(double vel, double weight){
		
		ArrayList<Planet> planets = new ArrayList<Planet>();
		
		planets.add(new Planet(30, new Vector2f(GamePanel.WIDTH/2+200, GamePanel.HEIGHT/2), new Vector2f(0,2), new Color(240,150,0)));
		
		planets.add(new Planet(50, new Vector2f(GamePanel.WIDTH/2-200, GamePanel.HEIGHT/2), new Vector2f(0,-1.2f), new Color(200,200,0)));
		
		planets.add(new Planet(0.98 + weight, new Vector2f(1000, GamePanel.HEIGHT/2), new Vector2f(0, (float)vel), new Color(10, 200, 150)));
		
		Vector2f momentum = new Vector2f(0,0);
		
		for(Planet p: planets){
			momentum = momentum.add(p.getVelocity().mul((float)p.getMass()));
		}
		
		planets.get(1).setVelocity(planets.get(1).getVelocity().add(momentum.getOposite().div((float)planets.get(1).getMass())));
		
		return new PlanetSystem(planets);
		
	}
	
	public static PlanetSystem createBounceTestSystem(){
		
		ArrayList<Planet> planets = new ArrayList<Planet>();
		
		planets.add(new Planet(100,new Vector2f(100,100),new Vector2f(1.0f,0),Color.red));
		planets.add(new Planet(100,new Vector2f(300,100),new Vector2f(-1.0f,0),Color.red));
		
		planets.add(new Planet(100,new Vector2f(700,100),new Vector2f(0,1.0f),Color.red));
		planets.add(new Planet(100,new Vector2f(700,300),new Vector2f(0,-1.0f),Color.red));
		
		planets.add(new Planet(100,new Vector2f(300,300),new Vector2f(1f,1f),Color.red));
		planets.add(new Planet(100,new Vector2f(512,512),new Vector2f(-1f,-1f),Color.red));
		
		planets.add(new Planet(100,new Vector2f(800,300),new Vector2f(-1f,1f),Color.red));
		planets.add(new Planet(100,new Vector2f(588,512),new Vector2f(1f,-1f),Color.red));
		
		return new PlanetSystem(planets);
		
	}
	
	public void checkCollisions(){
		
		start:
		for(int i = 0; i < planets.size(); i++){
			
			Planet p1 = planets.get(i);
			//if(i == 1)System.out.println(p1.getVelocity().getLength());
			
			for(int p = i; p < planets.size(); p++){
				
				Planet p2 = planets.get(p);
				
				if(p1 == p2)continue;
				
				Vector2f dist = Physics.getDistance(p1, p2);
				
				if(dist.getLength() < (p1.getWidth() + p2.getWidth())/2){
					
					if(!GamePanel.BOUNCE)
						//mergePlanets(i, p, dist);
						;
					else if(!p1.bounced && !p2.bounced){
						bouncePlanets(i, p, dist);
						p1.bounced = true;
						p2.bounced = true;
					}
					
				}
				
			}
		}
		
		if(GamePanel.BOUNCE){
			for(Planet p : planets){
				p.bounced = false;
			}
		}
		
	}
	
	private void mergePlanets(int a, int b, Vector2f dist){
		
		Planet p1 = planets.get(a);
		Planet p2 = planets.get(b);
		
		Vector2f vel = (p1.getVelocity().mul((float)p1.getMass()).add(p2.getVelocity().mul((float)p2.getMass()))).div((float)(p1.getMass()+p2.getMass()));
		
		Color color = new Color((p1.getColor().getRed() + p2.getColor().getRed())/2, (p1.getColor().getGreen() + p2.getColor().getGreen())/2, (p1.getColor().getBlue() + p2.getColor().getBlue())/2);
		
		planets.remove(a);
		planets.remove(b-1);
		planets.add(new Planet((p1.getMass() + p2.getMass()), (Vector2f)((p1.getMass() > p2.getMass()) ? p1.getLocation() : p2.getLocation()), vel, color));
		
		
	}
	
	private void bouncePlanets(int a, int b, Vector2f dist){
		
		Planet p1 = planets.get(a);
		Planet p2 = planets.get(b);
		
		Vector2f vel1 = p1.getVelocity().clone();
		Vector2f vel2 = p2.getVelocity().clone();
		
		vel1 = vel1.rotate((float)(-dist.getAngle()              +p1.getVelocity().getAngle())).getOposite();
		vel2 = vel2.rotate((float)(-dist.getOposite().getAngle() +p2.getVelocity().getAngle()));
		
		if(vel1.getX() / Math.abs(vel1.getX()) == vel2.getX()/ Math.abs(vel2.getX())){
			return;
		}
		
		Vector2f[] temp = Physics.elasticCollision(p1.getMass(), p2.getMass(), vel1, vel2);
		
		//Vector2f temp1 = new Vector2f(vel1.getX(),temp[0].getY());
		//Vector2f temp2 = new Vector2f(vel2.getX(),temp[1].getY());
		
		Vector2f temp1 = new Vector2f(temp[0].getX(),vel1.getY());
		Vector2f temp2 = new Vector2f(temp[1].getX(),vel2.getY());
		
		temp1 = temp1.rotate((float)(-dist.getAngle()              +p1.getVelocity().getAngle()));
		temp2 = temp2.rotate((float)(-dist.getOposite().getAngle() +p2.getVelocity().getAngle()));
		
		p1.setVelocity(temp1);
		p2.setVelocity(temp2);
		
		System.out.println(p1.getVelocity().getLength());
		
	}
	
	public void update(){
		
		checkCollisions();
		
		for(Planet p:planets){
			
			p.update();
			
		}

		for(Planet p:planets){
			
			p.setLocation();
			
		}
		
	}
	
	public void draw(Graphics2D g){
		
		for(Planet p:planets){
			p.draw(g);
		}
		
	}

	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	
}
