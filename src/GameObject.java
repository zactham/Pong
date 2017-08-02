import java.awt.Graphics;

public class GameObject 
{

	protected int x = 0;
	protected int y = 0;

	private static final int standardSize = 75;
	private static final int projectileSize = 24;

	private static int gameWidth = 0;
	private static int gameHeight = 0;



	public int getX()
	{
		return x;
	}

	public void setX(int xPos)
	{
		x = xPos;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int yPos)
	{
		y = yPos;
	}

	public static int getStandardSize()
	{
		return standardSize;
	}

	public static int getProjectileSize()
	{
		return projectileSize;
	}

	public static int getGameWidth()
	{
		return gameWidth;
	}

	public static void setGameWidth(int gw)
	{
		gameWidth = gw;
	}

	public static int getGameHeight()
	{
		return gameHeight;
	}

	public static void setGameHeight(int gh)
	{
		gameHeight = gh;
	}





	public void draw (Graphics page)
	{

	}

	public void update()
	{

	}
}
