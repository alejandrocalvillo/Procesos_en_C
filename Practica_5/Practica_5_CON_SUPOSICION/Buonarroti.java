import java.time.Instant;
import java.util.concurrent.Semaphore;

class Galeria { // Semaforos aqui
	public static final int TAM_GRUPO = 5;
	public static final int T_VISITA = 10000;
	public static final int T_APARICION = 2000;

	public static Instant inicio;
	public static Semaphore entrada = new Semaphore(TAM_GRUPO);
	public static Semaphore torno = new Semaphore(0);

	public static void Acquire_entrada() {
		try {
			entrada.acquire();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void Release_entrada() {
		entrada.release();
	}

	public static void Acquire_torno() {
		try {
			torno.acquire();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void Release_torno() {
		for (int i = 0; i < TAM_GRUPO; i++) {
			torno.release();
		}

	}
}

public class Buonarroti {
	public static void main(String[] args) {
		Visitante visitantes;
		int counter = 0;
		int grupo = 0;// Grupo de 5 formado

		while (true) {// llegada
			Utiles.Esperar(Galeria.T_APARICION);
			visitantes = new Visitante(counter + 1, grupo);
			if ((counter + 1) % Galeria.TAM_GRUPO == 0) {
				grupo++;
			}
			visitantes.start();
			counter++;
		}
	}
}

class Visitante extends Thread {

	int id = 0;
	int grupo = 0;
	Trazador trazar;

	public Visitante(int id, int grupo) {
		this.id = id;
		this.grupo = grupo;
		trazar = new Trazador(id, 0);
	}

	public void run() {
		Boolean Se_ha_formado_grupo = Entrada_de_la_sala();
		Entrar_en_la_Galeria(Se_ha_formado_grupo);
		Recorrer();
		Salir();
	}

	public Boolean Entrada_de_la_sala() { // Sala de espera MAX TAM GRUPO
		Boolean entrada = false;
		trazar.Trazar("Quiero entrar");
		Utiles.Esperar(Galeria.T_APARICION);
		Galeria.Acquire_entrada();
		entrada = true;
		return entrada;
	}

	public void Entrar_en_la_Galeria(Boolean entrada) {
		if (entrada == true) {
			trazar.Crear_tabs(1);
			trazar.Trazar("He entrado en la Sala");
			if (grupo > 0) {
				if (Salidas.Pasar_Galeria()) {
					Galeria.Release_torno();
				}
				Galeria.Acquire_torno();
			}
		}
	}

	public void Recorrer() {
		synchronized (this) {
			trazar.Crear_tabs(2);
			trazar.Trazar("Estoy recorriendo la galeria");
			Utiles.Esperar(Galeria.T_VISITA);
		}
	}

	public void Salir() {
		trazar.Crear_tabs(3);
		trazar.Trazar("Salgo de la sala");
		Salidas.Contar_Salidas(this.id);
	}
}

class Salidas {
	private static final Boolean GRUPO_TERMINADO = true;
	private static final Boolean GRUPO_NO_TERMINADO = false;

	private static int counter = 0;
	private static Trazador trazar;

	static synchronized void Contar_Salidas(int id) {
		Galeria.Release_entrada();
		counter++;
		if (counter == Galeria.TAM_GRUPO) {
			trazar = new Trazador(id, 6);
			trazar.Trazar("Sale el último");
		}
	}

	static Boolean Pasar_Galeria() {
		if (counter == Galeria.TAM_GRUPO) {
			counter = 0;
			return GRUPO_TERMINADO;
		} else {
			return GRUPO_NO_TERMINADO;
		}
	}
}
