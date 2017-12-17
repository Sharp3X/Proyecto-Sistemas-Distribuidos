package ProyectoFuncional.Servidor;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientedePrueba {
	public static void main(String[] args) {
		Socket s=null;
		OutputStream os=null;
		DataOutputStream dos=null;
		try {
			s=new Socket("localhost",6666);
			os=s.getOutputStream();
			dos=new DataOutputStream(os);
			Scanner sc=new Scanner(System.in);
			int i=sc.nextInt();
			dos.writeInt(i);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			cerrar(s);
			cerrar(os);
			cerrar(dos);
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
