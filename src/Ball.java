import java.awt.Graphics;

public class Ball 
{
	private int ballX = 500;
	private int ballY = 500;
	private int ballSize = 20;

	public int getBallX()
	{
		return ballX;
	}

	public void setBallX(int x)
	{
		ballX = x;
	}


	public int getBallY()
	{
		return ballY;
	}

	public void setBallY(int y)
	{
		ballY = y;
	}
	
	public int getBallSize()
	{
		return ballSize;
	}

	public void draw (Graphics page)
	{
		page.fillRect(getBallX(), getBallY(), getBallSize(), getBallSize());
	}
}
