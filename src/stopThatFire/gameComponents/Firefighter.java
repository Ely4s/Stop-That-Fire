package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;
import stopThatFire.utils.Utils;

public class Firefighter extends EntityLiving
{
	public static final int FIREFIGHTER_zindex = 3;
	public static final int FIREFIGHTER_w = 16;
	public static final int FIREFIGHTER_h = 16;
	public static final float FIREFIGHTER_life = 10;
	public static final float FIREFIGHTER_deadCost = 100;
	public static final float FIREFIGHTER_naturalRegeneration = 0.001f;
	public static final float FIREFIGHTER_speed = 0.2f;
	public static final float FIREFIGHTER_throwWaterDistance = 100;
	public static final float FIREFIGHTER_throwWaterColdDown = 1;
	public static final String FIREFIGHTER_textureDefault = "firefighter.png";
	
	private Tree treeFireObjectif;
	private float throwWaterColdDown;
	private float throwWaterDistance;
	
	//------ Constructors Firefighter ------//
	
	public Firefighter()
	{
		super(new Pos(0,0), new Size(FIREFIGHTER_w, FIREFIGHTER_h), FIREFIGHTER_speed, FIREFIGHTER_life, FIREFIGHTER_naturalRegeneration, 1, FIREFIGHTER_deadCost, FIREFIGHTER_textureDefault, FIREFIGHTER_zindex);
		this.life = Firefighter.FIREFIGHTER_life;
		this.treeFireObjectif = null;
		this.throwWaterColdDown = 0;
		this.setThrowWaterDistance(FIREFIGHTER_throwWaterDistance);
	}
	
	public Firefighter(Pos pos)
	{
		this();
		this.getPos().set(pos);
		this.getPosObjectif().set(pos);
	}
	
	public Firefighter(Pos pos, Pos posObjectif)
	{	
		this(pos);
		this.getPosObjectif().set(posObjectif);
	}
	
	//------ Getters/Setters Firefighter ------//
	
	public float getThrowWaterDistance() {return throwWaterDistance;}
	public void setThrowWaterDistance(float throwWaterDistance) {this.throwWaterDistance = throwWaterDistance;}
	
	public Tree getTreeFireObjectif() {return this.treeFireObjectif;}
	public void setTreeFireObjectif(Tree treeFireObjectif) {this.treeFireObjectif = treeFireObjectif;}
	
	//------ Methods Firefighter ------//
	
	//update collision between this [Firefighter] and [Tree.Fire] of [Forest]
	public boolean updateCollisionWithTreesInFire(Forest forest)
	{
		boolean collision = false;
		
		for(int i = 0; i < forest.getTreesInFire().size(); i++)
		{
			if(this.isAlive() && this.isInCollision2AABB(forest.getTreesInFire().get(i)))
			{
				this.takeDamage(forest.getTreesInFire().get(i).getFire().getOnFirefighterDamage());
				collision = true;
			}
		}
		
		return collision;
	}
	
	//update collision between this [Firefighter] and all [Water]
	public boolean updateCollisionWithEmbers(ArrayList<Ember> embers)
	{
		boolean collision = false;
		
		for(int i = 0; i < embers.size(); i++)
		{
			if(this.isAlive() && this.isInCollision2AABB(embers.get(i)))
			{
				this.takeDamage(embers.get(i).getOnFirefighterDamage());
				embers.remove(i); i--;
				collision = true;
			}
		}
		
		return collision;
	}
	
	//update [treeFireObjectif] of this [Firefighter]
	public void updateThrowWater(Forest forest, ArrayList<Water> waters)
	{
		if(this.isAlive() && this.pos.equals(this.posObjectif))
		{
			boolean useNew = true;
			float distance = -1;
			int index = -1;
			
			//new way to process
			if(useNew)
			{
				if(	this.treeFireObjectif == null || 
					Utils.distanceTwoPos(this.treeFireObjectif.getPos(), this.getPos()) > this.getThrowWaterDistance() ||
					!this.treeFireObjectif.isAlive() || 
					!this.treeFireObjectif.getIsBurning())
				{
					for(int k = 0; k < forest.getTreesInFire().size(); k++)
					{
						float distanceToIt = Utils.distanceTwoPos(forest.getTreesInFire().get(k).getPos(), this.getPos());
						
						if(distanceToIt <= this.getThrowWaterDistance() && (distanceToIt < distance || distance == -1))
						{
							index = k;
							distance = distanceToIt;
						}
					}
					
					if(index != -1)
					{
						this.treeFireObjectif = forest.getTreesInFire().get(index);
					}
				}
				
				if(this.treeFireObjectif != null && (!this.treeFireObjectif.getIsBurning() || !this.treeFireObjectif.isAlive() || Utils.distanceTwoPos(this.treeFireObjectif.getPos(), this.getPos()) > this.getThrowWaterDistance()))
				{
					this.treeFireObjectif = null;
				}
				
				if(this.treeFireObjectif != null)
				{
					this.throwWater(this.treeFireObjectif.pos, waters);
				}
			}
			//old way to process
			else
			{
				for(int k = 0; k < forest.getTreesInFire().size(); k++)
				{
					float distanceToIt = Utils.distanceTwoPos(forest.getTreesInFire().get(k).getPos(), this.getPos());
					
					if(distanceToIt <= this.getThrowWaterDistance() && (distanceToIt < distance || distance == -1))
					{
						index = k;
						distance = distanceToIt;
					}
				}
				
				if(index != -1)
				{
					this.throwWater(forest.getTreesInFire().get(index).getPos(), waters);
				}
			}
		}
	}
	
	//update this [Firefighter]
	public void update(ArrayList<Water> waters, Forest forest, ArrayList<Ember> embers, boolean force)
	{
		this.moveToPosObjectif(force);
		boolean collisionWithTrees = this.updateCollisionWithTreesInFire(forest);
		boolean collisionWithEmbers = this.updateCollisionWithEmbers(embers);
		
		if(this.isAlive() && !(collisionWithTrees||collisionWithEmbers)) this.regenerateLife();
		
		this.updateThrowWater(forest, waters);
	}
	
	//perform [throwWater()] if possible for this [Firefighter]
	public void throwWater(Pos posFireObjectif, ArrayList<Water> waters)
	{
		if(this.throwWaterColdDown <= 0)
		{
			waters.add(new Water(this.pos, posFireObjectif));
			this.throwWaterColdDown = FIREFIGHTER_throwWaterColdDown;
		}
		else
		{
			this.throwWaterColdDown -= 0.1f;
		}
	}
	
	public void draw()
	{
		super.draw();
		
		if(this.isAlive() && this.life != this.lifeMax)
		{
			LifeBar lb = new LifeBar(this);
			lb.draw();
		}
	}
}
