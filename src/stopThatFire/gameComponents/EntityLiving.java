package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class EntityLiving extends Entity
{
	public static final float ENTITYLIVING_timeLifeDecrease = 0.1f;
	float life;
	float lifeMax;
	float naturalRegeneration;
	float timeLife;
	float timeLifeMax;
	float deadCost;
	
	//------ Constructors EntityLiving ------//
	
	public EntityLiving(Pos pos, Size size, float speed, float life, float naturalRegeneration, float timeLife, float deadCost, String texture, float zindex)
	{
		super(pos, size, speed, texture, zindex);
		this.life = life;
		this.lifeMax = life;
		this.naturalRegeneration = naturalRegeneration;
		this.timeLife = timeLife;
		this.timeLifeMax = timeLife;
		this.deadCost = deadCost;
	}
	
	//------ Getters/Setters EntityLiving ------//
	
	public float getLife() {return this.life;}
	public void setLife(float life){this.life = life;}
	
	public float getTimeLife() {return timeLife;}
	public void setTimeLife(float timeLife) {this.timeLife = timeLife;}
	
	public void applyTimeLife() {this.timeLife -= ENTITYLIVING_timeLifeDecrease;}
	public void applyTimeLife(float multiplier) {this.timeLife -= (ENTITYLIVING_timeLifeDecrease*multiplier);}
	
	public boolean isAlive() {return this.life > 0 && this.timeLife > 0;}
	
	public void takeDamage(float damage) {this.life-=damage; if(this.life < 0) this.life = 0;}
	public void regenerateLife() {this.life+=this.naturalRegeneration; if(this.life > this.lifeMax) this.life = this.lifeMax;}
}
