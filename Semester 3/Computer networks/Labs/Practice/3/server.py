import socket, struct, threading, random

threads = []
winnerThread = -1
serverNumber = random.randint(0, 10)
clientCount = 0
mylock = threading.Lock()
e = threading.Event()
notGuessed = True

def worker(cs):
    global winnerThread, serverNumber, clientCount, mylock, e, notGuessed
    curTries = 0
    curCount = clientCount
    print(f"Starting with client {curCount}")
    while notGuessed:
        print(f"Client #{clientCount} is on try {curTries}")
        num = struct.unpack("!I", cs.recv(4))[0]
        print(num)        
        if num == serverNumber and notGuessed:
            mylock.acquire()
            winnerThread = threading.get_ident()
            notGuessed = False
            mylock.release()
        cs.sendall(b'C')
        curTries += 1
    if winnerThread == threading.get_ident():
        print(f"Thread {curCount} has won after {curTries} try(tries)")
        cs.sendall(b'W')
        e.set()
    else:
        cs.sendall(b'L')
    cs.close()
    print(f"Worker thread {curCount} stopped")


def resetSrv():
    global threads, serverNumber, clientCount, mylock, e, notGuessed
    while True:
        e.wait()
        for t in threads:
            t.join()

        print("All threads have finished")
        e.clear()
        mylock.acquire()
        threads = []
        serverNumber = random.randint(0, 10)
        clientCount = 0
        notGuessed = True
        winnerThread = -1
        mylock.release()


if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)
    print("Server started...")
    t=threading.Thread(target=resetSrv, daemon=True)
    t.start()
    while True:
        clientSocket, clientAddress = s.accept()
        print(f'New connection from {clientAddress[0]}:{clientAddress[1]}')
        t = threading.Thread(target = worker, args=(clientSocket,))
        t.start()
    


