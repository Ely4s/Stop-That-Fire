package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;

public class TreeDry extends Tree
{
	public static final float TREEDRY_life = 30;
	public static final float TREEDRY_deadCost = 1;
	public static final float TREEDRY_naturalRegeneration = 0.005f;
	public static final int TREEDRY_chanceToTakeFire = -1;
	public static final int TREEDRY_chanceToCatchFire = 0;
	public static final int TREEDRY_chanceToSpreadFire = 25;
	public static final int TREEDRY_spreadFireNbr = 5;
	public static final int TREEDRY_spreadFireDistance = 44;
	public static final String TREEDRY_textureDefault = "treeDry.png";
	
	//------ Constructors TreeDry ------//
	
	public TreeDry()
	{
		super(TREEDRY_life, TREEDRY_naturalRegeneration, TREEDRY_deadCost, TREEDRY_chanceToTakeFire, TREEDRY_chanceToCatchFire, TREEDRY_chanceToSpreadFire, TREEDRY_spreadFireNbr, TREEDRY_spreadFireDistance, TREEDRY_textureDefault);
	}
	
	public TreeDry(Pos pos)
	{
		this();
		this.pos.set(pos);
	}
}
