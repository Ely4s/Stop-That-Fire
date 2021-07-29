package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;
import stopThatFire.utils.Utils;

public abstract class Tree extends EntityLiving
{
	public static int TREE_zindex = 4;
	public static float TREE_w = 16;
	public static float TREE_h = 16;
	public static float TREE_koalaPosOffset = 4;
	
	private int chanceToTakeFire;
	private int chanceToCatchFire;
	private int chanceToSpreadFire;
	private int spreadFireDistance;
	private int spreadFireNbr;
	private boolean isBurning;
	private Flame flame;
	private Koala koala;
	
	//------ Constructors Tree ------//
	
	public Tree(float life, float naturalRegeneration, float deadCost, int chanceToTakeFire, int chanceToCatchFire, int chanceToSpreadFire, int spreadFireNbr, int spreadFireDistance, String texture)
	{
		super(new Pos(0,0), new Size(TREE_w, TREE_h), 0, life, naturalRegeneration, 1, deadCost, texture, TREE_zindex);
		
		this.isBurning = false;
		this.flame = null;
		this.chanceToTakeFire = chanceToTakeFire;
		this.chanceToCatchFire = chanceToCatchFire;
		this.chanceToSpreadFire = chanceToSpreadFire;
		this.spreadFireNbr = spreadFireNbr;
		this.spreadFireDistance = spreadFireDistance;
		this.setKoala(null);
	}
	
	public Tree(Pos pos, float life, float deadCost, float naturalRegeneration, int chanceToTakeFire, int chanceToCatchFire, int chanceToSpreadFire, int spreadFireNbr, int spreadFireDistance, String texture)
	{
		this(life,deadCost, naturalRegeneration, chanceToTakeFire, chanceToCatchFire, chanceToSpreadFire, spreadFireNbr, spreadFireDistance, texture);
		this.pos.set(pos);
	}
	
	//------ Getters/Setters Tree ------//
	
	public int getChanceToTakeFire() {return chanceToTakeFire;}
	protected void setChanceToTakeFire(int chanceToTakeFire) {this.chanceToTakeFire = chanceToTakeFire;}

	public int getChanceToSpreadFire() {	return chanceToSpreadFire;}
	protected void setChanceToSpreadFire(int chanceToSpreadFire) {	this.chanceToSpreadFire = chanceToSpreadFire;}

	public int getChanceToCatchFire() {return chanceToCatchFire;}
	protected void setChanceToCatchFire(int chanceToCatchFire) {	this.chanceToCatchFire = chanceToCatchFire;}

	public int getspreadFireNbr() {return spreadFireNbr;}
	protected void setspreadFireNbr(int spreadFireNbr) {this.spreadFireNbr = spreadFireNbr;}
	
	public int getspreadFireDistance() {	return spreadFireDistance;}
	protected void setspreadFireDistance(int spreadFireDistance) {	this.spreadFireDistance = spreadFireDistance;}

	public Flame getFire() {return flame;}
	protected void setFire(Flame flame) {this.flame = flame;}

	public Koala getKoala() {return koala;}
	protected void setKoala(Koala koala) {this.koala = koala;}
	
	public boolean getIsBurning() {return this.isBurning;}
	public void setIsBurning(boolean isBurning)
	{
		if(isBurning)
		{
			if(this.flame == null) this.flame = new Flame(this.pos);
		}
		else
		{
			this.flame = null;
		}
		
		this.isBurning = isBurning;
	}
	
	//------ Methods Tree ------//
	
	//apply damage of flame on this [Tree] if he is burning
	public void burn() {if(isBurning) this.life -= this.flame.getOnTreeDamage();}
	
	private boolean willTakeFire() 	{return willFire(this.chanceToTakeFire);}
	private boolean willSpreadFire()	{return willFire(this.chanceToSpreadFire);}
	private boolean willCatchFire() 	{return willFire(this.chanceToCatchFire);}
	
	//check if this [Tree] can do fire related action
	private boolean willFire(int chanceToFire)
	{		
		if(chanceToFire == -1) return false;
		else return Utils.rand(0, chanceToFire) == 0;
	}
	
	//try to set fire on this [Tree]
	public void TryTakeFire()
	{
		if(!this.getIsBurning() && this.willTakeFire())
		{
			this.setIsBurning(true);
		}
	}
	
	//try to set fire on this [Tree]
	public void TryCatchFire()
	{
		if(!this.getIsBurning() && this.willCatchFire())
		{
			this.setIsBurning(true);
		}
	}
	
	//try to spread fire from this [Tree]
	public void TrySpreadFire(ArrayList<Ember> embers)
	{
		if(this.getIsBurning() && this.willSpreadFire())
		{	
			for(int i = 0; i < this.spreadFireNbr; i++)
			{
				Ember ember = new Ember(this.pos, new Pos(this.pos.x + Utils.rand(-1*this.spreadFireDistance, this.spreadFireDistance), this.pos.y + Utils.rand(-1*this.spreadFireDistance, this.spreadFireDistance)));
				embers.add(ember);
			}
		}
	}
	
	//update collision of this [Tree] with all [Ember]
	public void updateCollisionWithEmbers(ArrayList<Ember> embers)
	{
		if(this.isAlive())
		{
			this.TryTakeFire();
			
			if(!this.getIsBurning())
			{
				for(int i = 0; i < embers.size(); i++)
				{
					if(embers.get(i).isInCollision2AABB(this))
					{	
						this.TryCatchFire();
						
						if(this.getIsBurning())
						{
							embers.remove(i); i--;
						}
					}
				}
			}
			
			if(!this.getIsBurning())
			{
				this.regenerateLife();
			}
		}
	}
	
	//update collision of this [Tree] with all [Water]
	public void updateCollisionWithWaters(ArrayList<Ember> embers, ArrayList<Water> waters)
	{
		if(this.isAlive())
		{
			this.TrySpreadFire(embers);
			
			if(this.getIsBurning())
			{
				this.burn();
				
				for(int i = 0; i < waters.size(); i++)
				{
					if(waters.get(i).isInCollision2AABB(this))
					{	
						waters.get(i).stopFire(this.getFire());
					}
				}
			}
		}
	}
	
	//update this [Tree]
	public void update(ArrayList<Ember> embers)
	{
		this.updateCollisionWithEmbers(embers);
	}
	
	//update this [Tree]
	public void update(ArrayList<Ember> embers, ArrayList<Water> waters)
	{
		this.updateCollisionWithWaters(embers, waters);
	}
	
	//draw this [Tree]
	@Override
	public void draw()
	{
		if(this.isBurning)
		{
			this.flame.draw();
		}
		
		if(this.koala != null)
		{
			this.koala.draw();
		}
		
		if(this.isAlive() && this.life != this.lifeMax)
		{

			LifeBar lb = new LifeBar(this);
			lb.draw();
		}
		
		super.draw();
	}
}
