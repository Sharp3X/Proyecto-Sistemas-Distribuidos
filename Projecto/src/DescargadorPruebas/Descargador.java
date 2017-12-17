package DescargadorPruebas;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Descargador extends Thread {

	private URL url;
	private String directorio;
	private long nByteInicial;
	private long nByteFinal;
	private String nFichero;
	private CountDownLatch cd;
	private RandomAccessFile raf;
	private InputStream is;
	
	public Descargador(URL url,String dir,long nbini, long nbfin, CountDownLatch c) {
		this.url=url;
		this.directorio=dir;
		this.nByteInicial=nbini;
		this.nByteFinal=nbfin;
		this.nFichero=url.getFile().substring(url.getFile().lastIndexOf("/")+1);
		this.cd=c;
	}

	@Override
	public void run() {
		HttpURLConnection urlc;
		try {
			
			urlc = (HttpURLConnection) url.openConnection();
			urlc.addRequestProperty("Range", "bytes="+nByteInicial+"-"+nByteFinal);
			
			//abro los streams
			is=urlc.getInputStream();
			System.out.println(nFichero);
			raf=new RandomAccessFile(directorio+"/"+nFichero, "rw");
			raf.seek(nByteInicial);
			
			//leo el fichero y lo escribo
			byte [] buff=new byte[1000];
			int leidos=is.read(buff);
			while(leidos!=-1) {
				raf.write(buff,0,leidos);
				leidos=is.read(buff);
			}
			
			
			cd.countDown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			cerrar(raf);
			cerrar(is);
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
