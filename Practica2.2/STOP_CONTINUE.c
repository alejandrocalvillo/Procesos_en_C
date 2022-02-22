#include "ASII.h"



void Proceso_hijo()
	{
	for (int i=0; i<100000; i++)
		{
		Trazar_d(i);
		Esperar(1000);
		}
	_exit(EXIT_SUCCESS);
	}

int main()
	{
	pid_t pid;
	pid = Fork();
	if (pid == 0)
		{
		Proceso_hijo();
		}
	while (1)
		{
		Esperar(5000);
		Trazar_s("Envio Stop");
		kill(pid,SIGTSTP);
		Esperar(5000);
		Trazar_s("Envio Continue");
		kill(pid,SIGCONT);
		}
	exit(EXIT_SUCCESS);
	}