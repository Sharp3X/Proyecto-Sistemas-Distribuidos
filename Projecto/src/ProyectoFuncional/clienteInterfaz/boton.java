package ProyectoFuncional.clienteInterfaz;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class boton 
{

	private int motor;
	private Button boton;
	private enviadorCliente out;
	
	public boton(Composite parent, int motor, enviadorCliente out) 
	{
		this.boton = new Button(parent, SWT.NONE);
		this.motor = motor;
		this.out = out;
		
	}
	public int getMotor() {return this.motor;}
	public Button getBoton() {return this.boton;}
	public enviadorCliente getEnviador() {return this.out;}
}
