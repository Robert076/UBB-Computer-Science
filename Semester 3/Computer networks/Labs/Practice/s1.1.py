import socket, struct

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)
    
    client_socket, client_address = s.accept()


    data = client_socket.recv(4)
    data = struct.unpack('!I', data)

    print(data)
