#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc, char* argv[])
{
	int n;
	printf("Please input the number n: ");
	scanf("%d", &n);
	int k = 0;
	int* div = malloc(100 * sizeof(int));
	for(int i = 1; i <= n; i++)
		if(n % i == 0)
			div[k++] = i;
	mkfifo("roby", 0600);
	int fd = open("roby", O_WRONLY);
	write(fd, &k, sizeof(int));
	write(fd, div, 100 * sizeof(int));
	close(fd);
	return 0;
}
