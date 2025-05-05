#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

int fuel = 0;
pthread_mutex_t fuelMutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t semnal = PTHREAD_COND_INITIALIZER;

void* fuellingCar(void* arg)
{
	for(int i = 0; i < 5; i++)
	{
		pthread_mutex_lock(&fuelMutex);
		fuel += 15;
		pthread_cond_signal(&semnal);
		printf("Fuelling up... Fuel: %d\n", fuel);
		pthread_mutex_unlock(&fuelMutex);
	}
	return 0;
}

void* addingFuel(void* arg)
{
	pthread_mutex_lock(&fuelMutex);
	fuel -= 40;
	printf("Got fuel. Now leaving\n");
	pthread_mutex_unlock(&fuelMutex);
	return 0;
}

int main(int argc, char** argv)
{
	pthread_t t1, t2;
	fuel = atoi(argv[1]); // starting fuel
	pthread_create(&t2, NULL, fuellingCar, NULL);
	pthread_create(&t1, NULL, addingFuel, NULL);
	pthread_join(t2, NULL);
	pthread_join(t1, NULL);
	pthread_mutex_destroy(&fuelMutex);
	return 0;
}
