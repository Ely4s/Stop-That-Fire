package stopThatFire.gameComponents;

public class TreeDead extends Tree
{
	public static final String TREEDEAD_texture = "treeDead.png";
	
	//------ Constructors TreeDead ------//
	
	public TreeDead(Tree tree)
	{
		super(tree.pos, 0, 0, 0, 0, 0, 0, 0, 0, TREEDEAD_texture);
	}
}
