package ejemplo;

import java.util.Scanner;
import java.util.Vector;

public class hiloClientes implements Runnable
{
	
	public Vector<Integer> cola;
	int i;
	
	public hiloClientes(Vector<Integer> cola, int i) 
	{
		this.cola = cola;
		this.i = i;
	}
	
	
	@Override
	public void run() 
	{
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Hilo "+ i + " .introduce dato:");
		int x = sc.nextInt();
		
		synchronized (cola) 
		{
			
			cola.addElement(x);
			System.out.println("Hilo "+ i + " elemento añadido");
		}
		
	}

}
