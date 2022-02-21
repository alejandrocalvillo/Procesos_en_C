#include "ASII.h"
#include <sys/wait.h>

void Proceso_hijo()
	{
	printf("Soy el proceso hijo y mi PID es: %d\n", getpid());
	_exit(EXIT_SUCCESS);
	}

int main()
	{
	pid_t pdi;
	int status;
	
	for (int i = 0; i<3; i++)
		{
		pdi=Fork();
		if (pdi == 0)
			{
			Proceso_hijo();
			}
		}
	for (int i = 0; i<3; i++)
		{
		pid_t fin= wait(&status);		
		}
	exit(EXIT_SUCCESS);
	}