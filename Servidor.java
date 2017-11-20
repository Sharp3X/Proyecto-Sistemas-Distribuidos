import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[] args) {
		System.out.println("Inicio de la actividad en el servidor");
		ServerSocket ss=null;
		Socket s=null;
		
		try {
			ss=new ServerSocket(6666);
			//Inicializamos el robot
			javaDuiono j = new javaDuiono();
			j.inicializarConexion();
			ExecutorService pool=Executors.newFixedThreadPool(5);
			final BlockingQueue<Integer> colaMotor=new ArrayBlockingQueue<Integer>(1000);

			try {
				while(true) {
					for(int i=0;i<5;i++) {
						s=ss.accept();
						System.out.println("Cliente "+i+"conectado");
						pool.execute(new HiloCliente(s,colaMotor,i));
					}
					//En bloques de 5 clientes, se cargan las instrucciones en la cola
					
					//Aquí las podemos tratar, y mandarlas al robot, pero necesitamos una forma de esperar a todos
					for(int i=0;i<5;i++) {
						j.enviarDatos(colaMotor.take());
						
					}
					
				}
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e) {
				System.out.println("Problema con el accept");
				e.printStackTrace();
			} 
		}catch(IOException e1) {
			System.out.println("Problema con el ServerSocket");
			e1.printStackTrace();
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

//for (int i=0;i<5;i++) {
	
	
	
//	s=ss.accept();
//	System.out.println("Cliente 1 conectado");
//	th1 =new Hilo(s);
//	pool.execute(th1);
//	
//	s=ss.accept();
//	System.out.println("Cliente 2 conectado");
//	th2 =new Hilo(s);
//	pool.execute(th2);
//	
//	s=ss.accept();
//	System.out.println("Cliente 3 conectado");
//	th3 =new Hilo(s);
//	pool.execute(th3);
//	
//	s=ss.accept();
//	System.out.println("Cliente 4 conectado");
//	th4 =new Hilo(s);
//	pool.execute(th4);
//	
//	s=ss.accept();
//	System.out.println("Cliente 5 conectado");
//	th5 =new Hilo(s);
//	pool.execute(th5);
//}
