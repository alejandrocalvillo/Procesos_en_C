import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

class Configuracion {
	public static final int N_TARTAS = 5;
	public static final int N_AMASORES = 1;
	public static final int N_HORNEADORES = 1;
	public static final int N_FRESADORES = 1;
	public static final int N_NATEADORES = 1;
	public static final int N_TOPPERS = 1;
	public static Instant inicio;

	public static void Iniciar() {
		inicio = Instant.now();
	}

	public static Duration Duracion() {
		Instant ahora = Instant.now();
		return (Duration.between(Configuracion.inicio, ahora));
	}
}

class Tartas {

	public static void main(String[] args) {
		int op;
		Trazador trazador = new Trazador(0, 0);
		Configuracion.Iniciar();
		ArrayList<Operario> operarios = Crear_operarios();
		Arrancar(operarios);
		Esperar_fin(operarios);
	}

	private static ArrayList<Operario> Crear_operarios() {
		int op = 0;
		Bandeja entrada, salida;
		ArrayList<Operario> operarios = new ArrayList<>();
		entrada = new Bandeja(Configuracion.N_TARTAS);
		salida = new Bandeja(0);
		for (int i = 0; i < Configuracion.N_AMASORES; i++, op++)
			operarios.add(new Amasador(op, 1, entrada, salida));
		entrada = salida;
		salida = new Bandeja(0);
		for (int i = 0; i < Configuracion.N_HORNEADORES; i++, op++)
			operarios.add(new Horneador(op, 2, entrada, salida));
		entrada = salida;
		salida = new Bandeja(0);
		for (int i = 0; i < Configuracion.N_FRESADORES; i++, op++)
			operarios.add(new Fresador(op, 3, entrada, salida));
		entrada = salida;
		salida = new Bandeja(0);
		for (int i = 0; i < Configuracion.N_NATEADORES; i++, op++)
			operarios.add(new Nateador(op, 4, entrada, salida));
		entrada = salida;
		salida = new Bandeja(0);
		for (int i = 0; i < Configuracion.N_TOPPERS; i++, op++)
			operarios.add(new Topeador(op, 5, entrada, salida));
		return (operarios);
	}

	private static void Arrancar(ArrayList<Operario> operarios) {
		for (Operario operario : operarios)
			operario.start();
	}

	private static void Esperar_fin(ArrayList<Operario> operarios) {
		for (Operario operario : operarios)
			operario.Esperar_fin();
	}
}

class Bandeja {
	int contador = 0;

	public Bandeja() {
		contador = 0;
	}

	public Bandeja(int n) {
		contador = n;
	}

	public void Espero() {
		synchronized (this) {
			try {
				this.wait();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void Aviso() {
		synchronized (this) {
			this.notifyAll();
		}
	}

	public synchronized void Poner() {
		contador++;
		Aviso();
	}

	public synchronized Boolean Coger() {
		if (contador > 0) {
			contador--;
			return (true);
		}
		if (contador == 0) {
			Espero();
		}
		return (false);
	}
}

abstract class Operario extends Thread {
	protected int id;
	protected Bandeja entrada, salida;
	protected Trazador trazador;

	public abstract int Tartas_procesadas();

	public abstract void Incrementar_contador_de_tartas();

	public abstract void Manipular();

	public Operario(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		this.id = id;
		this.entrada = entrada;
		this.salida = salida;
		trazador = new Trazador(id, ntabs);
		trazador.Trazar(this.getClass().getName());
	}

	public void run() {
		trazador.Trazar("RUN");
		while (Tartas_procesadas() < Configuracion.N_TARTAS) {
			if (entrada.Coger()) {
				trazador.Trazar("Hay");
				Incrementar_contador_de_tartas();
				Manipular();
				salida.Poner();
				trazador.Trazar("Procesadas", Tartas_procesadas());
			} else {
				trazador.Trazar("no hay");
				Utiles.Esperar(1000);
			}
		}
	}

	public void Esperar_fin() {
		try {
			join();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class Amasador extends Operario {
	static int n;

	synchronized static int Procesadas() {
		return n;
	}

	synchronized static void Incrementar() {
		n++;
	}

	public Amasador(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		super(id, ntabs, entrada, salida);
	}

	public int Tartas_procesadas() {
		return Procesadas();
	}

	public void Incrementar_contador_de_tartas() {
		Incrementar();
	}

	public void Manipular() {
		Utiles.Esperar(1000, 2000);
	}
}

class Horneador extends Operario {
	static int n;

	synchronized static int Procesadas() {
		return n;
	}

	synchronized static void Incrementar() {
		n++;
	}

	public Horneador(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		super(id, ntabs, entrada, salida);
	}

	public int Tartas_procesadas() {
		return (Procesadas());
	}

	public void Incrementar_contador_de_tartas() {
		Incrementar();
	}

	public void Manipular() {
		Utiles.Esperar(3000, 7000);
	}
}

class Fresador extends Operario {
	static int n;

	synchronized static int Procesadas() {
		return n;
	}

	synchronized static void Incrementar() {
		n++;
	}

	public Fresador(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		super(id, ntabs, entrada, salida);
	}

	public int Tartas_procesadas() {
		return (Procesadas());
	}

	public void Incrementar_contador_de_tartas() {
		Incrementar();
	}

	public void Manipular() {
		Utiles.Esperar(5000, 6000);
	}
}

class Nateador extends Operario {
	static int n;

	synchronized static int Procesadas() {
		return n;
	}

	synchronized static void Incrementar() {
		n++;
	}

	public Nateador(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		super(id, ntabs, entrada, salida);
	}

	public int Tartas_procesadas() {
		return (Procesadas());
	}

	public void Incrementar_contador_de_tartas() {
		Incrementar();
	}

	public void Manipular() {
		Utiles.Esperar(2000, 4000);
	}
}

class Topeador extends Operario {
	static int n;

	synchronized static int Procesadas() {
		return n;
	}

	synchronized static void Incrementar() {
		n++;
	}

	public Topeador(int id, int ntabs, Bandeja entrada, Bandeja salida) {
		super(id, ntabs, entrada, salida);
	}

	public int Tartas_procesadas() {
		return (Procesadas());
	}

	public void Incrementar_contador_de_tartas() {
		Incrementar();
		Integer n = Procesadas();
		Instant ahora = Instant.now();
		Duration duracion = Duration.between(Configuracion.inicio, ahora);
		Long milis = duracion.toMillis();
		Double dur = milis.doubleValue() / 60000;
		Double efec = n.doubleValue() / dur;
		System.out.println(String.format("Tartas %d, Tiempo %f min, Eficiencia %f", n, dur, efec));
	}

	public void Manipular() {
		Utiles.Esperar(1000, 3000);
	}
}
