#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX_SIZE 100

// 1. Se da un sir de litere. Folosind cate un proces pentru fiecare vocala, sa se elimine toate vocalele din acest sir

int main(int argc, char* argv[])
{
	char sir[] = "bbbbb";
	char vocale[] = "aeiouAEIOU";
	
	int i;
	for(i = 0; i < strlen(vocale); i++)
	{
		int pd[2];
		if(pipe(pd) == -1)
		{
			printf("Eroare la deschidere de pipe\n");
			return 1;
		}
		int pid = fork();
		if(pid == -1)
		{
			printf("Eroare la fork\n");
			return 2;
		}
		if(pid == 0)
		{
			close(pd[0]); // inchid capatul de citire
			int k = 0;
			char* tmp = malloc(MAX_SIZE * sizeof(char));
			int j;
			for(j = 0; j < strlen(sir); j++)
			{
				if(sir[j] != vocale[i])
					tmp[k++] = sir[j];
			}
			tmp[k] = '\0';
			write(pd[1], tmp, MAX_SIZE);
			close(pd[1]);
			free(tmp);
			exit(EXIT_SUCCESS);
		}
		// in procesul parinte
		close(pd[1]); // inchid capatul de scriere
		char* tmp = malloc(MAX_SIZE * sizeof(char));
		read(pd[0], tmp, MAX_SIZE);
		strcpy(sir, tmp); // suprascriu sirul initial
		close(pd[0]);
		free(tmp);
		wait(NULL);
	}
	printf("Sirul final: %s\n", sir);
	return 0;
}
