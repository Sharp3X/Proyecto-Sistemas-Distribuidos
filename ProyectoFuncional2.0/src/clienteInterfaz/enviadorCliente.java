package clienteInterfaz;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class enviadorCliente 
{
	
	private Socket s = null;
	private DataOutputStream out = null;
	
	
	//Hago los throws en la declaracion para el tratamiento posterior en la interfaz de usuario.
	
	public enviadorCliente(String host,int port) throws UnknownHostException, IOException
	{
		//aqui, el cliente se conecta al servidor y manda la string.
			s = new Socket(host, port);
			
			out = new DataOutputStream(s.getOutputStream());	
	}
	
	public boolean conexionActivada()
	{
		if(s != null && out !=null)
			return true;
		
		return false;
	}
	
	public void enviarComando(String datoAMandar) throws IOException
	{
		out.write(datoAMandar.getBytes());
				
		out.flush();
	}
	
	public void cerrarEnviadorCliente() throws IOException
	{
		if(s != null)
			s.close();

		if(out != null)
			out.close();

	}
	
}
