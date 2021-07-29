package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Flame extends Fire
{
	public final static float FLAME_life = 50;
	public final static float FLAME_w = 12;
	public final static float FLAME_h = 12;
	public final static float FLAME_onTreeDamage = 0.02f;
	public final static float FLAME_onTreeFirefighters = 0.002f;
	public final static String FLAME_textureDefault = "flame.png";
	public final static int FLAME_zindex = 5;
	public final static float FLAME_speed = 0;
	
	//------ Constructors Flame ------//
	
	public Flame(Pos pos)
	{
		super(pos, new Size(FLAME_w, FLAME_h), FLAME_speed, FLAME_life, 0, 0, FLAME_textureDefault, FLAME_zindex, FLAME_onTreeDamage, FLAME_onTreeFirefighters);
	}
	
	@Override
	public float getLife() {return life;}
	@Override
	public void setLife(float life) {this.life = life;}
	
	@Override
	public boolean isAlive() {return life > 0;}
}
