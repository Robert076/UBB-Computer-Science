import socket

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.bind(('0.0.0.0', 1234))

data = s.recvfrom(1024)[0].decode()
data = data.split(',')

peerSubject = data[0][3:]
peerStyle = data[1][3:]
peerDays = data[2][3:].split(':')
print(peerSubject)

print(peerStyle)

print(peerDays)
