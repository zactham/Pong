public class MyTimer 
{
	private long start = 0;
	private long delay;

	public MyTimer(int delay)
	{
		this.delay = delay;
	}

	public void start() 
	{
		this.start = System.currentTimeMillis();
	}

	public boolean isExpired() 
	{
		return ((System.currentTimeMillis() - this.start) > this.delay);
	}
	
	public void setDelay(long d)
	{
		delay = d;
	}

	public long getDelay()
	{
		return delay;
	}
}


