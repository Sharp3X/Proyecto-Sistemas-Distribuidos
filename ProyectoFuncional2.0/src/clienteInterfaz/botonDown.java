package clienteInterfaz;

import java.io.IOException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class botonDown extends boton
{
	
	public botonDown(Composite parent, int motor ,enviadorCliente out) 
	{
		super(parent, motor,out);
		super.getBoton().setText("Down");
		super.getBoton().addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				HandlerBotonDown();
			}
		});
	}
	
	public void HandlerBotonDown()
	{
		if(super.getEnviador() != null && super.getEnviador().conexionActivada())
		{
			try
			{
				super.getEnviador().enviarComando("MOVE "+super.getMotor()+" DOWN\r\n");
			}
			catch(IOException e)
			{
				System.out.println("ERROR al enviar el boton up la instruccion");
				e.printStackTrace();
			}
		}
		else
			System.out.println("Enviador no activado");
		
	}
}