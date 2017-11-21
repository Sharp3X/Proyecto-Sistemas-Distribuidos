package clienteInterfaz;

import java.io.IOException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class botonUp extends boton
{
	
	public botonUp(Composite parent , int motor,enviadorCliente out) 
	{
		super(parent , motor, out);
		super.getBoton().setText("Up    ");
		super.getBoton().addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				HandlerBotonUp();
			}
		});
	}
	
	
	public void HandlerBotonUp()
	{
		if(super.getEnviador() != null && super.getEnviador().conexionActivada())
		{
			try
			{
				super.getEnviador().enviarComando("MOVE "+super.getMotor()+" UP");
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
