import socket, threading, struct

mylock = threading.Lock()
threads = []
clientCount = 0
winnerThread = 0
e = threading.Event()
number = 5
clientGuessed = False

def worker(cs):
    global mylock, clientCount, winnerThread, e, clientGuessed

    cur = clientCount
    print(f'Welcome client {cur}')
    while not clientGuessed:
        cnum = cs.recv(4)
        cnum = struct.unpack('!I', cnum)[0]
        print(f'Received {cnum} from client {cur}')
        cs.sendall(b'C') # continue
        if cnum == 5:
            mylock.acquire()
            winnerThread = threading.get_ident()
            clientGuessed = True
            mylock.release()

    if clientGuessed:
        if threading.get_ident() == winnerThread:
            print('We have a winner, ', cur)
            cs.sendall(b'W')
            e.set()
        else:
            cs.sendall(b'L')
    cs.close()

def resetSrv():
    global mylock, clientCount, winnerThread, threads, e, clientGuessed

    while True:
        e.wait()
        for t in threads:
            t.join()
        print('All threads are finished now')
        e.clear()
        mylock.acquire()
        threads = []
        clientGuessed = False
        winnerThread = -1
        clientCount = 0
        print('New server number ', number)
        mylock.release()

if __name__ == "__main__":
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket_error as se:
        print(se.strerror)
        exit(-1)
    t = threading.Thread(target=resetSrv, daemon=True)
    t.start()
    while True:
        clientSocket, clientAddress = rs.accept()
        t = threading.Thread(target = worker, args = (clientSocket,))
        threads.append(t)
        clientCount += 1
        t.start()
