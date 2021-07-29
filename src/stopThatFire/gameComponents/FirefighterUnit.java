package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.ObjectifModifier;
import stopThatFire.utils.Pos;
import stopThatFire.utils.Utils;

public class FirefighterUnit extends Entity
{
	public static int FIREFIGHTERUNIT_zindex = 1;
	public static int FIREFIGHTERUNIT_speed = 1;
	public static final int FIREFIGHTERUNIT_w = 150;
	public static final int FIREFIGHTERUNIT_h = 150;
	public static final int FIREFIGHTERUNIT_firefightersNbr = 10;
	public static final int FIREFIGHTERUNIT_precisionPlacement = 10;
	public static final String FIREFIGHTERUNIT_textureDefault = "firefighterUnit.png";
	public static final String FIREFIGHTERUNIT_textureSelected = "firefighterUnitSelected.png";
	public static final int FIREFIGHTERUNIT_dispatchRadius = (int)((((FIREFIGHTERUNIT_w+FIREFIGHTERUNIT_h)/2 - (Firefighter.FIREFIGHTER_w+Firefighter.FIREFIGHTER_h)/2)/2)*0.8);
	
	private ArrayList<Firefighter> firefighters;
	
	//------ Constructors FirefightersUnit ------//
	
	FirefighterUnit()
	{	
		super();
		this.setZindex(FIREFIGHTERUNIT_zindex);
		this.setSpeed(FIREFIGHTERUNIT_speed);
		this.setTexture(FIREFIGHTERUNIT_textureDefault);
		this.getSize().setWH(FIREFIGHTERUNIT_w, FIREFIGHTERUNIT_h);
		this.firefighters = new ArrayList<Firefighter>();
	}

	public FirefighterUnit(Pos pos)
	{
		this();
		this.getPos().set(pos);
		this.getPosObjectif().set(pos);
		this.setNewFireFighters(FIREFIGHTERUNIT_firefightersNbr);
	}
	
	public FirefighterUnit(Pos pos, int firefighterNbr)
	{
		this();
		this.getPos().set(pos);
		this.getPosObjectif().set(pos);
		this.setNewFireFighters(firefighterNbr);
	}	

	//------ Getters/Setters FirefightersUnit ------//
	
	public ArrayList<Firefighter> getFirefighters(){return this.firefighters;}

	//------ Methods FirefightersUnit ------//
	
	//reset and set new [fireFighter]s to this [FirefightersUnit]
	public void setNewFireFighters(int firefighterNbr)
	{
		this.firefighters.clear();
		for(int i = 0; i < firefighterNbr; i++)
		{this.firefighters.add(new Firefighter());}
		this.firefightersPosDispatch();
	}
	
	//dispach in this [FirefightersUnit] area all [fireFighter] 
	public void firefightersPosDispatch()
	{	
		for(int i = 0; i < this.firefighters.size(); i++)
		{
			Firefighter f = this.firefighters.get(i);
			
			boolean isInCollision = true;
			int loopNbr = 0;
			
			while(isInCollision && loopNbr < FIREFIGHTERUNIT_precisionPlacement)
			{
				isInCollision = false;
				
				f.getPos().setXY
				(		
					this.getPos().x + (Utils.rand(-1*FIREFIGHTERUNIT_dispatchRadius, FIREFIGHTERUNIT_dispatchRadius)), 
					this.getPos().y + (Utils.rand(-1*FIREFIGHTERUNIT_dispatchRadius, FIREFIGHTERUNIT_dispatchRadius)) 
				);
			
				
				for(int j = 0; j < this.getFirefighters().size(); j++)
				{
					if(i != j && f.isInCollision2AABB(this.getFirefighters().get(j)))
					{
						isInCollision = true;
					}
				}
				
				loopNbr++;
			}
			
			f.getPosObjectif().setXY(f.getPos().x, f.getPos().y);
		}
	}
	
