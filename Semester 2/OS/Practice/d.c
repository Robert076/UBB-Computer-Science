#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
	int x = atoi(argv[1]);
	if(argc > 0)
		if(x == 5)	
			printf("dA");
	return 0;
}
