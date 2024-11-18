import socket

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.bind(('0.0.0.0', 1234))

data = s.recvfrom(1024)[0].decode()
print(data)
