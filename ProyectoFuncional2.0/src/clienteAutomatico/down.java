package clienteAutomatico;
import java.io.IOException;
import java.net.UnknownHostException;

import clienteInterfaz.enviadorCliente;

public class down implements Runnable{
	public down() {}
	public void run()
	{
		try {
						
			
			enviadorCliente out = new enviadorCliente("188.77.49.178", 6666);
			
			for(int i = 1 ; i <= 5 ; i++)
			{
				out.enviarComando("MOVE "+i+" DOWN\r\n");
				out.enviarComando("MOVE "+i+" DOWN\r\n");
				out.enviarComando("MOVE "+i+" DOWN\r\n");
				out.enviarComando("MOVE "+i+" DOWN\r\n");
				out.enviarComando("MOVE "+i+" DOWN\r\n");
				
			}
			
			out.cerrarEnviadorCliente();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
