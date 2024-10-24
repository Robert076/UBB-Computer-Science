import socket, struct, sys

if __name__ == '__main__':
    try:
        s = socket.create_connection(('172.30.248.209', 1234))
    except socket.error as msg:
        print("Error", msg.strerror)
        exit(-1)

    data = s.recv(1024)
    print(data.decode('ascii'))

    my_num = int(input('Pick a number: '
))
    try:
        s.send(struct.pack('!I', my_num))
        answer = s.recv(1)
    except socket.error as msg:
        print('Error: ', msg.strerror)
        s.close()
        exit(-2)

    s.close()
    if answer==b'W':
        print("I am the winner with",my_num)
    else:
        print("I lost !")
