class Sabios {
	static final int N_SABIOS = 7;

	public static void main(String[] string) {
		Sabio sabio;
		Mesa mesa = new Mesa(N_SABIOS);

		Trazador trazador = new Trazador(0, 0);

		for (int i = 0; i < N_SABIOS; i++) {
			sabio = new Sabio(i + 1, mesa);
			sabio.start();
		}
		Parar();
	}

	static private void Parar() {
		Object cerrojo = new Object();
		synchronized (cerrojo) {
			try {
				cerrojo.wait();
			} catch (Exception e) {
			}
		}
	}
}

class Sabio extends Thread {
	private final int T_COMER = 5000;
	private final int T_MEDITAR = 10000;
	private final int T_ESPERAR = 3000;

	private int id;
	private Trazador trazador;
	private Mesa mesa;

	public Sabio(int id, Mesa mesa) {
		this.id = id;
		this.mesa = mesa;
	}

	public void run() {
		Sitio sitio = mesa.Tomar_sitio();
		this.trazador = new Trazador(id, sitio.posicion);
		while (true) {
			trazador.Trazar("Medito");
			Utiles.Esperar(T_MEDITAR);
			while (true) {
				trazador.Trazar("Quiero Comer");
				if (mesa.Coger_palillos(sitio))
					break;

				trazador.Trazar("Espero");
				Utiles.Esperar(T_ESPERAR);
			}
			trazador.Trazar("Como");
			Utiles.Esperar(T_COMER);
			trazador.Trazar("Dejo de comer");
			mesa.Dejar_palillos(sitio);
		}
	}
}

class Sitio {
	public int posicion, izquierda, derecha;
}

class Mesa {
	Boolean palillos[] = new Boolean[Sabios.N_SABIOS];;
	Sitio sitios_mesa[] = new Sitio[Sabios.N_SABIOS];
	int counter;

	public Mesa(int sitios) {
		counter = 0;

		// Inicializacion de la mesa
		for (int i = 0; i < sitios; i++) {
			sitios_mesa[i] = new Sitio();
			sitios_mesa[i].posicion = i;
			sitios_mesa[i].izquierda = i;
			if (i == Sabios.N_SABIOS - 1) {
				sitios_mesa[i].derecha = 0;
			} else {
				sitios_mesa[i].derecha = i + 1;
			}
		}
		// Inicializacion palillos
		for (int i = 0; i < palillos.length; i++) {
			palillos[i] = true;
		}
	}

	public synchronized Sitio Tomar_sitio() {
		sitios_mesa[counter].posicion = counter;
		counter++;
		return sitios_mesa[counter - 1];
	}

	public synchronized Boolean Coger_palillos(Sitio sitio) {
		final Boolean puedes_comer = true;
		final Boolean no_puedes_comer = false;

		if (palillos[sitio.izquierda] && palillos[sitio.derecha]) {
			palillos[sitio.izquierda] = false;
			palillos[sitio.derecha] = false;
			return puedes_comer;
		} else {
			return no_puedes_comer;
		}

	}

	public synchronized void Dejar_palillos(Sitio sitio) {
		palillos[sitio.izquierda] = true;
		palillos[sitio.derecha] = true;
	}
}
