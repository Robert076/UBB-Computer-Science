import socket, time

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error as se:
    print(se.strerror)
    exit(-1)
    
def getTime():
    myTime = time.strftime("%H:%M:%S")
    return myTime.encode()

if __name__ == "__main__":
    s.bind(('0.0.0.0', 1234))
    print("Server is listening...")
    while True:
        msg, addr = s.recvfrom(1024)
        print(f"Incoming connection from {addr}")
        if msg == b'Request':
            print(f"Sent time to {addr}")
            s.sendto(getTime(), addr)
        else:
            print(msg)
            s.sendto(b"Corrupted request", addr)
