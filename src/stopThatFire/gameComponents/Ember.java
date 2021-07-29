package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Ember extends Fire
{
	public final static float EMBER_w = 8;
	public final static float EMBER_h = 8;
	public final static float EMBER_life = 1;
	public final static float EMBER_onFirefighterDamage = 0.1f;
	public final static String EMBER_textureDefault = "ember.png";
	public final static int EMBER_zindex = 6;
	public final static float EMBER_speed = 0.1f;
	public final static int EMBER_timeLifeFluctuation = 2;
	
	//------ Constructors Ember ------//
	
	public Ember(Pos pos, Pos posObjectif)
	{
		super(pos, new Size(EMBER_w, EMBER_h), EMBER_speed, EMBER_life, 0, 0, EMBER_textureDefault, EMBER_zindex, 0, EMBER_onFirefighterDamage);
		this.getPosObjectif().set(posObjectif);
		this.setHasPosObjectif(true);
	}
	
	//------ Methods Ember ------//
	
	//draw all [Ember] in a [ArrayList<Ember>]
	public static void draw(ArrayList<Ember> embers)
	{
		for(int i = 0; i < embers.size(); i++)
		{
			embers.get(i).draw();
		}
	}
	
	//move all [Ember] in a [ArrayList<Ember>] to their [posObjectif]
	public static void moveToPosObjectif(ArrayList<Ember> embers)
	{
		for(int i = 0; i < embers.size(); i++)
		{
			embers.get(i).moveToPosObjectif(false);
			
			if(!embers.get(i).getHasPosObjectif())
			{
				embers.remove(i);
				i--;
			}
		}
	}
	
	//update all [Ember] in a [ArrayList<Ember>]
	public static void update(ArrayList <Ember> embers)
	{
		Ember.moveToPosObjectif(embers);
	}
}
