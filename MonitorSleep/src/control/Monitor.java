package control;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monitor extends Thread {

	private Semaphore S_salaEspera;
	private Monitoria monitoria;
	private Random rdm;

	// Constructor de la clase - Inicializa todos los datos requeridos
	public Monitor(Semaphore salaEspera, Monitoria monitoria) {
		super();
		S_salaEspera = salaEspera;
		this.monitoria = monitoria;
		rdm = new Random();
	}

	// Metodo principal del hilo
	public void run() {
		while (true) {
			try{
				S_salaEspera.acquire(); // Se revisa el estado de la sala
				//En el if se verifica si hay sillas vacias
			    if (monitoria.getNum_sillas_vacias()<3) {
					System.out.println("* [MONITOR] Durmiendo...");
					monitoria.setNum_sillas_vacias(monitoria.getNum_sillas_vacias()+1);//Se desocupa la silla del estudiante que pasa a platicar con el monitor
					System.out.println("* [MONITOR] Despierto, hay un estudiante");
					S_salaEspera.release();
					System.out.println("* [MONITOR] Trabajando ando");
					sleep(rdm.nextInt(10)*1000+1); //Tiempo que dura la asesoria
					System.out.println("* [MONITOR] Regresa cuando lo necesites");
				}else{
					S_salaEspera.release();
				}
			} catch (InterruptedException e) {
			}
		}
	}


}