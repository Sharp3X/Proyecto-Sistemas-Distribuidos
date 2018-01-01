package SinBrazo;

import java.io.IOException;

public class javaDuionoSinBrazo {
	//esta clase realiza la accion de mostrar por pantalla las ordenes 
	//que deberían lanzarse al brazo mecanico.
	
	//para ello se necesitan ciertas librerias y conexion con el puerto 
	// correspondiente que use el ordenador para conectarse con el brazo.
	// Para ver si funciona sin el brazo, se necesita usar esta clase.
	
	public javaDuionoSinBrazo()
	{
		// no hace nada
	}
	
	public void inicializarConexion()
	{
		System.out.println("Conexión con brazo iniciada");	
		
	}
	
	public void enviarDatos(int datos)
	{
		System.out.println("Se envia por el puerto serie: "+datos);
	}

}










