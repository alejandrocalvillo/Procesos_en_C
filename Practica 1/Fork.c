#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main()
	{
	pid_t pid = fork();

	if (pid>0)
		{
		printf("%d\n", getpid());
		printf("Soy el proceso padre: mi pdi=%d, mi hijo es %d\n", getpid(), pid);
		exit(EXIT_SUCCESS);
		}
	if(pid == 0)
		{
		printf("Soy el proceso hijo, mi pid=%d\n", getpid());
		_exit(EXIT_SUCCESS);
		}
	if(pid<0)
		{
		exit(EXIT_FAILURE);
		}
	}