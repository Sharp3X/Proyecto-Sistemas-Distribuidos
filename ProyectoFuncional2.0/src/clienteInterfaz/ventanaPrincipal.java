package clienteInterfaz;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ventanaPrincipal 
{
	public static void main(String[] args) 
	{
		Display monitor = new Display();
		
		Shell shell = new Shell(monitor); //capa
		
		shell.setText("Motor movimientos");
		
		
		TabFolder tabF = new TabFolder(shell, SWT.TOP);//grupo de pestañas. Si queremos que esten arriba, SWT.TOP , y abajo SWT.BOTTOM
		

		enviadorCliente out = null;
		try
		{
//			out = new enviadorCliente("192.168.43.14", 6666);
			//usariamos una ip como esta para poder conectarnos a la ip del servidor.
			//para la entrega del trabajo, usare localhost y que el cliente se conecte a el mismo ordenador.
			
			out = new enviadorCliente("localhost", 6666);
			
			Group gpadre = new Group(tabF, SWT.NONE);
				
			gpadre.setLayout( new GridLayout( 3, false ) );
			gpadre.setText("Motores:");
					
			
			Label lab;
			botonUp boton1UP;
			botonDown boton1DOWN;
				
				
			for(int i = 1 ; i <= 5 ; i++)
			{
				lab = new Label(gpadre, SWT.NONE);
				lab.setText("Motor"+i+": ");	
						
				boton1UP = new botonUp(gpadre, i,out);
				boton1DOWN = new botonDown(gpadre, i,out);
			}
		
				
			gpadre.pack();
		}
		catch(IOException e)
		{
			Group gpadre = new Group(tabF, SWT.NONE);
			
			gpadre.setLayout( new GridLayout( 1, false ) );
			gpadre.setText("ERROR");
			
			Label lab = new Label(gpadre, SWT.NONE); ;
			lab.setText("No se ha podido establecer la conexion con el servidor, intentelo mas tarde");
			
			gpadre.pack();
		}
		
		tabF.pack();
		
		
		shell.pack();
		shell.open();
		
		
		while(!shell.isDisposed())
		{
			if(monitor.readAndDispatch())
			{
				monitor.sleep();
			}
		}
		//libera memoria
		monitor.dispose();
		//!!IMPORTANTE!!
		
		if(out != null)
		{	try
			{
				out.cerrarEnviadorCliente();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
