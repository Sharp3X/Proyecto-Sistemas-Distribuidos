import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

public class HiloCliente implements Runnable {
	// tres parametros, socket, numero de motor, grados

	private Socket s;
	private int nMotor;
	private int grados; // Up down
	private BlockingQueue<Integer> cola;
	int i;

	public int getnMotor() {
		return nMotor;
	}

	public int getGrados() {
		return grados;
	}

	public HiloCliente(Socket s, BlockingQueue<Integer> cola, int i) {
		super();
		this.s = s;
		this.cola = cola;
		this.i = i;

	}

	@Override
	public void run() {
		InputStream is = null;
		DataInputStream dis = null;
		int recibido;

		try {
			is = s.getInputStream();
			dis = new DataInputStream(is);

			// recibe el nMotor, y el numero de grados

			recibido = dis.readInt();
			
				cola.put(recibido);
				System.out.println("Hilo "+ i + " elemento añadido");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Interrupción al añadir");
			e.printStackTrace();
		} finally {
			cerrar(is);
			cerrar(dis);
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
