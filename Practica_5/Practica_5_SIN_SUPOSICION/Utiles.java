import java.util.*;

public class Utiles
	{
	public static void Esperar(int milisegundos)
		{
		Random rnd = new Random();
		try 
		    {
			Thread.sleep(rnd.nextInt(milisegundos));
			} catch (Exception e) {}
 
		}

	public static void Esperar(int init, int fin)
		{
		Random rnd = new Random();
		try 
		    {
               Thread.sleep(rnd.nextInt(fin-init)+init);
			} catch (Exception e) {}
 
		}
	}