#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

// 1. Scrieti un program C care executa o comanda Bash si afiseaza rezultatul pe ecran
// Exemplu ls -l

int main(int argc, char* argv[])
{
	char *args[] = {"ls", "-l", NULL};

	int pid = fork();
	if(pid == -1)
	{
		printf("Eroare la fork!\n");
		return 1;
	}	
	if(pid == 0) // in copil
	{
		if(execvp(args[0], args) < 0)
		{
			perror("execvp()");
			return 2;
		}
	}
	else
	{
		int status;
		wait(&status);
		printf("[PARINTE] Copil PID %d a returnat %d\n", pid, status);
	}
	return 0;
}
