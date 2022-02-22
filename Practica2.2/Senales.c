#include "ASII.h"

#define N_ENANITOS 7

pid_t pid_padre;
int counter=0;
int flag; // For controlling which part of the program are we
int pid_enanitos [N_ENANITOS];
char *enanitos[N_ENANITOS] = {"Sabio", "Grunon", "Bonachon", "Dormilon", "Timido", "Mocoso", "Mudito"};

void Manejador_algo(int senal)
	{
	if (flag == 0)
		{
		Trazar_s("He recibido arranque");
		Esperar(1200);
		}
	
	if (flag==1)
		{
		Trazar_ss("Termino", enanitos[counter]);
		counter++;
		}
	}

void Manejar_algo_hijo(int senal)
    {
	__sighandler_t manejador;
	manejador = Redirigir(SIGUSR1, Manejar_algo_hijo);
	if (counter<6)
		{
		int status;
		Trazar_s(enanitos[counter]);
   		Trazar_s("HI HO");
		Trazar_sd("Para", pid_enanitos[counter+1]);
    	Trazar_s ("Termino");
		Esperar(5000);
		kill(pid_enanitos[counter+1],SIGUSR1);
		wait(&status);
		kill(pid_padre, SIGUSR2);
    	_exit(EXIT_SUCCESS);
		}
	else 
		{
		Trazar_s(enanitos[counter]);
   		Trazar_s("HI HO");
		Trazar_s ("Termino");
		kill(pid_padre, SIGUSR2);
		_exit(EXIT_SUCCESS);
		}
    }

void Proceso_hijo(char *nombre, int i)
	{
	int status;
	Crear_tabs(i+1);
	counter = i;
	Trazar_ss("Soy", nombre);
	Trazar_s("Arranque");
	__sighandler_t manejador;
	manejador = Redirigir(SIGUSR1, Manejar_algo_hijo);
	kill(pid_padre, SIGUSR2);
	flag = 1;
	pause();
    }

int main()
	{
	int status;
	pid_t pid, wtr;
	pid_padre=getpid();
	__sighandler_t manejador;
	manejador=Redirigir(SIGUSR2, Manejador_algo);

	for (int i = 6; i>-1; i--)
		{
		char *nombre = enanitos[i];		
		Trazar_ss("He creado a ", nombre);
		pid = Fork();
		if(pid!=0)
			{
			Trazar_sd("con PID",pid);
			pid_enanitos[i]=pid;
			}
		if (pid==0)
			{
			Proceso_hijo(nombre, i);
			}
		}
	Esperar(10000);
	Trazar_s("Blancanieves");
	Trazar_s("HI HO!");
	Trazar_sd("para", pid);
	manejador = Redirigir(SIGUSR1, Manejar_algo_hijo);
	flag = 1;
	kill(pid, SIGUSR1);
	wtr = waitpid(pid_enanitos[6], &status, WUNTRACED | WCONTINUED);
	Trazar_s("Soy Blancanieves y termino");
	exit(EXIT_SUCCESS);
	}


