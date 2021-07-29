package stopThatFire.utils;

public class Size
{
	public float w;
	public float h;

	//------ Constructors Size ------//
	
	public Size() {this.w = 0; this.h = 0;};
	public Size(float w, float h) {this.w = w; this.h = h;}
	
	//------ Getters/Setters Size ------//
	
	public void setWH(float w, float h) {this.w = w; this.h = h;}
	public void set(Size size) {this.w = size.w; this.h = size.h;}
}
