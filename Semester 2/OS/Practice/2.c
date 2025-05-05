#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char* argv[])
{
	int fd = open("roby", O_RDONLY);
	int cmmmc;
	read(fd, &cmmmc, sizeof(int));
	int p[2];
	pipe(p);
	int id = fork();
	if(id == -1)
	{
		printf("Eroare fork\n");
		exit(1);
	}
	if(id == 0)
	{
		close(p[0]);
		int* divizori = malloc(100 * sizeof(int));
		int k = 0;
		for(int i = 1; i <= cmmmc; i++)
			if(cmmmc % i == 0)
				divizori[k++] = i;
		write(p[1], &k, sizeof(int));
		write(p[1], divizori, 100 * sizeof(int));
		close(p[1]);
		free(divizori);
		exit(0);
	}
	else
	{
		wait(NULL);
		close(p[1]);
		int* divizori = malloc(100 * sizeof(int));
		int k = 0;
		read(p[0], &k, sizeof(int));
		read(p[0], divizori, 100 * sizeof(int));
		close(p[0]);
		for(int i = 0; i < k; i++)
			printf("%d ",*(divizori + i));
	}
	return 0;
}
