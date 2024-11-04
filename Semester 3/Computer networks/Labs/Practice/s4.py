import socket, struct

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))

    s.listen(5)
    print('Server is listening...')
    client_connection, client_address = s.accept()
    data = client_connection.recv(4)
    data = struct.unpack('!I', data)[0]
    data2 = client_connection.recv(1024)
    data2 = data2.decode('utf-8')
    print(data)
    print(data2)
