package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;
import stopThatFire.utils.Utils;

public class Water extends EntityLiving
{
	public final static float WATER_w = 8;
	public final static float WATER_h = 8;
	public final static float WATER_timeLife = 2000;
	public final static float WATER_onFireDamage = 0.1f;
	public final static String WATER_textureDefault = "water.png";
	public final static int WATER_zindex = 2;
	public final static float WATER_speed = 0.5f;
	public final static int WATER_timeLifeFluctuation = 2;
	public final static int WATER_posObjectifFluctuation = 5;
	
	private float onFireDamage;
	
	//------ Constructors Water ------//
	
	public Water(Pos pos, Pos posObjectif)
	{
		super(pos, new Size(WATER_w, WATER_h), WATER_speed, 1, 0, WATER_timeLife, 0, WATER_textureDefault, WATER_zindex);
		this.getPosObjectif().setXY(posObjectif.x + Utils.rand(-1*WATER_posObjectifFluctuation, WATER_posObjectifFluctuation), posObjectif.y + Utils.rand(-1*WATER_posObjectifFluctuation, WATER_posObjectifFluctuation));
		this.setHasPosObjectif(true);
		this.setOnFireDamage(WATER_onFireDamage);
	}
	
	//------ Getters/Setters Water ------//
	
	public float getOnFireDamage() {return this.onFireDamage;}
	public void setOnFireDamage(float onFireDamage) {	this.onFireDamage = onFireDamage;}
	
	//------ Methods Water ------//
	
	//move this [Water] to [posObjectif]
	@Override
	public void moveToPosObjectif(boolean force)
	{
		this.applyTimeLife();
		super.moveToPosObjectif(force);
	}
	
	//reduce life of a [Fire]
	public void stopFire(Fire fire)
	{
		fire.setLife(fire.getLife()-this.onFireDamage);
	}
	
	//draw this [Water]
	public static void draw(ArrayList<Water> waters)
	{
		for(int i = 0; i < waters.size(); i++)
		{
			waters.get(i).draw();
		}
	}
	
	//move all [Water] to [posObjectif]
	public static void moveToPosObjectif(ArrayList<Water> waters)
	{
		for(int i = 0; i < waters.size(); i++)
		{
			waters.get(i).moveToPosObjectif(false);
			
			if(!waters.get(i).isAlive() || !waters.get(i).getHasPosObjectif())
			{
				waters.remove(i);
				i--;
			}
		}
	}
	
	//update all [Water]
	public static void update(ArrayList<Water> waters)
	{
		Water.moveToPosObjectif(waters);
	}
}
