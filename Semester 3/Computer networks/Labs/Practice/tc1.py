import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        exit(-1)

    data = s.recv(1024)
    print(data.decode('ascii'))

    num = int(input('Enter number: '))
    s.sendall(struct.pack('!I', num))
    s.close()
