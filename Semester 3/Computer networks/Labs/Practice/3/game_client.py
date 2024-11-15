import socket, struct

try:
    s = socket.create_connection(('localhost', 1234))
except socket.error as se:
    exit(-1)

if __name__ == "__main__":
    x = int(input("Guess: "))
    s.sendall(struct.pack('!I', x))
    m = s.recv(1)
    if(m.decode('ascii') == 'W'):
        print("We won")
    else:
        print("We lost")
