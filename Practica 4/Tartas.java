
public class Tartas {
	final static String[] operarios = new String[]{"Amasador", "Horneador", "Fresador", "Nateador", "Topeador"};

	public static void main(String[] args)
		{
		int salida = 0;
		Amasador amasador = new Amasador(operarios[0], 1, 0);
		Horneador horneador = new Horneador(operarios[1], 2, 1);
		Fresador fresador = new Fresador(operarios[2], 3, 2);
		Nateador nateador = new Nateador(operarios[3], 3, 3);
		Topeador topeador = new Topeador(operarios[4], 4, 4);
		amasador.start();
		horneador.start();
		fresador.start();
		nateador.start();
		topeador.start();
		while(salida != 1)
			{
			if(amasador.Get_terminado()==1)
				{
				horneador.Set_anterior_terminado(1);
				}
			if(horneador.Get_terminado()==1)
				{
				fresador.Set_anterior_terminado(1);
				}
			if(fresador.Get_terminado()==1)
				{
				nateador.Set_anterior_terminado(1);
				}
			if(nateador.Get_terminado()==1)
				{
				topeador.Set_anterior_terminado(1);
				}
			if(topeador.Get_procesadas()==9)
				{
				salida=1;
				}
			}
		Esperar_por(amasador);
		Esperar_por(horneador);
		Esperar_por(fresador);
		Esperar_por(nateador);
		Esperar_por(topeador);
		System.out.println("Final");
		}

	public static void Esperar_por(Thread hilo)
        {
        try
            {
            hilo.join();
        }
        catch (Exception e)
            {
            System.err.println(e);
            }
        }

}
class Amasador extends Thread
	{
	private String name;
	private Trazador trazador;
	private int tiempo_inicial = 1000; //miliseconds
	private int tiempo_final = 2000;
	private int terminado= 0;
	private int procesadas;
	public Amasador(String name, int id, int ntabs)
		{
		this.name=name;
		procesadas=0;
		terminado=0;
		trazador = new Trazador(id, ntabs);
		}
	public String Get_Name()
		{
		return this.name;
		}
	public int Get_terminado()
		{
		return this.terminado;
		}

	public void run()
		{
		while(procesadas<10)
			{		
			if (procesadas > 0)
				{
				trazador.Trazar("Procesadas", procesadas);
				trazador.Trazar("RUN");
				trazador.Trazar("Hay");
				terminado= Trabajo.Trabajando(tiempo_inicial,tiempo_final);
				procesadas++;
				}
			if (procesadas == 0)
				{
				trazador.Trazar(name);
				Utiles.Esperar(1000);
				procesadas++;
				}	
			}
		}
	}
class Horneador extends Thread
	{
	private String name;
	private Trazador trazador;
	private int tiempo_inicial = 3000; //miliseconds
	private int tiempo_final = 7000;
	private int procesadas;
	private int terminado;
	private int anterior_terminado;
	public Horneador(String name, int id, int ntabs)
		{
		this.name=name;
		terminado = 0;
		trazador = new Trazador(id, ntabs);
		}
	public String Get_Name()
		{
		return this.name;
		}
	public void run()
		{
		while(procesadas<10)
			{
			if (procesadas > 0)
				{
				trazador.Trazar("Procesadas", procesadas);
				trazador.Trazar("RUN");
				trazador.Trazar("Hay");
				terminado= Trabajo.Trabajando(tiempo_inicial,tiempo_final);
				procesadas++;
				}
			if (procesadas == 0)
				{
				trazador.Trazar(name);
				trazador.Trazar("RUN");
				trazador.Trazar("No hay");
				Utiles.Esperar(1000);
				procesadas++;
				}
			}	
		}
	public void Set_anterior_terminado(int terminado)
		{
		this.anterior_terminado=terminado;
		}
	public int Get_terminado()
		{
		return this.terminado;
		}	
	}
