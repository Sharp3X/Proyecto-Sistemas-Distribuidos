package ejemplo;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {

	public static void main(String[] args) 
	{
		try
		{
			ExecutorService pool = Executors.newCachedThreadPool();
			
			Vector<Integer> cola = new Vector<>();
			
			pool.execute(new hiloRobot(cola));
			System.out.println("hilo robot ejecutando");
			
			for(int i = 0 ; i < 10 ; i++)
			{
				
				pool.execute(new hiloClientes(cola,i));
				
				Thread.sleep(3000);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
