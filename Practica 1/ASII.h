#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include <sys/wait.h>
#include <errno.h>
#include <signal.h>

#define RED "\e[0;31m"
#define SIZE 80

char tabs[SIZE];

pid_t Fork()
	{
	pid_t pid = fork();

	if(pid<0)
		{
		perror("Something went wrong\n");
		exit(EXIT_FAILURE);
		}
	return pid;
	}

void Esperar(int maximo)
	{
	int espera=rand() % maximo;
	usleep(espera*1000);
	}

void Trazar_s(const char * s)
	{
	printf("%s[%d]%s\n",tabs, getpid(), s);
	}

void Trazar_sd(const char * s, int d)
	{
	printf("%s[%d]%s:%d\n",tabs, getpid(), s, d);
	}

void Trazar_ss(const char * s1, const char * s2)
	{
	printf("%s[%d]%s:%s\n",tabs, getpid(), s1, s2);
	}

void Mostrar_error(const char *mensaje)
	{
	fprintf(stderr, RED "%s: %s\n", mensaje, strerror(errno));
	}

void Crear_tabs(int n, char * tab)
	{
	for (int k=0; k<n; k++)	tabs[k]='\t';
	}

__sighandler_t Redirigir(int senal, __sighandler_t manejador)
	{
	 __sighandler_t m;
    
    m = signal(senal, manejador);
    if (m == SIG_ERR) 
        {
        printf("ERROR\n");
        exit(-1);
        }
    return(m);
	}