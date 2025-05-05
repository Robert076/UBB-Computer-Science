#include <stdarg.h>
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

void* er(void* arg)
{
	printf("Some message...\n");
	return 0;
}

int main(int argc, char** argv)
{
	int n = atoi(argv[1]);

	pthread_t *ths = malloc(n * sizeof(pthread_t));

	int i;
	for(i = 0; i < n; i++)
	{
		pthread_create(&ths[i], NULL, er, NULL);
	}
	
	for(i = 0; i < n; i++)
	{
		pthread_join(ths[i], NULL);
	}
		
	return 0;
}
