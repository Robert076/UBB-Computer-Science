import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    msg = b'The string we sent<END>'
    char = b'e'
    s.sendall(msg)
    s.sendall(char)

    data = struct.unpack('!I', s.recv(4))[0]

    print(data)
