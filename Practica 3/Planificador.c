#include "ASII.h"
#include <ctype.h>

#define QUANTUM 1000000
#define MAX_PROCESOS 20

pid_t pid_padre;
pid_t pid_procesos[MAX_PROCESOS];
int counter;
int terminado;

int args_check(int ac, char** av);
void Proceso (int duracion, int i);
void Manejador_algo(int senal);
int Recorrer_cola(int ac);
void Eliminador (pid_t pid_hijo);

int main(int ac, char**av)
	{
	int args = args_check(ac, av);
    if(args!= 0)
        {
        exit(EXIT_FAILURE);
        }
	int fin = 0;
	pid_t pid;
	pid_padre=getpid();
	__sighandler_t manejador;
	manejador=Redirigir(SIGUSR1, Manejador_algo);
	for (int i = 1; i<ac; i++)
		{
		pid = Fork();
		if(pid!=0)
			{
			pid_procesos[i-1]=pid;
			kill(pid, SIGTSTP);
			}
		if (pid==0)
			{
			Proceso(atoi(av[i]), i);
			}
        }
	//SCHEDULER
	while(1)
		{
		for (int i = 0; i<ac-1; i++)
			{
			if (pid_procesos[i]!=0)
				{
				Trazar_sd("Continuo proceso", pid_procesos[i]);
				kill(pid_procesos[i], SIGCONT);
				usleep(QUANTUM);
				if (terminado == 0)
					{
					Trazar_sd("Paro proceso", pid_procesos[i]);
					kill(pid_procesos[i], SIGTSTP);
					}
				terminado = 0;
				}
			}
			if(counter == ac-1)
				{
				exit(EXIT_SUCCESS);
				}
		}	
	}

int args_check(int ac, char** av)
    {
    for(int i = 1; i<ac; i++)
        {
        char digit = *av[i];
        if(isdigit(digit)==0)
            {
            Mostrar_error("Argumentos invalidos\nForma de uso:\n     ./Planificador dur1 dur2 ... durn\n     durx debe ser un numero entero decimal que representa la duración del proceso en segundos \nel número máximo de procesos es 20\n");
            return -1;
            }
        }
    if(ac<2)
        {
        Mostrar_error("Argumentos insuficientes\nForma de uso:\n     /Planificador dur1 dur2 ... durn\n     durx debe ser un numero entero decimal que representa la duración del proceso en segundos\n el número máximo de procesos es 20\n");
        return -1;
        }
    if(ac>20)
        {
        Mostrar_error("Demasiados argumentos\nForma de uso:\n     ./Planificador dur1 dur2 ... durn\n     durx debe ser un numero entero decimal que representa la duración del proceso en segundos\n el número máximo de procesos es 20\n");
        return -1;
        }
   
    else {return 0;}
    }	

void Proceso(int duracion, int i)
    {
    Crear_tabs(i);
    Trazar_s("Empiezo");
    for (int t = duracion; t>=0; t--)
        {
        printf("%s[%d] %d \n",tabs,getpid(),t);
        for(long i=0; i<100000000; i++);
        }
    
   	kill(pid_padre,SIGUSR1);
	_exit(EXIT_SUCCESS);
    }

void Manejador_algo(int senal)
	{
	int status;
	pid_t pid_hijo = wait(&status);
	Eliminador(pid_hijo);
	Trazar_sd("Termino", pid_hijo);
	terminado = 1;
	counter++;
	}

void Eliminador (pid_t pid_hijo)
	{
	for (int i = 0; i<MAX_PROCESOS; i++)
		{
		if (pid_procesos[i]==pid_hijo)
			{
			pid_procesos[i] = 0;
			}
		}
	}