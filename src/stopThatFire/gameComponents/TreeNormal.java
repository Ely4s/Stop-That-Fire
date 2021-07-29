package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;

public class TreeNormal extends Tree
{
	public static final float TREENORMAL_life = 100;
	public static final float TREENORMAL_deadCost = 5;
	public static final float TREENORMAL_naturalRegeneration = 0.005f;
	public static final int TREENORMAL_chanceToTakeFire = 500000;
	public static final int TREENORMAL_chanceToCatchFire = 300;
	public static final int TREENORMAL_chanceToSpreadFire = 500;
	public static final int TREENORMAL_spreadFireNbr = 5;
	public static final int TREENORMAL_spreadFireDistance = 34;
	public static final String TREENORMAL_textureDefault = "treeNormal.png";
	
	//------ Constructors TreeNormal ------//
	
	public TreeNormal()
	{
		super(TREENORMAL_life, TREENORMAL_naturalRegeneration, TREENORMAL_deadCost, TREENORMAL_chanceToTakeFire, TREENORMAL_chanceToCatchFire, TREENORMAL_chanceToSpreadFire, TREENORMAL_spreadFireNbr, TREENORMAL_spreadFireDistance, TREENORMAL_textureDefault);
	}
	
	public TreeNormal(Pos pos)
	{
		this();
		this.pos.set(pos);
	}
}

