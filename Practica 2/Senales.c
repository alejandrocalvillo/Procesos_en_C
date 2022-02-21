#include "ASII.h"

#define N_ENANITOS 7


pid_t pid_padre;
pid_t pid_primero;
int contador = 0;

char *enanitos[N_ENANITOS] = {"Sabio", "Grunon", "Bonachon", "Dormilon", "Timido", "Mocoso", "Mudito"};

void Manejador_hijo()
	{
	Trazar_s("He recibido arranque");
	}

void Manejar_algo_hijo()
    {
	Trazar_s(enanitos[contador]);
    if (contador <= 6)
		{
		Trazar_s("HI HO!");
		}
    kill(getpid()-1,SIGUSR1);
    Trazar_s ("Termino");
	contador++;
    _exit(EXIT_SUCCESS);
    }

void Proceso_hijo(char *nombre, int i)
	{	
	int status;
	Crear_tabs(i+1);
	Trazar_ss("Soy", nombre);
	Trazar_s("Arranque");
	pid_primero=getpid();
	printf("%d\n",pid_primero);
	kill(pid_padre, SIGUSR2);
	_exit(EXIT_SUCCESS);
    }

int main()
	{
	int status;
	pid_t pid;
	char *print_pid = "con pid";
	pid_padre=getpid();

	int counter = 0;
	__sighandler_t manejador;

	Redirigir(SIGUSR2, Manejador_hijo);
	
	for (int i = 6; i>-1; i--)
		{
		char *nombre = enanitos[i];
		counter ++;
		Trazar_ss("He creado a", nombre);
		Trazar_sd(print_pid,getpid()+counter);	
		pid = Fork();
		wait(&status);
		if (pid==0)
			{
			Proceso_hijo(nombre, i);
			}
		}

	Redirigir(SIGUSR1, Manejar_algo_hijo);

	Trazar_s("Blancanieves");
	Trazar_sd("Empiezo por", pid_primero);
	Trazar_s("HI HO!");
	Trazar_sd("HOLITA", pid_padre);
	Trazar_sd("para", getpid()+5);
	pid_t aguita = getpid()+5;
	kill(aguita,SIGUSR1);
	exit(EXIT_SUCCESS);
	}


