package entity;

public class Vector2f {
	
	public static final Vector2f blank = new Vector2f(0,0);
	
	private float x, y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(double angle, float length){
		
		x = (float)Math.cos(angle)*length;
		y = (float)Math.sin(angle)*length;
		
	}
	
	public Vector2f add(Vector2f v2){
		return new Vector2f(x + v2.getX(), y + v2.getY());
	}
	
	public Vector2f div(float d){
		return new Vector2f(x/d,y/d);
	}
	
	public Vector2f mul(float m){
		return new Vector2f(x*m, y*m);
	}
	
	public Vector2f log(){
		//return new Vector2f((float)Math.log10(x), (float)Math.log10(y));
		return this;
	}
	
	public Vector2f rotate(float angle){
		return new Vector2f(getAngle() + angle, (float)getLength());
	}
	
	public double getLength(){
		return Math.sqrt(x * x + y * y);
	}
	
	public double getAngle(){
		
		double ang = Math.atan((double)y/(double)x);
		//System.out.println(Math.toDegrees(ang));
		return ang;
		
	}
	
	public Vector2f getOposite(){
		
		return new Vector2f(x*-1,y*-1);
		
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2f clone(){
		return new Vector2f(x,y);
	}
	
}
