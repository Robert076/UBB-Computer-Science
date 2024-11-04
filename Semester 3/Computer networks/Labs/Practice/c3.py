import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket_error as msg:
        print(msg.strerror)
        exit(-1)

    strr = b'the text we sent'
    s.sendall(strr)
    ans = s.recv(1024)
    ans = ans.decode('utf-8')
    print(ans)

