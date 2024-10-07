
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
       int c;
       struct sockaddr_in server;
       uint16_t nr_spatii;

       c = socket(AF_INET, SOCK_STREAM, 0);
       if (c < 0) {
              printf("Eroare la crearea socketului client\n");
              return 1;
       }

       memset(&server, 0, sizeof(server));
       server.sin_port = htons(1234);
       server.sin_family = AF_INET;
       server.sin_addr.s_addr = inet_addr("127.0.0.1");

       if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
              printf("Eroare la conectarea la server\n");
              return 1;
       }
       char sir[256];
       fgets(sir, 256, stdin);
       printf("Sir = ");
       send(c, sir, sizeof(char) * strlen(sir) + sizeof(char), 0);
       recv(c, &nr_spatii, sizeof(nr_spatii), 0);
       nr_spatii = ntohs(nr_spatii);
       printf("Numarul de spatii este %hu\n", nr_spatii);
       close(c);
}
