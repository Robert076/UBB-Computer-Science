#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

int main(int argc, char* argv[])
{
	int n, m;
	printf("Primul numar: ");
	scanf("%d", &n);
	printf("Al doilea numar: ");
	scanf("%d", &m);
	
	int a = n, b = m;

	while(b)
	{
		int r = a % b;
		a = b;
		b = r;
	}
	
	int cmmmc = n * m / a; 
	mkfifo("roby", 0600);
	int fd = open("roby", O_WRONLY);
	write(fd, &cmmmc, sizeof(int));
	close(fd);
	// execlp("rm", "rm", "roby", NULL);
	return 0;
}
