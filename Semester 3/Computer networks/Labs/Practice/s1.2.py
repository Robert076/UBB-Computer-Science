import socket, struct, time, random

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)

    client_socket, client_address = s.accept()
    while True:
        data = client_socket.recv(400)
        data = struct.unpack('!' + 'I' * 100, data)
        
        summ = 0
        for num in data:
            summ += num

        if summ % 2 == 0:
            print('Even')
        else:
            print('Odd')
