package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Box2D
{
	protected Pos pos;
	protected Size size;
	
	//------ Constructors Box2D ------//
	
	public Box2D()
	{
		this.pos = new Pos();
		this.size = new Size();
	}
	
	public Box2D(Pos pos, Size size)
	{
		this.pos = new Pos(pos.x, pos.y);
		this.size = new Size(size.w, size.h);
	}
	
	//------ Getters/Setters Box2D ------//
	
	public Pos getPos() {return this.pos;};
	public void setPos_ptr(Pos pos) {this.pos = pos;}

	public Size getSize() {return this.size;}
	public void setSize_ptr(Size size){this.size = size;}
	
	//------ Methods Box2D ------//
	
	//return [true] if this [Box2d] is in collision with a other [Box2D], else return [false]
	public boolean isInCollision2AABB(Box2D b2D)
	{
		float x1 = this.pos.x-this.size.w/2;
		float y1 = this.pos.y-this.size.h/2;
		float w1 = this.size.w;
		float h1 = this.size.h;
		
		float x2 = b2D.pos.x-b2D.size.w/2;
		float y2 = b2D.pos.y-b2D.size.h/2;
		float w2 = b2D.size.w;
		float h2 = b2D.size.h;
		
	   if(
		   (x2 >= x1 + w1) || 
		   (x2 + w2 <= x1) || 
		   (y2 >= y1 + h1) || 
		   (y2 + h2 <= y1))
	   {
		   return false; 
	   }
	   else
	   {
		   return true; 
	   }
	}
	
	//return [true] if this [Box2d] is in collision with a [Pos] (= point), else return [false]
	public boolean isInCollisionAABBPoint(Pos point)
	{
		float x = this.pos.x-this.size.w/2;
		float y = this.pos.y-this.size.h/2;
		float w = this.size.w;
		float h = this.size.h;
		
		if(
			point.x >= x &&
			point.x< x + w && 
			point.y >= y && 
			point.y< y + h)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
}

