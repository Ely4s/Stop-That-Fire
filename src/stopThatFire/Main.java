package stopThatFire;

import java.awt.Color;
import java.util.ArrayList;

import stopThatFire.game.GameGUI;
import stopThatFire.gameComponents.Drawable;
import stopThatFire.gameComponents.Ember;
import stopThatFire.gameComponents.FireStation;
import stopThatFire.gameComponents.Forest;
import stopThatFire.gameComponents.Text;
import stopThatFire.gameComponents.Water;
import stopThatFire.utils.FrameRate;
import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Main 
{
	public static final boolean isDebugMode = false;
	public static final boolean isDebugMode_fps = false;
	
	//check for game reinit
	public static float checkForReInit(FireStation fireStation, Forest forest, ArrayList<Water> waters, ArrayList<Ember> embers, GameGUI window, float score)
	{
		if(window.isKeyTyped("r"))
		{
			return init(fireStation, forest, waters, embers, window);
		}
		else
		{
			return score;
		}
	}
	
	//game init
	public static float init(FireStation fireStation, Forest forest, ArrayList<Water> waters, ArrayList<Ember> embers, GameGUI window)
	{
		for(int i = 0; i < fireStation.getFirefighterUnits().size(); i++) {fireStation.getFirefighterUnits().remove(i); i--;}
		fireStation.addFirefighterUnit(new Pos(330,640));
		fireStation.addFirefighterUnit(new Pos(510,640));
		fireStation.addFirefighterUnit(new Pos(690,640));
		
	    forest.generate(75,5,32,30,30);

	    waters.clear();
	    embers.clear();
	   
		return 0;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException 
	{	
		//INIT
		
		GameGUI window = new GameGUI(1024, 720);
		FrameRate frameRate = new FrameRate(120);
		
		FireStation fireStation = new FireStation();
		Forest forest = new Forest(new Pos(0,0), new Size(window.getW(),window.getH()*(8f/10f)));
	    ArrayList<Water> waters = new ArrayList<Water>();
	    ArrayList<Ember> embers = new ArrayList<Ember>();
	    
	    float score = Main.init(fireStation, forest, waters, embers, window);
	    
	    Text textScore = new Text(new Pos(5, window.getH()-62), "", 15, Color.red);
  		Text textInFire = new Text(new Pos(5, window.getH()-42), "", 15, Color.black);
  		Text textAlive = new Text(new Pos(5, window.getH()-22), "", 15, Color.black);
  		Text textRestart = new Text(new Pos(window.getW()-125, window.getH()-22), "Press \"R\" to restart", 13, Color.black);
	  		
		while(window.getIsRunning())
		{	
			//UPDATE			
			
			window.mouseInit();
			
			score = Main.checkForReInit(fireStation, forest, waters, embers, window, score);
			
			score = fireStation.update(waters, forest, embers, true, true, window.isMouseClicked(), window.getXMouse(), window.getYMouse(), score);
			score = forest.update(embers, waters, score);
			
			Water.update(waters);
			Ember.update(embers);
			
			textScore.setStr("Score : "+Math.round(score));
			textInFire.setStr("Trees in fire: "+Integer.toString(forest.getTreesInFire().size())+"/"+Integer.toString(forest.getTreesNotInFire().size()+forest.getTreesInFire().size()));
			textAlive.setStr("Remaining trees : "+Integer.toString(forest.getTreesNotInFire().size()+forest.getTreesInFire().size())+"/"+Integer.toString((forest.getTreesNotInFire().size()+forest.getTreesInFire().size())+forest.getTreesDead().size()));
			
			window.mouseReset();

			//DRAW
	
			if(Drawable.getDrawables().isEmpty())
			{
				fireStation.draw();
				forest.draw();
				Water.draw(waters);
				Ember.draw(embers);
				textScore.draw();
				textInFire.draw();
				textAlive.draw();
				textRestart.draw();
				
				Drawable.applyZindex();
				
				window.repaint();
			}
			
			//WAIT
			
			frameRate.sync();
		}
		
		window.close();
	}
}