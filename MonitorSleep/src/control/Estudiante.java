package control;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estudiante extends Thread {

	private Semaphore S_despertarMonitor; // Semaforo del monitor (lo despierta)
	private Semaphore S_salaEspera; // El semaforo especifico que permite acceder a la sala de espera
	private int id; // El id del estudiante
	private Monitoria monitoria;
	private Random rdm;

	// Constructor de la clase. Inicializa todos los datos requeridos
	public Estudiante(Semaphore despertarMonitor, Semaphore salaEspera, int id, Monitoria monitoria) {
		super();
		S_despertarMonitor = despertarMonitor;
		S_salaEspera = salaEspera;
		this.id = id;
		this.monitoria = monitoria;
		rdm = new Random();
	}

	// Metodo principal del hilo
	public void run() {
		// Correr continuamente
		while (true) {
			try {
				sleep(rdm.nextInt(10)*1000+1); //Tiempo de espera aleatorio
				S_salaEspera.acquire(); // Un estudiante llega a la sala de espera
				System.out.println("* [Est:" + id + "] Entrando a la sala de espera...");
				if (monitoria.getNum_sillas_vacias()>0) { // Hay espacio?
					System.out.println("* [Est:"+id+"] ...Hay espacio disponible en la sala");
					monitoria.setNum_sillas_vacias(monitoria.getNum_sillas_vacias()-1);//Resto la silla que fue ocupada por el estudiante que acaba de entrar
					S_salaEspera.release(); // Liberar acceso exclusivo
					System.out.println("* [Est:" + id + "] Esperando su turno para ver al Monitor...");
					S_despertarMonitor.acquire(); // Obtener la atención del monitor al monitor
					System.out.println("* [Est:" + id + "] Platicando con el Monitor");
					S_despertarMonitor.release(); // Se libera al monitor
				} else {
					System.out.println("* [Est:"+id+"] La sala esta llena");
					S_salaEspera.release();
					sleep(rdm.nextInt(10)*1000+1); //Tiempo que el estudiante se va a programar
				}
			} catch (InterruptedException e) {
			}
		}
	}

}
