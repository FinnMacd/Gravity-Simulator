package physics;

import entity.Planet;
import entity.Vector2f;

public class Physics {
	
	public static double G = (6.67*Math.pow(10, -11));
	public static long massCon = (long)Math.pow(11, 13), distCon = 30000000, timeCon = 30000000;
	//public static long massCon = (long)Math.pow(11, 8), distCon = 30000000;
	
	
	public static Vector2f getGForce(double m, double M, Vector2f r){
		
		double angle = r.getAngle();
		
		//double force = ((m*massCon)*(M*massCon))/((r.getLength()*distCon));
		double force = ((m*massCon)*(M*massCon))/((r.getLength()*distCon)*(r.getLength()*distCon));
		
		return new Vector2f(angle, (float)(force * G));
		
	}
	
	public static Vector2f getDistance(Planet p1, Planet p2){
		
		Vector2f dist = new Vector2f(p2.getLocation().getX()-p1.getLocation().getX(),
			    					 p2.getLocation().getY()-p1.getLocation().getY());
		
		//if(dist.getX() < 0.1)dist.setX(0.1f);
		//if(dist.getY() < 0.1)dist.setY(0.1f);
		
		return dist;
		
	}
	
	public static Vector2f[] elasticCollision(double m, double M, Vector2f vel1, Vector2f vel2){
		
		return  new Vector2f[]{vel1.mul((float)((m - M)/(m + M))).add(vel2.mul((float)((2 * M)/(m + M)))),
							   vel1.mul((float)((2 * m)/(m + M))).add(vel2.mul((float)((M - m)/(m + M))))};
		
	}
	
}
