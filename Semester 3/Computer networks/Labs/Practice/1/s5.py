import socket, struct

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))

    s.listen(5)

    client_connection, client_address = s.accept()

    msg = client_connection.recv(1024)
    msg = msg.decode('utf-8')

    msg = msg.split('<END>')
    res = 0
    for c in msg[0]:
        if c == msg[1]:
            res += 1
    
    client_connection.sendall(struct.pack('!I', res))
    


