import socket, time

if __name__ == "__main__":
    try:
        s = socket.create_connection(('0.0.0.0', 1234))
    except socket.error as se:
        print(se.strerror)
        exit(-1)
    while True:
        msg = s.recv(1024)
        print(msg.decode('ascii'))
        time.sleep(10)
