package stopThatFire.utils;

import stopThatFire.Main;

public class FrameRate
{
	private static final float FRAMERATE_fpsShowColdDownInitDefault = 10;
    private long timeThen;
    private boolean newVersion = true;
    private float fpsShowColdDown;
    private long fpsTotal;
    private long fpsNbr;
    private int fps;
    
  //------ Constructors FrameRate ------//
    
    public FrameRate(int fps)
    {
    		this.fps = fps;
        if (newVersion) timeThen = System.nanoTime();
        else timeThen = System.currentTimeMillis();
    }
    
    //------ Methods FrameRate ------//
    
    //wait the time required to reach the desired FPS
    public void sync()
    {
        Thread.yield();
        
        long timeNow = 0;
        
        if (newVersion)
        {
            long gapTo = 1000000000L / this.fps + timeThen;
            timeNow = System.nanoTime();
            
            try
            {
                while (gapTo > timeNow)
                {
	                	try
	                	{
	                		Thread.sleep((gapTo-timeNow) / 2000000L);
	                	} 
	                	catch (InterruptedException e) {}
                	
	                	timeNow = System.nanoTime();
                }
            }
            catch (Exception e) {}
            
        }
        else
        {
        		long gapTo = 1000L / fps + timeThen;
            timeNow = System.currentTimeMillis();
            
            while (gapTo > timeNow)
            {
                try 
                { 
                		Thread.sleep(1);
                } 
                catch (InterruptedException e) {}
                
                timeNow = System.currentTimeMillis();
            }
            
        }
        
        if(Main.isDebugMode_fps)
        {
	        	int fps_reached = 0;
	            if(newVersion) fps_reached = Math.round(1/((timeNow-timeThen)/1000000000f));
	            else fps_reached = Math.round(1/((timeNow-timeThen)/1000f));
	            
	        		this.fpsTotal += fps_reached;
	        		this.fpsNbr++;
	        		
	    		if(fpsShowColdDown <= 0)
	    		{
	    			Utils.print(this.fpsTotal/this.fpsNbr+" FPS");
	    			this.fpsTotal = 0;
	    			this.fpsNbr = 0;
	    			
	    			fpsShowColdDown = FRAMERATE_fpsShowColdDownInitDefault;
	    		}
	    		else
	    		{
	    			fpsShowColdDown -= 1;
	    		}
        }
        
		timeThen = timeNow;
    }
}
