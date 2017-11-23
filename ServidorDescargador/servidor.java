package ServidorDescargador;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class servidor {
	
	public static void main(String[] args) {
		ExecutorService es = null;
		Socket client=null;
		ServerSocket ss = null;

		atenderCliente aux=null;
		
		es=Executors.newCachedThreadPool();
		try {
			ss=new ServerSocket(8080);
			System.out.println("Servidor funcionando");
			while(true) {
			
				try {
					client=ss.accept();
					System.out.println("Cliente atendido");
					aux=new atenderCliente(client);
					aux.start();
					
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			cerrar(ss);
		}

	}
	
	public static void cerrar(Closeable o) {
		try {
			if (o != null) {
				o.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
