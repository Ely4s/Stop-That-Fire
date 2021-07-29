package stopThatFire.gameComponents;

import stopThatFire.utils.Pos;
import stopThatFire.utils.Size;

public class LifeBar extends Drawable
{
	public static final int LIFEBAR_h = 4;
	public static final int LIFEBAR_yOffset = 5;
	public static final int LIFEBAR_zindex = 10;
	public static final String LIFEBAR_textureLife = "green.png";
	public static final String LIFEBAR_textureDamage = "red.png";
	
	Drawable lifeBar;
	Drawable damageBar;
	
	//------ Constructors LifeBar ------//
	
	public LifeBar(EntityLiving el)
	{
		float lifeBarW = el.size.w*(el.life/el.lifeMax);
		float xOffset = -0.5f*(el.size.w - lifeBarW);
		this.lifeBar = new Drawable(new Pos(el.pos.x+xOffset, el.pos.y-(el.size.h/2)-LIFEBAR_yOffset), new Size(lifeBarW, LIFEBAR_h), LIFEBAR_textureLife, LIFEBAR_zindex);
		
		float damageBarW = el.size.w - lifeBarW;
		this.damageBar = new Drawable(new Pos(lifeBar.getPos().x + lifeBar.getSize().w/2 + damageBarW/2, lifeBar.getPos().y), new Size(damageBarW, lifeBar.getSize().h), LIFEBAR_textureDamage, LIFEBAR_zindex);
	}

	//------ Methods Box2D ------//
	
	//draw this [LifeBar]
	public void draw()
	{
		this.lifeBar.draw();
		this.damageBar.draw();
	}
}
