import socket, threading, struct, sys

mylock = threading.Lock()
winnerThread = 0
e = threading.Event()
e.clear()
threads = []
clientCount = 0

def worker(cs):
    global mylock, winnerThread, clientCount, e

    myIdCount = clientCount
    print('Client #',clientCount,' from: ', cs.getpeername(), cs)
    message = "Hello client #" + str(clientCount)
    cs.sendall(bytes(message, 'ascii'))

    cnumber = cs.recv(4)
    cnumber = struct.unpack('!I', cnumber)[0]

    if(cnumber == 5):
        mylock.acquire()
        winnerThread = threading.get_ident()
        mylock.release()

    if winnerThread != 0:
        if threading.get_ident() == winnerThread:
            print('We have a winner ', myIdCount)
            e.set()
    cs.close()

if __name__ == "__main__":
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))

        rs.listen(5)
    except socket.error as msg:
        exit(-1)

    while True:
        client_socket, addrc = rs.accept()
        t = threading.Thread(target = worker, args=(client_socket,))
        threads.append(t)
        clientCount+=1
        t.start()

    
