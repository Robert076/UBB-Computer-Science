#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
	int id = fork();
	if(id == 0) // suntem in child process
	{
		printf("%d | %d \n", getppid(), getpid());
		int id2 = fork();
		if(id2 == 0) // child of child (nephew of parent)
		{
			printf("%d | %d\n", getppid(), getpid());
		}
	}
	else
	{
		printf("%d\n", getpid());
	}
	wait(0);
	return 0;
}
