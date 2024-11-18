import socket

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error as se:
    print(se.strerror)
    exit(-1)

while True:
    message = input('Enter a message: ')
    s.sendto(message.encode('ascii'), ('localhost', 1234))
