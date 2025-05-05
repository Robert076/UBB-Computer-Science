#include <stdio.h>
#include <string.h>
#include <unistd.h>


int main(int argc, char** argv)
{
	if(fork())
	{
		printf("Hello from parent process\n");
		if(fork())
		{
			printf("This is a super parent\n");
		}
		else
		{
			printf("This is a process that is both a parent and a child\n");
		}
	}
	return 0;
}
