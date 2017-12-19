package httpServicio;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class servidorAlojaJar extends Thread{
	
	public void run() {
		String rutaHome = "./public_html";
		ServerSocket ss = null;
		Socket cliente = null;
		
		ExecutorService pool = Executors.newCachedThreadPool();
		
		try
		{
			ss = new ServerSocket(6680);
			
			while(true)
			{
				try
				{
					
					cliente = ss.accept();
					pool.execute(new atenderCliente(cliente , rutaHome ));
					
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}

			}
			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	
	}

}
