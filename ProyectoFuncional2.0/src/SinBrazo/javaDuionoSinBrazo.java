package SinBrazo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class javaDuionoSinBrazo {

	private static final String TURN_AMARILLO_OFF = "0";
	private static final String TURN_AMARILLO_ON = "1";
	private static final String TURN_ROJO_OFF = "2";
	private static final String TURN_ROJO_ON = "3";
	
	
	//variables de conexion
	private OutputStream output = null;
	private SerialPort serialPort = null;
	private final String PUERTO = "COM4";
	
	
	private static final int TIMEOUT = 2000;	
	//espera para dar tiempo a que se abra el puerto
	
	
	private static final int DATA_RATE=9600;
	//ratio del puerto en arduino.
	
	public javaDuionoSinBrazo()
	{
		// no hace nada
	}
	
	public void inicializarConexion()
	{
		System.out.println("Conexión con brazo iniciada");	
		
	}
	
	public void enviarDatos(String orden)
	{
			System.out.println(orden);		
	}

}










