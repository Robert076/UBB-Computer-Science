#include <stdio.h>
#include <pthread.h>

#define BEARS 5
#define BEES 30

int honey = 100; // commodity
pthread_mutex_t m;
pthread_cond_t c;

void* bee(void* a)
{
	// thread
	while(1)
	{
		pthread_mutex_lock(&m);
		honey++;
		pthread_mutex_unlock(&m);
	}
	return NULL;
}

void* bear(void* a)
{
	// thread
	while(1)
	{
		pthread_mutex_lock(&m);
		if(honey >= 10)
		{
			honey -= 10;
		}
		else
		{
			pthread_cond_signal(&c);
		}
		pthread_mutex_unlock(&m);
	}
	return NULL;
}

void* ranger(void* a)
{
	while(1)
	{
		pthread_mutex_lock(&m);
		while(honey >= 10)
		{
			pthread_cond_wait(&c, &m);
		}
		honey += 100;
		pthread_mutex_unlock(&m);
	}
	return NULL;
}

int main(int argc, char** argv)
{
	pthread_t bees[BEES], bears[BEARS], rangert;

	int i;
	for(i = 0; i < BEES; i++)
		pthread_create(&bees[i], NULL, bee, NULL);
	
	for(i = 0; i < BEARS; i++)
		pthread_create(&bears[i], NULL, bear, NULL);
	pthread_create(&rangert, NULL, ranger, NULL);

	for(i = 0; i < BEES; i++)
		pthread_join(bees[i], NULL);

	for(i = 0; i < BEARS; i++)
		pthread_join(bears[i], NULL);

	pthread_join(rangert, NULL);

	pthread_mutex_destroy(&m);
	pthread_cond_destroy(&c);

	return 0;
}
