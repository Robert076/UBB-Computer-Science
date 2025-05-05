#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>

int x = 0;

int n;

pthread_mutex_t m;
pthread_cond_t c;

void* th1(void* arg)
{
	int i;
	for(i = 0; i < n; i++)
	{
		pthread_mutex_lock(&m);
		x++;
		if(! (x % 7))
		{
			pthread_cond_signal(&c);
		}
		pthread_mutex_unlock(&m);
	}
	return 0;
}

void* th2(void* arg)
{
	pthread_mutex_lock(&m);
	pthread_cond_wait(&c, &m);
	printf("x = %d\n", x);
	pthread_mutex_unlock(&m);
	return 0;
}

int main(int argc, char** argv)
{
	pthread_t *t1, *t2;
	int nt1, nt2;
	nt1 = atoi(argv[1]);
	nt2 = atoi(argv[2]);
	t1 = malloc(nt1 * sizeof(pthread_t));
	t2 = malloc(nt2 * sizeof(pthread_t));
	n = atoi(argv[3]);
	pthread_mutex_init(&n, NULL);
	pthread_cond_init(&c, NULL);
	int i;
	for(i = 0; i < nt1; i++)
	{
		pthread_create(&t1[i], NULL, th1, NULL);
	}
	for(i = 0; i < nt2; i++)
	{
		pthread_create(&t2[i], NULL, th2, NULL);	
	}
	for(i = 0; i < nt1; i++)
	{
		pthread_join(&t1[i], NULL);
	}
	for(i = 0; i < nt2; i++)
	{
		pthread_join(&t2[i], NULL);
	}
	return 0;
}
