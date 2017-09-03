import java.awt.Graphics;

public class Paddle
{
	private int paddleX = 60;
	private int paddleY = 900;

	private int paddleWidth = 30;
	private int paddleHeight = 80;

	public int getPaddleX()
	{
		return paddleX;
	}

	public void setPaddleX(int x)
	{
		paddleX = x;
	}

	public int getPaddleWidth()
	{
		return paddleWidth;
	}

	public int getPaddleHeight()
	{
		return paddleHeight;
	}


	public int getPaddleY()
	{
		return paddleY;
	}

	public void setPaddleY(int y)
	{
		paddleY = y;
	}

	public void draw (Graphics page)
	{
		page.fillRect(getPaddleX(), getPaddleY(), getPaddleWidth(), getPaddleHeight());


	}

}
