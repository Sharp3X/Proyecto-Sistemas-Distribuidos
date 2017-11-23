package ProyectoFuncional.servidor;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class HiloCliente implements Runnable {

	private Socket s;
	private BlockingQueue<String> cola;
	int i;



	public HiloCliente(Socket s, BlockingQueue<String> cola, int i) {
		super();
		this.s = s;
		this.cola = cola;
		this.i = i;

	}

	@Override
	public void run() {
		DataInputStream dis = null;
		String mensaje;

		try {
			dis = new DataInputStream(s.getInputStream());

			// recibe el nMotor, y el numero de grados
			while(true)
			{
				mensaje = dis.readLine();
				cola.put(mensaje);
			}
			
			
			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Interrupción al añadir");
			e.printStackTrace();
		} finally {
			cerrar(dis);
			cerrar(s);
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