#include <stdio.h>
#include <stdlib.h>

#include "ASII.h"

void Proceso(int duracion);

int main(int ac, char**av)
    {
    Proceso(10);
    }
    
void Proceso(int duracion)
    {
    Crear_tabs(1);
    Trazar_s("Empiezo");
    for (int t = duracion; t>=0; t--)
        {
        printf("%s[%d] %d \n",tabs,getpid(),t);
        for(long i=0; i<100000000; i++);
        }
     
    Trazar_s("Fin");
    exit(EXIT_SUCCESS);
    }
    
	