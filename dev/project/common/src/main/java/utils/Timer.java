package utils;

public class Timer
{
	
	private long now;

	public Timer()
	{
		this.now = System.currentTimeMillis();
	}

	public void checkpoint()
	{
		System.out.println("Time: " + (System.currentTimeMillis() - now));
		now = System.currentTimeMillis();
	}

}
