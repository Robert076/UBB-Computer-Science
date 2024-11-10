import socket, struct, random, time

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket_error as se:
        print(se.strerror)
        exit(-1)

    while True:
        lst = [random.randint(1,10) for _ in range(100)]
        s.sendall(struct.pack('!' + 'I' * 100, *lst))
        time.sleep(5)
