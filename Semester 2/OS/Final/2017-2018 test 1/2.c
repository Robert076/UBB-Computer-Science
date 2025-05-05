#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    int pipe_fds[2];
    char read_char;

    // Create the pipe
    if (pipe(pipe_fds) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    // Close the write end of the pipe to ensure it's empty
    close(pipe_fds[1]);

    // Attempt to read a single character from the pipe
    if (read(pipe_fds[0], &read_char, 1) == -1) {
        perror("read");
    } else {
        printf("Read character: %c\n", read_char);
    }

    // Close the read end of the pipe
    close(pipe_fds[0]);

    return 0;
}

