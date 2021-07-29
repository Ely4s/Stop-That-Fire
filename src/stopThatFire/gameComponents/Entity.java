package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;
import stopThatFire.utils.Utils;

public class Entity extends Drawable
{
	protected Pos posObjectif;
	protected boolean hasPosObjectif;
	protected float speed;
	
	//------ Constructors Entity ------//
	
	public Entity()
	{
		super();
		this.posObjectif = new Pos();
		this.hasPosObjectif = false;
		this.speed = 0;
	}
	
	public Entity(Pos pos, Size size, float speed, String texture, float zindex)
	{
		super(pos, size, texture, zindex);
		this.posObjectif = new Pos();
		this.hasPosObjectif = false;
		this.speed = speed;
	}
	
	//------ Getters/Setters Entity ------//
	
	public boolean getHasPosObjectif() {return this.hasPosObjectif;}
	public void setHasPosObjectif(boolean hasPosObjectif) { this.hasPosObjectif = hasPosObjectif;}
	
	public Pos getPosObjectif() {return this.posObjectif;}
	public void setPosObjectif_ptr(Pos posObjectif){this.posObjectif = posObjectif;}
	
	public float getSpeed() {return speed;}
	public void setSpeed(float speed) {this.speed = speed;}
	
	//------ Methods Entity ------//
	
	//move this [entity] to his [posObjectif]
	public void moveToPosObjectif(boolean force)
	{
		if(Math.round(this.pos.x) == Math.round(this.posObjectif.x))
		{
			this.pos.x = this.posObjectif.x;
		}
		
		if(Math.round(this.pos.y) == Math.round(this.posObjectif.y))
		{
			this.pos.y = this.posObjectif.y;
		}
		
		if(!(Math.round(this.pos.x) == Math.round(this.posObjectif.x) && Math.round(this.pos.y) == Math.round(this.posObjectif.y)))
		{
			if(!(!this.getHasPosObjectif() && !force))
			{
				Pos posNormalized = Utils.getPosNormalizedFromTwoPos(this.pos, this.posObjectif);
				this.pos.addToX(posNormalized.x*this.speed);
				this.pos.addToY(posNormalized.y*this.speed);
			}
		}
		else
		{
			this.hasPosObjectif = false;
		}
	}
}
