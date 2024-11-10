import socket, struct


if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket_error as se:
        print(se.strerror)
        exit(-1)

    s.sendall(struct.pack('!I', 5))
    s.close()
