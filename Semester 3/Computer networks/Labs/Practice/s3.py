import socket, struct

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)

    print('Server is listening')

    client_socket, client_address = s.accept()

    print(f"Connected to {client_address}")

    data = client_socket.recv(1024)
    dataa = data.decode('utf-8')

    rev = dataa[::-1]

    rev = rev.encode('utf-8')
    client_socket.sendall(rev)

    print(dataa)
