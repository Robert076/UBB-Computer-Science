#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t mut = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t* m2;
int global = 0;

typedef struct{
	int nr1, nr2, id;
} argStruct;

void* f1(void* a)
{
	argStruct* arg = (argStruct*)a;
	int n = arg->nr1;
	int m = arg->nr2;
	for(int i = 0; i < m; i++)
	{
		pthread_mutex_lock(&mut);
		global += n;
		printf("Thread with id %d has addded %d to global, which is now %d\n", arg->id, n, global);
		pthread_mutex_unlock(&mut);
	}
	pthread_mutex_unlock(&m2[arg->id]);
	free(arg);
	return NULL;
}

void* f2(void* a)
{
	argStruct* arg = (argStruct*)a;
	int x = arg->nr1;
	int y = arg->nr2;
	pthread_mutex_lock(&m2[arg->id]);
	for(int i = 0; i < y; i++)
	{
		pthread_mutex_lock(&mut);
		if(global - x < 0)
		{
			printf("Thread with id %d did not subtract %d from global because global is %d\n", arg->id, x, global);
		}
		else
		{
			printf("Thread with id %d has subtracted %d from global and global is now %d\n", arg->id, x, global);
			global -= x;
		}
		pthread_mutex_unlock(&mut);
	}
	return NULL;
}

int main(int argc, char** argv)
{
	int n = argc / 4;
	pthread_t t1[n];
	pthread_t t2[n];

	pthread_mutex_t mutexes[n];
	for(int i = 0; i < n; i++)
	{
		pthread_mutex_init(&mutexes[i], NULL);
		pthread_mutex_lock(&mutexes[i]);
	}
		
	m2 = mutexes;
	for(int i = 0; i < n; i++)
	{
		argStruct* arg1 = malloc(sizeof(argStruct));
		argStruct* arg2 = malloc(sizeof(argStruct));
		
		arg1->nr1 = atoi(argv[i * 4 + 1]);
		arg1->nr2 = atoi(argv[i * 4 + 2]);
		arg1->id = i;
		arg2->nr1 = atoi(argv[i * 4 + 3]);
		arg2->nr2 = atoi(argv[i * 4 + 4]);
		arg2->id = i;

		pthread_create(&t1[i], NULL, f1, arg1);
		pthread_create(&t2[i], NULL, f2, arg2);
	}
	for(int i = 0; i < n; i++)
	{
		pthread_join(t1[i], NULL);
		pthread_join(t2[i], NULL);
		pthread_mutex_destroy(&m2[i]);
	}
	pthread_mutex_destroy(&mut);
	return 0;
}
