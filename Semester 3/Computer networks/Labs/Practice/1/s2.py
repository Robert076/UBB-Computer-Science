import socket
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('0.0.0.0', 1234))
s.listen(5)

print('Server is listening...')

client_socket, client_address = s.accept()

print(f"Connected to {client_address}")

strr = client_socket.recv(1024)
strr = strr.decode('utf-8')

print("Received", strr)

res = 0
for i in range(0, len(strr)):
    if strr[i] == ' ':
        res += 1

client_socket.sendall(struct.pack('!I', res))
