#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

pthread_mutex_t m;
int global = 0;

void* f1(void* arg)
{
	int* a = (int*)arg;
	pthread_mutex_lock(&m);
	printf("thread %d\n", *a);
	pthread_mutex_unlock(&m);
	return 0;
}

int main(int argc, char** argv)
{
	int n = atoi(argv[1]);
	pthread_t t[n];
	pthread_mutex_init(&m, NULL);
	for(int i = 0; i < n; i++)
	{
		int* arg = malloc(sizeof(int));
		*arg=i;
		pthread_create(&t[i], NULL, f1, arg);
	}
	for(int i = 0; i < n; i++)
	{
		pthread_join(t[i], NULL);
	}
	printf("%d", global);
	return 0;
}
