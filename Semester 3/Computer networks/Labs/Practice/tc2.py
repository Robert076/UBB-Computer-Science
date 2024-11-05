import socket, struct


if __name__ == "__main__":
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as err:
        print(err)
        exit(-1)
    
    message = s.recv(1024)
    message = message.decode('ascii')
    print(message)
    
    n = int(input('How many numbers you want: '))
    nums = []
    
    for i in range(0, n):
        num = int(input(f'Enter number {i}: '))
        nums.append(num)
        
    # sending the amount of numbers
    s.sendall(struct.pack('!I', n))
    
    # sending numbers
    s.sendall(struct.pack('!' + 'I' * n, *nums))