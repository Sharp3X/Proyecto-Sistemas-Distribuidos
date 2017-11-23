package DescargadorPruebas;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ej1 {

	
	public static void main(String[] args) {
		URL url=null;
		HttpURLConnection con=null;
		ExecutorService es=null;
		String recurso=" http://localhost:8080/descargador.java";//meter recurso aqui
		String directorio="recursos";
		CountDownLatch cd=null;
		try {
			url = new URL(recurso);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("HEAD");
			long tamano = con.getContentLengthLong();
			
			//tamaños a usar
			long t1=tamano/3;
			long t2=(tamano/3)*2;
			
			//creo los hijos
			cd=new CountDownLatch(3);
			es=Executors.newFixedThreadPool(3);

			Descargador d1=new Descargador(url, directorio, 0, t1-1, cd);
			Descargador d2=new Descargador(url, directorio, t1, t2, cd);
			Descargador d3=new Descargador(url, directorio, t2, tamano-1, cd);
			
			es.execute(d1);
			es.execute(d2);
			es.execute(d3);
			
			
			
			//espero a que acaben
			cd.await();
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			es.shutdown();
		}
		
		

		
	}
	

}
