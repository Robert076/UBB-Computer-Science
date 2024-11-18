import socket

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
strr = b'SB:Maths,ST:h,AD:1:-1:3:-1:3:2:1'
s.sendto(strr, ('0.0.0.0', 1234))
