import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as err:
        exit(-1)
        
    for i in range(0, 3):
        n = int(input(f'Enter choice {i}: '))
        s.sendall(struct.pack('!I', n))