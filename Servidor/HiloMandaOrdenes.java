import java.util.concurrent.BlockingQueue;

import claseManejadorasRobot.javaDuiono;

public class HiloMandaOrdenes implements Runnable{
	private BlockingQueue cola;
	//private javaDuiono j;
	
	public HiloMandaOrdenes(BlockingQueue cola/*,javaDuiono j*/) {
		this.cola=cola;
		//this.j=j;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized (cola) {
				try {
					//j.enviarDatos((int)this.cola.take());
					System.out.println(this.cola.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
