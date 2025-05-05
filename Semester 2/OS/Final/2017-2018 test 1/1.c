#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

pthread_mutex_t a, b;
int n = 0;

void* fa(void* p){
  pthtread_mutex_lock(&a);
  n++;
  pthread_mutex_unlock(&a);
}

void* fb(void* p){
  pthread_mutex_lock(&b);
  n++;
  pthread_mutex_unlock(&b);
}

int main(int argc, char** argv)
{
  pthread_t t1, t2;
  pthread_create(&t1, NULL, fa, NULL);
  pthread_create(&t2, NULL, fb, NULL);
  return 0;
}
