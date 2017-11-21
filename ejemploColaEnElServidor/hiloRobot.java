package ejemplo;

import java.util.Vector;

public class hiloRobot implements Runnable
{
	
	public Vector<Integer> cola;
	
	public hiloRobot(Vector<Integer> cola)
	{
		this.cola = cola;
	}


	public void run() 
	{
		while(true)
		{
			if(cola.size()!=0)
			{
				synchronized(this)
				{
					System.out.println("Elimino dato "+cola.get(0));
					cola.remove(0);
				}
			}
		}
	}
	
}
