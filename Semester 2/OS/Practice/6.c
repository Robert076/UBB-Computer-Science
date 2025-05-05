#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc, char* argv[])
{
	int pd[2];
	pipe(pd);
	if(fork() == 0)
	{
		close(pd[0]);
		int k;
		int* div = malloc(100 * sizeof(int));
		int fd = open("roby", O_RDONLY);
		read(fd, &k, sizeof(int));
		read(fd, div, 100 * sizeof(int));
		close(fd);
		int sum = 0;
		for(int i = 0; i < k; i++)
			sum += div[i];
		write(pd[1], &sum, sizeof(int));
		close(pd[1]);
		exit(0);	
	}
	else
	{
		close(pd[1]);
		int sum;
		read(pd[0], &sum, sizeof(int));
		printf("%d", sum);
		close(pd[0]);
	}
	return 0;
}
