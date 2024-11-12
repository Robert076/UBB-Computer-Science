import socket, threading, struct


threads = []
sender = -1
e = threading.Event()
clientCount = 0
mylock = threading.Lock()

def worker(cs):
    global threads, sender, e, clientCount, mylock

    while True:
        data = cs.recv(1024)
        data = data.decode('ascii')
        # mesajul e in data
        sender = threading.get_ident()
        for t in threads:
            if threading.get_ident() != sender:
                t[1].sendall(bytes(data, 'ascii'))

    cs.close()

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)

    while True:
        clientSocket, clientAddress = s.accept()
        t = threading.Thread(target = worker, args=(clientSocket,))
        threads.append((t, clientSocket))
        clientCount += 1
        t.start()
