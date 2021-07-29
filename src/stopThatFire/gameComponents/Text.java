package stopThatFire.gameComponents;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import stopThatFire.game.GameGUI;
import stopThatFire.game.GameGUI.Graphic;
import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class Text extends Drawable
{
	public static final float TEXT_zindex = 20;
	private String str;
	private float fontSize;
	private Color color;
		
	//------ Constructors Text ------//
	
	public Text(Pos pos, String str, float fontSize, Color color)
	{
		super(pos, new Size(), DRAWABLE_textureDefault, TEXT_zindex);
		this.setStr(str);
		this.fontSize = fontSize;
		this.color = color;
	}

	//------ Getters/Setters Text ------//
	
	public String getStr() {return str;}
	public void setStr(String str) {this.str = str;}
	
	public float getFontSize() {return fontSize;}
	public void setFontSize(float fontSize) {this.fontSize = fontSize;}

	public Size getSize() {	return size;}
	public void setSize(Size size) {	this.size = size;}

	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
	
	//------ Methods Text ------//
	
	//draw this [Text] on screen
	@Override
	public void drawApply(Graphic g, Graphics2D g2d, GameGUI window) 
	{
		Text drawable = (Text)this;
		g2d.setFont(g2d.getFont().deriveFont(drawable.getFontSize()));
		g2d.setColor(drawable.getColor());
		FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
		g2d.drawString(drawable.getStr(), drawable.getPos().x, drawable.getPos().y + metrics.getAscent());
	}
}
