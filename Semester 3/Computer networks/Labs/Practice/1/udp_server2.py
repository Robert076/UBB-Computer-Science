import socket

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error as se:
    print(se.strerror)
    exit(-1)

s.bind(('0.0.0.0', 1234))

print('Server is listening...')
while True:
    data, addr = s.recvfrom(1024)
    print(f"Recieved from <{addr}> message: {data}")
