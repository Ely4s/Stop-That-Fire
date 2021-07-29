package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;

public class TreeLush extends Tree
{
	public static final float TREELUSH_life = 200;
	public static final float TREELUSH_deadCost = 10;
	public static final float TREELUSH_naturalRegeneration = 0.005f;
	public static final int TREELUSH_chanceToTakeFire = 500000;
	public static final int TREELUSH_chanceToCatchFire = 3000;
	public static final int TREELUSH_chanceToSpreadFire = 300;
	public static final int TREELUSH_spreadFireNbr = 5;
	public static final int TREELUSH_spreadFireDistance = 34;
	public static final String TREELUSH_textureDefault = "treeLush.png";
	
	//------ Constructors TreeLush ------//
	
	public TreeLush()
	{
		super(TREELUSH_life, TREELUSH_naturalRegeneration, TREELUSH_deadCost, TREELUSH_chanceToTakeFire, TREELUSH_chanceToCatchFire, TREELUSH_chanceToSpreadFire, TREELUSH_spreadFireNbr, TREELUSH_spreadFireDistance, TREELUSH_textureDefault);
	}
	
	public TreeLush(Pos pos)
	{
		this();
		this.pos.set(pos);
	}
}
