import socket

UDP_IP="192.168.0.82"
UDP_PORT=5005

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

s.bind((UDP_IP, UDP_PORT))

while True:
    data, addr = s.recvfrom(1024)
    print("Received message: %s" % data)
