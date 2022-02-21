#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
	{
	pid_t pid = fork();
	int status;

	if (pid > 0)
		{
		printf("%d\n", getpid());
		printf("Soy el proceso padre: mi pdi=%d, mi hijo es %d\n", getpid(), pid);
		pid_t fin = wait(&status);
		printf("Soy el proceso padre: mi pdi=%d, mi hijo es %d, %d ha terminado\n", getpid(), pid, pid);
		exit(EXIT_SUCCESS);
		}
	if(pid == 0)
		{
		printf("Soy el proceso hijo, mi pid=%d\n", getpid());
		printf("Soy el proceso hijo, mi pid=%d y termino\n", getpid());
		_exit(EXIT_SUCCESS);
		}
	if(pid<0)
		{
		exit(EXIT_FAILURE);
		}
	}