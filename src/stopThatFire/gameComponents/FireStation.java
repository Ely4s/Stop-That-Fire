package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.ObjectifModifier;
import stopThatFire.utils.Pos;

public class FireStation 
{
	private ArrayList<FirefighterUnit> firefighterUnits;
	private FirefighterUnit firefighterUnitSelected;
	
	//------ Constructors FireStation ------//
	
	public FireStation()
	{
		this.setFirefighterUnits(new ArrayList<FirefighterUnit>());
		this.firefighterUnitSelected = null;
	}

	//------ Getters/Setters FireStation ------//
	
	public ArrayList<FirefighterUnit> getFirefighterUnits() {return firefighterUnits;}
	public FirefighterUnit getFirefighterUnits(int index) {return firefighterUnits.get(index);}
	public void setFirefighterUnits(ArrayList<FirefighterUnit> firefighterUnits) {this.firefighterUnits = firefighterUnits;}
	
	//------ Methods FireStation ------//
	
	//add [FirefightersUnit] to this [FireStation] (default number)
	public void addFirefighterUnit(Pos pos)
	{
		this.getFirefighterUnits().add(new FirefighterUnit(pos));
	}
	
	//add [FirefightersUnit] to this [FireStation]
	public void addFirefighterUnit(Pos pos, int firefighterNbr)
	{
		this.getFirefighterUnits().add(new FirefighterUnit(pos, firefighterNbr));
	}
	
	//update selected [FirefightersUnit]
	private void updateSelected(boolean isMouseClick, int xMouse, int yMouse)
	{
		FirefighterUnit ffu_selectedTmp = null;
		FirefighterUnit ffu_it = null;
		
		if(isMouseClick)
		{
			for(int i = 0; i < firefighterUnits.size(); i++)
			{
				ffu_it = firefighterUnits.get(i);
							
				if(ffu_it.isInCollisionAABBPoint(new Pos(xMouse, yMouse)))
				{
					if((ffu_selectedTmp == null ) || (ffu_it.getZ() <= ffu_selectedTmp.getZ()))
					{	
						ffu_selectedTmp = ffu_it;
					}
				}
			}
			
			if (this.firefighterUnitSelected != null)
			{
				if(!this.firefighterUnitSelected.getHasPosObjectif() && this.firefighterUnitSelected.getAllFireFightersHasPosObjectif(false))
				{
					this.firefighterUnitSelected.setAllPosObjectif(new Pos(xMouse, yMouse), ObjectifModifier.True, ObjectifModifier.True);
					this.firefighterUnitSelected.unselect();
					this.firefighterUnitSelected = null;
				}
				else
				{
					this.firefighterUnitSelected.unselect();
					this.firefighterUnitSelected = null;
					
					if(ffu_selectedTmp != null)
					{
						this.firefighterUnitSelected = ffu_selectedTmp;
						this.firefighterUnitSelected.select(this.firefighterUnitSelected.getAllFireFightersHasPosObjectif(false));
					}
				}
			}
			else if (this.firefighterUnitSelected == null && ffu_selectedTmp != null)
			{
				this.firefighterUnitSelected = ffu_selectedTmp;
				this.firefighterUnitSelected.select(this.firefighterUnitSelected.getAllFireFightersHasPosObjectif(false));
			}
		}
	}
	
	//update this [FireStation]
	public float update(ArrayList<Water> waters, Forest forest, ArrayList<Ember> embers, boolean nodeForce, boolean subForce, boolean isMouseClicked, int xMouse, int yMouse, float score)
	{
		this.updateSelected(isMouseClicked, xMouse, yMouse);
		
		for(int i = 0; i < this.getFirefighterUnits().size(); i++)
		{
			score = this.getFirefighterUnits().get(i).update(waters, forest, embers, nodeForce, subForce, score);
			
			if(this.getFirefighterUnits().get(i).getFirefighters().isEmpty())
			{
				this.getFirefighterUnits().remove(i); i--;
			}
		}
		
		return score;
	}
	
	//draw this [FireStation]
	public void draw()
	{
		for(int i = 0; i < this.getFirefighterUnits().size(); i++)
		{
			this.getFirefighterUnits(i).draw();
		}
	}
}
