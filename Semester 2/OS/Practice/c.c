#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char** argv) {
	int p2a[2];
	// p2a[0] -> read
	// p2a[1] -> write
	if(pipe(p2a) == -1) {
		printf("Nu am reusit sa facem teava\n");
		return 1;
	}
	int id = fork();
	if(id == 0) {
		close(p2a[0]);
		int x;
		printf("Ce valoare vrei: ");
		scanf("%d", &x);
		write(p2a[1], &x, sizeof(int));
		close(p2a[1]);
	}
	else {
		close(p2a[1]);
		int y;
		read(p2a[0], &y, sizeof(int));
		close(p2a[0]);
		printf("Am primit din procesul copil: %d", y);
	}
	return 0;
}	
