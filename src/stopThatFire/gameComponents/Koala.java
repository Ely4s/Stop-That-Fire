package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Koala extends EntityLiving 
{
	public static final int KOALA_zindex = 3;
	public static final int KOALA_w = 16;
	public static final int KOALA_h = 16;
	public static final float KOALA_life = 100;
	public static final float KOALA_deadCost = 300;
	public static final float KOALA_naturalRegeneration = 0.001f;
	public static final String KOALA_texture = "koala.png";
	
	//------ Constructors Koala ------//
	
	public Koala(Pos pos)
	{
		super(pos, new Size(KOALA_w, KOALA_h), 0, KOALA_life, KOALA_naturalRegeneration, 1, 500, KOALA_texture, KOALA_zindex);
	}

}
