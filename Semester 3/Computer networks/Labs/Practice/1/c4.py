import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    s.sendall(struct.pack('!I', 5))
    strr = b'Message'

    s.sendall(strr)
