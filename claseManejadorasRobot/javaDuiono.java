import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class javaDuiono {

	private static final String TURN_AMARILLO_OFF = "0";
	private static final String TURN_AMARILLO_ON = "1";
	private static final String TURN_ROJO_OFF = "2";
	private static final String TURN_ROJO_ON = "3";
	
	
	//variables de conexion
	private OutputStream output = null;
	private SerialPort serialPort = null;
	private final String PUERTO = "COM5";
	
	
	private static final int TIMEOUT = 2000;	
	//espera para dar tiempo a que se abra el puerto
	
	
	private static final int DATA_RATE=9600;
	//ratio del puerto en arduino.
	
	public javaDuiono()
	{
		// no hace nada
	}
	
	public void inicializarConexion()
	{
		/*
		 * CODIGO REUTILIZABLE AL USAR JAVA CON ARTUINO
		 * 
		 * */
		
		//identificador del puerto
		CommPortIdentifier puertoID = null;
		
		
		//enumeracion de puertos
		Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();

		
		CommPortIdentifier actualPuertosID;
		
		puertoEnum.nextElement();
		

		
		while(puertoEnum.hasMoreElements())
		{
			//buscamos los puertos
			actualPuertosID = (CommPortIdentifier) puertoEnum.nextElement();
			if(PUERTO.equals(actualPuertosID.getName()))
			{
				
				puertoID = actualPuertosID;
				break;
			}
		}
		
		if(puertoID == null)
		{
			System.out.println("ERROR, NO SE PUEDE CONECTAR AL PUERTO");
		}
		else
		{
			
			try
			{
				serialPort = (SerialPort) puertoID.open(this.getClass().getName(),TIMEOUT);
				
				serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
				
				output = serialPort.getOutputStream();
				
				
				
			}
			catch(PortInUseException e)
			{
				System.out.println("ERROR AL ABRIR EL PUERTO");
				e.printStackTrace();
			}
			catch(UnsupportedCommOperationException e)
			{
				System.out.println("ERROR EN EL SETSERIALPORTPARAMS");
				e.printStackTrace();
			}
			catch(IOException e)
			{
				System.out.println("ERROR AL OBTENER EL OUTPUTSTREAM");
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void enviarDatos(int datos)
	{
		try
		{
			output.write((""+(char)datos).getBytes());
		}
		catch(IOException e)
		{
			System.out.println("ERROR NO SE PUEDEN MANDAR LOS DATOS");
			e.printStackTrace();
		}
		
		
	}

}










