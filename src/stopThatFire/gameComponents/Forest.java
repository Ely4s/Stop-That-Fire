package stopThatFire.gameComponents;

import java.util.ArrayList;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;
import stopThatFire.utils.Utils;

public class Forest extends Drawable
{
	private ArrayList<Tree> treesNotInFire;
	private ArrayList<Tree> treesInFire;
	private ArrayList<Tree> treesDead;
	
	private int treeTotal;
	
	//------ Constructors Forest ------//
	
	public Forest()
	{
		this.treesNotInFire = new ArrayList<Tree>();
		this.treesInFire = new ArrayList<Tree>();
		this.treesDead = new ArrayList<Tree>();
	}
	
	public Forest(Pos pos, Size size)
	{
		this();
		this.pos.set(pos);
		this.size.set(size);
	}
	
	//------ Getters/Setters Forest ------//
	
	public ArrayList<Tree> getTreesNotInFire() {return this.treesNotInFire;}
	public ArrayList<Tree> getTreesInFire() {return this.treesInFire;}
	public ArrayList<Tree> getTreesDead() {	return this.treesDead;}

	public int getTreeTotal() {return treeTotal;}
	
	//------ Methods Forest ------//
	
	//generate an all new forest
	public void generate(int treePackNbr, int treeNbr, int area, int precisionTreePack, int precisionTree)
	{
		this.treeTotal = treePackNbr*treeNbr;
		
		this.treesNotInFire.clear();
		this.treesInFire.clear();
		this.treesDead.clear();
		
		ArrayList<Pos> centerPoss = new ArrayList<Pos>();
		
		float areaW = area;
		float areaH = area;
		
		float x = this.pos.x+(area+(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4);
		float y = this.pos.y+(area+(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4);
		float w = this.pos.x+this.size.w-(area+(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4);
		float h = this.pos.y+this.size.h-(area+(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4);
		
		if(x > w)
		{
			x = (this.pos.x+this.size.w)/2;
			w = x;
			areaW = this.size.w/2-(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4;
		}
		
		if(y > h)
		{
			y = (this.pos.y+this.size.h)/2;
			h = y;
			areaH = this.size.h/2-(Firefighter.FIREFIGHTER_h+Firefighter.FIREFIGHTER_w)/4;
		}
		
		if(areaW < 0)
		{
			areaW = 0;
		}
		if(areaH < 0)
		{
			areaH = 0;
		}
		
		for(int j = 0; j < treePackNbr || this.treesNotInFire.size() < this.treeTotal; j++)
		{
			Pos centerPos = new Pos();
			
			boolean isInCollision = true;
			int loopNbr = 0;
			while(isInCollision && loopNbr < precisionTreePack)
			{
				isInCollision = false;
				
				centerPos.setXY(Utils.rand((int)x, (int)w), Utils.rand((int)y, (int)h));
				
				for(int k = 0; k < centerPoss.size() ; k++)
				{
					if(Utils.distanceTwoPos(centerPos, centerPoss.get(k)) < area*2)
					{
						isInCollision = true;
					}
				}
				
				loopNbr++;
			}
			
			centerPoss.add(centerPos);
			
			int treeNbrAlea = treeNbr + Utils.rand(-2, 2);

			for(int i = 0; i < treeNbrAlea && this.treesNotInFire.size() < this.treeTotal; i++)
			{
				int treeType = Utils.rand(1, 100);
				Tree tree = null;
				
				if(treeType <= 5)
				{
					tree = new TreeDry();
				}
				else if(treeType <= 25)
				{
					tree = new TreeLush();
					
					int treeHasKoala = Utils.rand(1, 100);
					if(treeHasKoala <= 5) tree.setKoala(new Koala(new Pos()));
				}
				else if(treeType <= 100)
				{
					tree = new TreeNormal();
				}
				
				isInCollision = true;
				loopNbr = 0;
				
				while(isInCollision && loopNbr < precisionTree)
				{
					isInCollision = false;
			
					tree.pos.setXY(centerPos.x + (Utils.rand(-1*(int)areaW, (int)areaW)), centerPos.y + (Utils.rand(-1*(int)areaH, (int)areaH)));
					
					for(int k = 0; k < this.getTreesNotInFire().size() ; k++)
					{
						if(tree.isInCollision2AABB(this.getTreesNotInFire().get(k)))
						{
							isInCollision = true;
						}
					}
					
					loopNbr++;
				}

				if(tree.getKoala() != null)
				{
					tree.getKoala().pos.setXY(tree.pos.x + Tree.TREE_koalaPosOffset, tree.pos.y + Tree.TREE_koalaPosOffset);
				}
				
				this.getTreesNotInFire().add(tree);
			}
		}
	}
	
	//update [treesNotInFire] of this [Forest]
	private void updateTreesNotInFire(ArrayList <Ember> embers)
	{
		for(int i = 0; i < this.getTreesNotInFire().size(); i++)
		{
			this.getTreesNotInFire().get(i).update(embers);
			
			if(this.getTreesNotInFire().get(i).getIsBurning())
			{
				this.getTreesInFire().add(this.getTreesNotInFire().get(i));
				this.getTreesNotInFire().remove(i);
				i--;	
			}
		}
	
		
		if(this.getTreesInFire().isEmpty() && !this.getTreesNotInFire().isEmpty())
		{
			int choose = Utils.rand(0, this.getTreesNotInFire().size() - 1);
			Tree tree = this.getTreesNotInFire().get(choose);
			
			if(tree.getChanceToTakeFire() != -1)
			{
				this.getTreesNotInFire().remove(choose);
				tree.setIsBurning(true);
				this.getTreesInFire().add(tree);
			}
		}
	}
	
	//update [treesInFire] of this [Forest]
	private float updateTreesInFire(ArrayList <Ember> embers, ArrayList <Water> waters, float score)
	{
		for(int i = 0; i < this.getTreesInFire().size(); i++)
		{
			this.getTreesInFire().get(i).update(embers, waters);
			
			if(!this.getTreesInFire().get(i).getFire().isAlive())
			{
				this.getTreesInFire().get(i).setIsBurning(false);
			}
			
			if(!this.getTreesInFire().get(i).isAlive())
			{
				score += this.getTreesInFire().get(i).deadCost;
				
				if(this.getTreesInFire().get(i).getKoala() != null)
				{
					score += this.getTreesInFire().get(i).getKoala().deadCost;
				}
				
				this.getTreesDead().add(new TreeDead(this.getTreesInFire().get(i)));
				this.getTreesInFire().set(i, null);
				this.getTreesInFire().remove(i); i--;
			}
			else if(!this.getTreesInFire().get(i).getIsBurning())
			{
				this.getTreesNotInFire().add(this.getTreesInFire().get(i));
				this.getTreesInFire().remove(i); i--;
			}
		}
		
		return score;
	}
	
	//update this [Forest]
	public float update(ArrayList <Ember> embers, ArrayList <Water> waters, float score)
	{
		this.updateTreesNotInFire(embers);
		score = this.updateTreesInFire(embers, waters, score);
		
		return score;
	}
	
	//draw this [Forest]
	@Override
	public void draw()
	{
		for(int i = 0; i < this.getTreesNotInFire().size(); i++)
		{
			this.getTreesNotInFire().get(i).draw();
		}
		
		for(int i = 0; i < this.getTreesInFire().size(); i++)
		{
			this.getTreesInFire().get(i).draw();
		}
		
		for(int i = 0; i < this.getTreesDead().size(); i++)
		{
			this.getTreesDead().get(i).draw();
		}
	}
}
