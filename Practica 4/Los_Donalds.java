import java.time.Duration;
import java.time.Instant;

class Account
    {
   double balance = 0;
    public Account(double balance)
        {
        this.balance = balance;
        }
    
    }

class Los_Donalds
    {
    static String[] nombres_sobrinos = new String[]{"Jaimito", "Jorgito", "Juanito"};
    static double[][] operaciones_sobrinos = new double[][]
        {
            {-2.85,-3.8,-6.42,-7.55,7.81,-5.5,-2.89,8.7,-6.38,0.13,-2.32,-4.07,4.22,4.71,-2.11,},
            {-9.22,7.7,2.36,2.34,-5.04,6.93,-3.25,2.79,3.44,-4.19,4.35,-3.56,},
            {8.01,8.32,-1.87,-7.28,3.12,7.85,8.96,8.91,6.16,0.68,}
        };
    
    public static void main(String[] args)
        {
	    Account cuenta = new Account(0);
        Instant inicio, fin;
        Duration duracion;
        Sobrino sobrinos[] = new Sobrino[3];
        Trazador trazador = new Trazador(0,0);
        inicio = Instant.now();
        for (int i=0; i<3; i++)
            {
            trazador.Trazar("Creo a", nombres_sobrinos[i]);
            sobrinos[i] = new Sobrino((i+1)*2, (i+1)*2, nombres_sobrinos[i], cuenta, operaciones_sobrinos[i]);
            sobrinos[i].start();
            }
        Esperar_por(sobrinos[0]);
        Esperar_por(sobrinos[1]);
        Esperar_por(sobrinos[2]);

        trazador.Trazar("FIN");
        fin = Instant.now();
        duracion = Duration.between(inicio,fin);
        System.out.println("Tiempo de ejecucion: "+ duracion);
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

class Sobrino extends Thread
    {
    Trazador trazador;
    String nombre;
    Account account;
    double operaciones[];
    public Sobrino(int id, int ntabs, String nombre, Account account, double operaciones[])
        {
        trazador = new Trazador(id,ntabs);
        this.nombre = nombre; 
        this.account = account;
        this.operaciones=operaciones;
        }
        
    public void run()
        {
        trazador.Trazar("Soy",nombre);
        Utiles.Esperar(1000);
        for (int j=0; j<operaciones.length; j++)
            {
            synchronized(this)
                {
                operar(operaciones[j]);
                Utiles.Esperar(1000);
                }
            }
        }

    public void operar(double amount)
        {
        if(amount<0.0)
            {
            trazador.Trazar("Retiro", amount);
            account.balance += amount;
            trazador.Trazar("Saldo", account.balance);
            }
        if(amount>0.0)
            {
	        trazador.Trazar("Ingreso", amount);
            account.balance += amount;
	        trazador.Trazar("Saldo", account.balance);
            }
        }
    }
