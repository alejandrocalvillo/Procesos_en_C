#include "ASII.h"


extern char tabs[80];
char *nombres[] = {"Donald","Jaimito","Juanito","Jorgito"}; 

void Proceso_sobrino(int i, pid_t padre)
	{
	int k;
	char tabs[i+1];
	int espera;

	srand(getpid());
	for (k=0; k<i; k++)	tabs[k]='\t';
	
	printf("%s[%d]",tabs, getpid());
	printf("%s\n", nombres[i]);
	printf("%sEmpiezo\n", tabs);

	Esperar(5000);

	printf("%s[%d]Termino: %d\n", tabs, padre, getpid());
	_exit(EXIT_SUCCESS);
	}

void Crear_sobrino(int cuantos, pid_t padre)
	{
	pid_t pdi;
	for (int i = 1; i<cuantos; i++)
		{
		pdi=Fork();
		if (pdi==0)
			{
			Proceso_sobrino(i, padre);
			}
		}
	}

void Esperar_sobrinos(int cuantos)
	{
	int status;
	for (int i = 1; i<cuantos; i++)
		{
		pid_t fin= wait(&status);		
		}
	}

int main()
	{
	size_t n = sizeof(nombres)/sizeof(nombres[0]);//Size of the array
	pid_t pid=getpid(); //Variable de tag para saber de quien es hijo el proceso 

	printf("%s[%d]",tabs, pid);
	printf("%s\n", nombres[0]);
	printf("%sEmpiezo\n", tabs);

	Crear_sobrino(n, pid);
	Esperar_sobrinos(n);

	printf("%s[%d]Donald:Termina\n", tabs, getpid());
	exit(EXIT_SUCCESS);
	}