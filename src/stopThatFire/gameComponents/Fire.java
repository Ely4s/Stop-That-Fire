package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public abstract class Fire extends EntityLiving
{
	private float onTreeDamage;
	private float onFirefighterDamage;
	
	//------ Constructors Fire ------//
	
	public Fire(Pos pos, Size size, float speed, float life, float naturalRegeneration, float timeLife, String texture, float zindex, float onTreeDamage, float onFirefighterDamage)
	{
		super(pos, size, speed, life, naturalRegeneration, timeLife, 0, texture, zindex);
		this.setOnTreeDamage(onTreeDamage);
		this.setOnFirefighterDamage(onFirefighterDamage);
	}
	
	//------ Getters/Setters Fire ------//
	
	public float getOnTreeDamage() {return onTreeDamage;}
	public void setOnTreeDamage(float onTreeDamage) {this.onTreeDamage = onTreeDamage;}
	
	public float getOnFirefighterDamage() {return onFirefighterDamage;}
	public void setOnFirefighterDamage(float onFirefighterDamage) {this.onFirefighterDamage = onFirefighterDamage;}
}