class Fresador extends Thread
	{
	private String name;
	private Trazador trazador;
	private int tiempo_inicial = 5000; //miliseconds
	private int tiempo_final = 6000;
	private int procesadas;
	private int terminado;
	private int anterior_terminado;

	public Fresador(String name, int id, int ntabs)
		{
		this.name=name;
		trazador = new Trazador(id, ntabs);
		terminado = 0;
		}
	public String Get_Name()
		{
		return this.name;
		}
	public void run()
		{
		while(procesadas<10)
			{
			if (procesadas > 0)
				{
					trazador.Trazar("Procesadas", procesadas);
					trazador.Trazar("RUN");
					trazador.Trazar("Hay");
					terminado= Trabajo.Trabajando(tiempo_inicial,tiempo_final);
					procesadas++;
				}
			if (procesadas == 0)
				{
				trazador.Trazar("RUN");
				trazador.Trazar("No hay");
				trazador.Trazar(name);
				Utiles.Esperar(1000);
				procesadas++;
				}
			}
		}
	public int Get_terminado()
		{
		return this.terminado;
		}
	public void Set_anterior_terminado(int terminado)
		{
		this.anterior_terminado=terminado;
		}

	}
class Nateador extends Thread
	{
	private String name;
	private Trazador trazador;
	private int tiempo_inicial = 2000; //miliseconds
	private int tiempo_final = 4000;
	private int procesadas;
	private int terminado;
	private int anterior_terminado;
	public Nateador(String name, int id, int ntabs)
		{
		this.name=name;
		terminado = 0;
		trazador = new Trazador(id, ntabs);
		}
	public String Get_Name()
		{
		return this.name;
		}
	public void run()
		{
		while(procesadas<10)
			{
			if (procesadas > 0)
				{
					trazador.Trazar("Procesadas", procesadas);
					trazador.Trazar("RUN");
					trazador.Trazar("Hay");
					terminado= Trabajo.Trabajando(tiempo_inicial,tiempo_final);
					procesadas++;
				}
			if (procesadas == 0)
				{
				trazador.Trazar("RUN");
				trazador.Trazar("No hay");
				trazador.Trazar(name);
				Utiles.Esperar(1000);
				procesadas++;
				}
			}
		}
	public int Get_terminado()
		{
		return this.terminado;
		}
	public void Set_anterior_terminado(int terminado)
		{
		this.anterior_terminado=terminado;
		}
	}

class Topeador extends Thread
	{
	private String name;
	private Trazador trazador;
	private int tiempo_inicial = 1000; //miliseconds
	private int tiempo_final = 3000;
	private int procesadas;
	private int terminado;
	private int anterior_terminado;
	private int terminador_main;
	public Topeador(String name, int id, int ntabs)
		{
		this.name=name;
		trazador = new Trazador(id, ntabs);
		terminado = 0;
		}

	public String Get_name()
		{
		return this.name;
		}
	public void run()
		{
		do
			{
			if (procesadas > 0)
				{
					trazador.Trazar("Procesadas", procesadas);
					trazador.Trazar("RUN");
					trazador.Trazar("Hay");
					terminado= Trabajo.Trabajando(tiempo_inicial,tiempo_final);
				procesadas++;
				}
			if (procesadas == 0)
				{
				trazador.Trazar("RUN");
				trazador.Trazar("No hay");
				trazador.Trazar(name);
				Utiles.Esperar(1000);
				procesadas++;
				}
			} while(procesadas<10);	
		}
	public int Get_terminado()
		{
		return this.terminado;
		}
	public void Set_anterior_terminado(int terminado)
		{
		this.anterior_terminado=terminado;
		}
	public int Get_terminador_main()
		{
		return this.terminador_main;
		}
	public int Get_procesadas()
		{
		return this.procesadas;
		}
	public void Set_terminar_main(int terminar)
		{
		this.terminador_main=terminar;
		}
	}
 class Trabajo
	{
	public synchronized static int Trabajando(int tiempo_inicial, int tiempo_final)
		{
		int finish= 0;
		System.out.println("Trabajado");
		Utiles.Esperar(tiempo_inicial, tiempo_final);
		finish = 1;
		return finish;
		}	
	}