#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
int main(int argc, char* argv[])
{
	int pd[2];
	pipe(pd);
	if(fork() == 0)
	{
		close(pd[0]);
		int n, k = 0;
		int* nums = malloc(100 * sizeof(int));
		printf("How many numbers to read from file: ");
		scanf("%d", &n);
		FILE* f = fopen("num.txt", "r");
		for(int i = 0; i < n; i++)
		{
			int num;
			fscanf(f, "%d", &num);
			nums[k++] = num;
		}
		fclose(f);
		write(pd[1], &k, sizeof(int));
		write(pd[1], nums, 100 * sizeof(int));
		close(pd[1]);
		exit(0);
	}	
	else
	{
		wait(NULL);
		close(pd[1]);
		int k;
		int* nums = malloc(100 * sizeof(int));
		read(pd[0], &k, sizeof(int));
		read(pd[0], nums, 100 * sizeof(int));
		int sum = 0;
		for(int i = 0; i < k; i++)
			if(nums[i] % 2 == 1)
				sum += nums[i];
		mkfifo("roby", 0600);
		int fd = open("roby", O_WRONLY);
		write(fd, &sum, sizeof(int));
		close(fd);
		close(pd[0]);
	}
	return 0;
}
