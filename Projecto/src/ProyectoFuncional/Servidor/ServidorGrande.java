package ProyectoFuncional.Servidor;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ProyectoFuncional.ServidorDescargador.servidorAlojaJar;

public class ServidorGrande {
	public static void main(String[] args) {
		System.out.println("Inicio de la actividad en el servidor");
		ServerSocket ss=null;
		Socket s=null;
		
		try {
			//Inicializamos el servidor que servirá para descargar el ejecutable, atiende en el 8080
			Thread des=new Thread(new servidorAlojaJar());
			des.start();
			
			//Inicializamos el ServerSocket
			ss=new ServerSocket(6666);
			
			
			//Inicializamos el robot
			//javaDuiono j = new javaDuiono();
			//j.inicializarConexion();
			ExecutorService pool=Executors.newCachedThreadPool();
			BlockingQueue<Integer> colaMotor=new ArrayBlockingQueue<Integer>(1000);
			
			//Inicializamos el hilo que manda ordenes al brazo
			
			Thread hi=new Thread(new HiloMandaOrdenes(colaMotor/*,j*/));
			hi.start();
			

			try {
				while(true) {
					for(int i=0;i<5;i++) {
						s=ss.accept();
						System.out.println("Cliente "+i+"conectado");
						pool.execute(new HiloCliente(s,colaMotor,i));
					}
					System.out.println("Bloque de 5 hilos creado");
				}
			}
			catch(IOException e) {
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
