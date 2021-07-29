package stopThatFire.gameComponents;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import stopThatFire.game.GameGUI;
import stopThatFire.game.GameGUI.Graphic;
import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Drawable extends Box2D
{
	public final static int DRAWABLE_zindexIntervalH = 1000;
	public final static String DRAWABLE_textureDefault = "none";
	
	private static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	
	private float zindex;
	private String texture;
	
	//------ Constructors Drawable ------//
	
	public Drawable()
	{
		super();
		this.zindex = 0;
		this.texture = DRAWABLE_textureDefault;
	}
	
	public Drawable(Pos pos, Size size, String texture, float zindex)
	{
		super(pos, size);
		this.zindex = zindex;
		this.texture = texture;
	}
		
	//------ Getters/Setters Drawable ------//
	
	public float getZindex() {return this.zindex;}
	public void setZindex(float zindex) {this.zindex = zindex;}
	public float getZ() {return this.zindex*DRAWABLE_zindexIntervalH + this.pos.y;}
	
	public String getTexture() {return this.texture;}
	public void setTexture(String texture) {this.texture = texture;}
	
	public static ArrayList<Drawable> getDrawables() {return drawables;}
	
	//------ Methods Drawable ------//
	
	//partition function of quickSort()
	private static int partition(ArrayList<Drawable> array, int begin, int end) 
	{
	    int pivot = end;

	    int counter = begin;
	    for (int i = begin; i < end; i++) 
	    {
	        if (array.get(i).getZ() <= array.get(pivot).getZ()) 
	        {
	            Drawable temp = array.get(counter);
	            array.set(counter, array.get(i));
	            array.set(i, temp);
	            counter++;
	        }
	    }
	    
	    Drawable temp = array.get(pivot);
	    
	    array.set(pivot, array.get(counter));
	    array.set(counter, temp);

	    return counter;
	}

	//sort [ArrayList<Drawable>] according to element's zIndex
	private static void quickSort(ArrayList<Drawable> array, int begin, int end) 
	{
	    if (end < begin) return;
	    int pivot = partition(array, begin, end);
	    quickSort(array, begin, pivot-1);
	    quickSort(array, pivot+1, end);
	}
	
	//add this [Drawable] to [ArrayList<Drawable>], this list will be used to draw all Drawable when repaint() will be call
	public void draw()
	{
		Drawable.drawables.add(this);
	}

	//apply sort on [ArrayList<Drawable>] according to element's zIndex
	public static void applyZindex()
	{
		quickSort(Drawable.drawables, 0, Drawable.drawables.size()-1);
	}

	//draw this [Drawable] on screen
	public void drawApply(Graphic g, Graphics2D g2d, GameGUI window) 
	{
		Drawable drawable = (Drawable)this;
		AffineTransform transform = new AffineTransform();
	    transform.translate(drawable.getPos().x-drawable.getSize().w/2, drawable.getPos().y-drawable.getSize().h/2);
	    transform.scale(drawable.getSize().w/window.getTextures().get(drawable.getTexture()).getWidth(), drawable.getSize().h/window.getTextures().get(drawable.getTexture()).getHeight());
	    g2d.drawImage(window.getTextures().get(drawable.getTexture()), transform, g);
	}
}
