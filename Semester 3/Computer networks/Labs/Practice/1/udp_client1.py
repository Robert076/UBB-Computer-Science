import socket

UDP_IP="192.168.0.82"
UDP_PORT=5005
MESSAGE=b"Hello, World!"

print("UDP target ip: %s" % UDP_IP)
print("UDP target port: %s" % UDP_PORT)
print("Message: %s" % MESSAGE)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.sendto(MESSAGE, (UDP_IP, UDP_PORT))
