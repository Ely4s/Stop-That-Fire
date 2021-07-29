package stopThatFire.utils;

public class Utils 
{
	//------ Methods Utils ------//
	
	//System.out.println() alias
	public static void print(Object o)
	{
		System.out.println(o);
	}
	
	//return rand [int] between two [int]
	public static int rand(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	//return rand sign
	public static int randSign()
	{
		if(Utils.rand(0, 1) == 0) return -1;
		else return 1;
	}
	
	//return normalized [Pos] from two [Pos]
	public static Pos getPosNormalizedFromTwoPos(Pos pos1, Pos pos2)
	{
		double radian = Math.atan2(pos2.y - pos1.y, pos2.x - pos1.x);
		double xPlus = Math.cos(radian);
		double yPlus = Math.sin(radian);
		
		return new Pos((float)xPlus, (float)yPlus);
	}
	
	//return radian from two [Pos]
	public static float getRadianFromTwoPos(Pos pos1, Pos pos2)
	{
		return (float)Math.atan2(pos2.y - pos1.y, pos2.x - pos1.x);
	}
	
	//return distance between two [Pos]
	public static float distanceTwoPos(Pos pos1, Pos pos2)
	{
		 return (float) Math.sqrt((pos2.x - pos1.x)*(pos2.x - pos1.x) + (pos2.y - pos1.y)*(pos2.y - pos1.y));
	}
}