	//dispach [posObjectif] all [fireFighter] in this [FirefightersUnit]
	public void firefightersPosObjectifDispatch()
	{	
		for(int i = 0; i < this.firefighters.size(); i++)
		{
			Firefighter f = this.firefighters.get(i);
			
			boolean isInCollision = true;
			int loopNbr = 0;
			
			while(isInCollision && loopNbr < FIREFIGHTERUNIT_precisionPlacement)
			{
				isInCollision = false;
				
				f.getPosObjectif().setXY
				(		
					this.getPosObjectif().x + (Utils.rand(-1*FIREFIGHTERUNIT_dispatchRadius, FIREFIGHTERUNIT_dispatchRadius)), 
					this.getPosObjectif().y + (Utils.rand(-1*FIREFIGHTERUNIT_dispatchRadius, FIREFIGHTERUNIT_dispatchRadius)) 
				);
			
				
				for(int j = 0; j < this.getFirefighters().size(); j++)
				{
					Firefighter f_tmp1 = new Firefighter(f.getPosObjectif());
					Firefighter f_tmp2 = new Firefighter(this.getFirefighters().get(j).getPosObjectif());
					
					if(i != j && f_tmp1.isInCollision2AABB(f_tmp2))
					{
						isInCollision = true;
					}
				}
				
				loopNbr++;
			}
		}
	}
	
	//set posObjectif for all [Firefighter] of this [FirefightersUnit]
	public void setAllPosObjectif(Pos posObjectif, ObjectifModifier nodeModifier, ObjectifModifier subModifier)
	{
		this.getPosObjectif().set(posObjectif);
		
		if(nodeModifier == ObjectifModifier.False) this.setHasPosObjectif(false);
		if(nodeModifier == ObjectifModifier.True) this.setHasPosObjectif(true);
		
		for(int i = 0; i < this.firefighters.size(); i++)
		{
			Firefighter f = this.firefighters.get(i);
			
			f.getPosObjectif().setXY
			(		
				this.getPosObjectif().x, 
				this.getPosObjectif().y
			);
				
			if(subModifier == ObjectifModifier.False) f.setHasPosObjectif(false);
			if(subModifier == ObjectifModifier.True) f.setHasPosObjectif(true);
		}
		
		this.firefightersPosObjectifDispatch();
	}
	
	//get if all [Firefighter] of this [FirefightersUnit] have posObjectif, can be revert with [has] boolean
	public boolean getAllFireFightersHasPosObjectif(boolean has)
	{
		boolean allHasPosObjectif = true;
		boolean allHasntPosObjectif = true;
		
		for(int i = 0; i < this.getFirefighters().size(); i++)
		{
			if(!this.getFirefighters().get(i).getHasPosObjectif())
			{
				allHasPosObjectif = false;
			}
			else
			{
				allHasntPosObjectif = false;
			}
		}
		
		if(has) return allHasPosObjectif;
		else return allHasntPosObjectif;
		
	}
	
	//update this [FirefightersUnit]
	public float update(ArrayList<Water> waters, Forest forest, ArrayList<Ember> embers, boolean nodeForce, boolean subForce, float score)
	{
		super.moveToPosObjectif(nodeForce);
		
		for(int i = 0; i < this.firefighters.size(); i++)
		{
			this.firefighters.get(i).update(waters, forest, embers, subForce);
			
			if(!this.firefighters.get(i).isAlive())
			{
				score += this.firefighters.get(i).deadCost;
				this.firefighters.remove(i); i--;	
			}
		}
		
		return score;
	}
	
	//set [textureSelected] to [FirefightersUnit] and eventually dispatch all [Firefighter.posObjectif]
	public void select(boolean dispatch)
	{
		if(dispatch)this.setAllPosObjectif(this.getPosObjectif(), ObjectifModifier.None, ObjectifModifier.None);
		this.setTexture(FIREFIGHTERUNIT_textureSelected);
	}
	
	//set unselected texture to [FirefightersUnit]
	public void unselect()
	{
		this.setTexture(FIREFIGHTERUNIT_textureDefault);
	}
	
	//draw [FirefightersUnit]
	public void draw()
	{
		super.draw();
		
		for(int i = 0; i < this.firefighters.size(); i++)
		{
			this.firefighters.get(i).draw();
		}
	}
}
