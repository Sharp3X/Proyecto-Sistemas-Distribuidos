package SinBrazo;


import java.util.concurrent.BlockingQueue;

import claseManejadorasRobot.javaDuiono;

public class HiloMandaOrdenesSinBrazo implements Runnable{
	private BlockingQueue<String> cola;
	private memoriaMotoresSinBrazo m = null;
	
	public HiloMandaOrdenesSinBrazo(BlockingQueue<String> cola) {
		this.cola=cola;
		m = new memoriaMotoresSinBrazo();
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized (cola) {
				try 
				{
					Thread.sleep(20);//para que le de tiempo al motor a mover la posicion
					m.moverMotor(this.cola.take());
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
	}

}
