#include <fcntl.h>
#include <stdarg.h>
#include <stdio.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char** argv)
{
	int p[2];
	pipe(p); // pipes can only be inherited from parent to child
	if(!fork())
	{
		exit(0);
		close(p[1]);
		int y;
		read(p[0], &y, sizeof(int));
		printf("%d NOOOOOOO %d", getpid(), y);
	}
	close(p[0]);
	int x;
	printf("I (%d) am your father!", getpid());
	x = atoi(argv[1]);
	write(p[1], &x, sizeof(int));
	close(p[1]);
	wait(0);
	return 0;
}
