#A client sends to the server an array of numbers. Server returns the sum of the numbers.

import socket, struct, random, sys, time

if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print("Error", msg.strerror)
        exit(-1)


    nums = [1, 2, 3, 4]
    size = len(nums)

    s.sendall(struct.pack('!I', size))
    s.sendall(struct.pack('!' + 'I' * size, *nums))
    data = s.recv(4)
    res = struct.unpack('!I', data)[0]
    print(res)
    s.close()


