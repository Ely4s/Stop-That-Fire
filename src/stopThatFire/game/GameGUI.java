package stopThatFire.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import stopThatFire.Main;
import stopThatFire.gameComponents.Drawable;
import stopThatFire.gameComponents.Text;
import stopThatFire.utils.Pos;
import stopThatFire.utils.Utils;

public class GameGUI extends JFrame implements MouseListener, MouseMotionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	
	private Graphic p;
	
	private boolean running = true;
	
	private int w;
	private int h;

	public boolean blockUpdate = true;
	public boolean blockDraw = false;
	
	private String resourcesPath = "Resources/";	
	
	private HashMap<String, BufferedImage> textures;
	
	private int xMouse;
	private int yMouse;
	
	private char keyTyped;
	
	private boolean isKeyTyped = false;
	
	private boolean isMouseClicked = false;
	private boolean isMousePressed = false;
	private boolean isMouseReleased = false;
	private boolean isMouseEventCatched = false;
	
	//------ Constructors GameGUI ------//
	
	public GameGUI(int largeur, int hauteur)
	{
		super("StopThatFire");
		this.initTextures();
		this.initText();
		this.getContentPane().addMouseListener(this);
		this.getContentPane().addMouseMotionListener(this);
		this.addKeyListener(this);

		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {running = false;}
		});
		
		this.w = largeur;
		this.h = hauteur;
		this.setBackground(new Color(133,147,77));
		this.p = new Graphic();
		this.p.window = this;
		this.p.setPreferredSize(new Dimension(this.w, this.h));
		this.getContentPane().add(this.p,BorderLayout.CENTER);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		
		if(Main.isDebugMode) Utils.print("[SUCCESS] Create GameGUI interface \""+this.getTitle()+"\"");
	}
	
	//------ Getters/Setters GameGUI ------//
	
	public boolean getIsRunning() {return this.running;}
	
	public HashMap<String, BufferedImage> getTextures() {return textures;}
	
	public Graphic getP() {return p;}
	public void setP(Graphic p) {this.p = p;}

	public int getW() {return w;}
	public void setW(int w) {this.w = w;}

	public int getH() {return h;}
	public void setH(int h) {this.h = h;}

	public int getXMouse() {return xMouse;}
	public int getYMouse() {	return yMouse;}
	
	//------ Methods GameGUI ------//
	
	//close the window
	public void close()
	{
		System.exit(0);
	}
	
	//init all textures
	private void initTextures()
	{
		textures = new HashMap<String, BufferedImage>();
		
		ArrayList<String> textures = new ArrayList<String>();
		
		textures.add("koala.png");
		textures.add("firefighter.png");
		textures.add("firefighterUnit.png");
		textures.add("firefighterUnitSelected.png");
		textures.add("treeDead.png");
		textures.add("treeDry.png");
		textures.add("treeNormal.png");
		textures.add("treeLush.png");
		textures.add("flame.png");
		textures.add("water.png");
		textures.add("ember.png");
		textures.add("green.png");
		textures.add("red.png");
		textures.add("blue.png");
		
		for(int i = 0; i < textures.size(); i++)
		{
			File file = null;
			BufferedImage img = null;
			
			try 
			{
				file = new File(resourcesPath+textures.get(i));
				img = ImageIO.read(file);
				this.textures.put(textures.get(i), img);
				
				if(Main.isDebugMode) Utils.print("[SUCCESS] Load : "+file.getAbsolutePath());
				
			} catch (IOException e)
			{
				if(Main.isDebugMode) Utils.print("[ERROR  ] Load : "+file.getAbsolutePath());
			}		
		}
	}
	
	//init text
	private void initText()
	{
		Text t = new Text(new Pos(0,0), "", 0, Color.BLACK);
		t.draw();
		this.repaint();
	}
	
	public class Graphic extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private GameGUI window;
		
		@Override
		protected void paintComponent(Graphics gr)
		{		
			super.paintComponent(gr);

			Graphics2D g2 = (Graphics2D)gr;	
		 	
			g2.setColor(new Color(133,147,77));
            	g2.fillRect(0, 0, w, h);
			
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		    g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		    g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		    
		    //draw all [Drawable]
			for(int i = 0; i < Drawable.getDrawables().size(); i++)
			{
				try
				{
					Drawable.getDrawables().get(i).drawApply(this, g2, this.window);
				}
				catch (NullPointerException e){}	
			}
			
			g2.dispose();
		
			Drawable.getDrawables().clear();
		}
	}
	
	//init event
	public void mouseInit()
	{
		if(this.isMouseClicked || this.isKeyTyped)
		{
			isMouseEventCatched=true;
		}
		else
		{
			isMouseEventCatched=false;
		}
		
	}
	
	//reset event
	public void mouseReset()
	{
		if(isMouseEventCatched)
		{
			isMouseClicked = false;
			isMousePressed = false;
			isKeyTyped = false;
		}
		
	}
	
	public boolean isMouseReleased()
	{			
		return this.isMouseReleased;
	}
	
	public boolean isMouseClicked()
	{			
		return this.isMouseClicked;
	}
	
	public boolean isMousePressed()
	{			
		return this.isMousePressed;
	}
	
	public boolean isKeyTyped(String key)
	{			
		if(this.isKeyTyped && keyTyped == key.charAt(0)) return true;
		else return false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		keyTyped = e.getKeyChar();
		isKeyTyped = true;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		isKeyTyped = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		xMouse = e.getPoint().x;
		yMouse = e.getPoint().y;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		xMouse = e.getPoint().x;
		yMouse = e.getPoint().y;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{	
		isMouseClicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		isMousePressed = true;
		isMouseReleased = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		isMousePressed = false;
		isMouseReleased = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}
}
