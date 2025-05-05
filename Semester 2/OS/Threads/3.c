#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

typedef struct {
        int a, b;
        int nr;
} argStruct;

int global=0;
pthread_mutex_t mut = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t* m2;


void* f1(void* a) {
        argStruct* arg = (argStruct*)a;
        int n = arg->a, m = arg->b;

        for(int i=0; i<m; i++) {
                pthread_mutex_lock(&mut);
                global += n;
                printf("gl %d: threadNr %d added %d\n", global, arg->nr, n);
                pthread_mutex_unlock(&mut);
        }

        pthread_mutex_unlock(&m2[arg->nr]);

        free(arg);
        return NULL;
}

void* f2(void* a) {
        argStruct* arg = (argStruct*)a;
        int x = arg->a, y = arg->b;

        pthread_mutex_lock(&m2[arg->nr]);

        int missedOp = 0;
        for(int i=0; i<y; i++) {
                pthread_mutex_lock(&mut);
                if(global-x < 0) {
                        printf("gl %d: threadNr %d didn't decrease\n", global, arg->nr);
                        missedOp = y-i;
                        pthread_mutex_unlock(&mut);
                        break;
                }
                else {
                        global -= x;
                        printf("gl %d: threadNr %d decr'd %d\n", global, arg->nr, x);

                }
                pthread_mutex_unlock(&mut);
        }

        if(missedOp > 0)
                printf("END: threadNr %d did not decrese %d times\n", arg->nr, missedOp);

        free(arg);
        return NULL;
}

int main(int argc, char** argv) {
        int n = argc/4;
        pthread_t t1[n];
        pthread_t t2[n];

        pthread_mutex_t mutexes[n];
        for(int i=0; i<n; i++) {
                pthread_mutex_init(&mutexes[i], NULL);
                pthread_mutex_lock(&mutexes[i]);
        }
        m2 = mutexes;

        for(int i=0; i<n; i++) {
                argStruct* arg1 = (argStruct*)malloc(sizeof(argStruct));
                argStruct* arg2 = (argStruct*)malloc(sizeof(argStruct));

                arg1->a = atoi(argv[i*4+1]);
                arg1->b = atoi(argv[i*4+2]);
                arg1->nr = i;
                arg2->a = atoi(argv[i*4+3]);
                arg2->b = atoi(argv[i*4+4]);
                arg2->nr = i;


                pthread_create(&t1[i], NULL, f1, arg1);
                pthread_create(&t2[i], NULL, f2, arg2);
        }

        for(int i=0; i<n; i++) {
                pthread_join(t1[i], NULL);
                pthread_join(t2[i], NULL);
                pthread_mutex_destroy(&m2[i]);
        }

        pthread_mutex_destroy(&mut);

        return 0;
}
