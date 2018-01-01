package clienteAutomatico;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class tiraServer {
	
	public static void main(String[] args) {
		//Se puede usar esta clase para ver si el servidor funciona con determinadas peticiones.
		
		ExecutorService pool = Executors.newCachedThreadPool();
		
		int NUM_PETICIONES = 10;
		
		int i = 0;
		while(i < NUM_PETICIONES)
		{
			pool.execute(new up());
			pool.execute(new down());
			
			i++;
		}
		pool.shutdown();
	}
	
}

