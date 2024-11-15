import socket, random, time

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    while True:
        x = random.randint(1, 10)
        if(x < 5):
            s.sendto(b'Request', ('localhost', 1234))
        else:
            s.sendto(b'Corrupted', ('localhost', 1234))
        
        b = s.recvfrom(1024)
        print(b[0])
        time.sleep(3)