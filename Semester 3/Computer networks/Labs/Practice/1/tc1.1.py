import socket, struct, time, random

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as se:
        print(se.strerror)
        exit(-1)

    while True:
        num = random.randint(0, 10)
        s.sendall(struct.pack('!I', num))
        print(f'Sent {num} to server')
        ans = s.recv(1)
        if ans == b'W':
            print('We won')
            break
        elif ans == b'C':
            print('Noone guessed yet. Continuing')
        else:
            print('We lost')
            break
        time.sleep(2)

