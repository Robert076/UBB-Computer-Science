#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>

int main(int argc, char* argv[])
{
	int fd = open("roby", O_RDONLY);
	int sum;
	read(fd, &sum, sizeof(int));
	close(fd);
	printf("%d", sum);
	return 0;
}
