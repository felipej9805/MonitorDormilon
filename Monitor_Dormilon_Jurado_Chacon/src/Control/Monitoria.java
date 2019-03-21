package Control;

import java.io.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monitoria {

	private static int num_sillas_vacias;
	private static int numEstudiantes;
	private static Semaphore S_salaEspera;
	private static Semaphore S_despertarMonitor;

	public static int getNum_sillas_vacias() {
		return num_sillas_vacias;
	}

	public static void setNum_sillas_vacias(int num_sillas_vacias) {
		Monitoria.num_sillas_vacias = num_sillas_vacias;
	}
	
	public Monitoria() {
		num_sillas_vacias = 3;
	}


	public static void main(String[] args) throws NumberFormatException, IOException {
		
		Monitoria monitoria = new Monitoria();
		
		System.out.println("Ingresa el numero de estudiantes:");

		BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));

		String num = inConsole.readLine();
		numEstudiantes = Integer.parseInt(num);
		
		// Todos semaforos binarios
		S_despertarMonitor = new Semaphore(1, true);
		S_salaEspera = new Semaphore(1, true);
		// Instanciar los estudiantes
		Estudiante[] estudiantes = new Estudiante[numEstudiantes];
		//for (int i = 0; i < numEstudiantes; i++){
			//estudiantes[i] =new Estudiante(S_despertarMonitor, S_salaEspera, i, monitoria);
			//estudiantes[i].start();
		//}
		// Instanciar el monitor
		Monitor elMonitor = new Monitor(monitoria.S_salaEspera, monitoria);
		//elMonitor.start(); // Iniciar los hilos
		for(int i=0;i<numEstudiantes;i++){
			estudiantes[i] = new Estudiante(S_despertarMonitor, S_salaEspera, i, monitoria);
			estudiantes[i].start();
			if(!elMonitor.isAlive())elMonitor.start();
			while(System.currentTimeMillis()==10000);
			estudiantes[i].interrupt();
		}

	}

}
