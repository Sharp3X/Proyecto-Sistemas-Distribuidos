import java.util.Scanner;

public class principal {

	public static void main(String[] args) 
	{
			javaDuiono j = new javaDuiono();
			
			j.inicializarConexion();
			
			Scanner sc = new Scanner(System.in);
			
			
			for(int i = 0 ; i < 100 ; i++)
			{
				System.out.println("introduce entero:");
				j.enviarDatos(sc.nextInt());
			}


		
		
	}

}
