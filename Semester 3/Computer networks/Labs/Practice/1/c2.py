import socket, struct

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print("Error", msg.strerror)
        exit(-1)

    strr = b'Te st'
    
    s.sendall(strr)

    data = s.recv(4)
    dataa = struct.unpack('!I', data)[0]
    print(dataa)
