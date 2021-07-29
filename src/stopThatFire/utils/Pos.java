package stopThatFire.utils;

public class Pos
{	
	public float x;
	public float y;
	
	//------ Constructors Pos ------//
	
	public Pos() {this.x = 0; this.y = 0;}
	public Pos(float x, float y) {this.x = x; this.y = y;}

	//------ Getters/Setters Pos ------//
	
	public void setXY(float x, float y) {this.x = x; this.y = y;}
	public void addToX(float x) {this.x += x;}
	public void addToY(float y) {this.y += y;}
	public boolean equals(Pos pos) {return this.x == pos.x && this.y == pos.y;}
	public void set(Pos pos) {this.x = pos.x; this.y = pos.y;}
}
