import socket, struct, threading

winnerThread = 0
threads = []
mylock = threading.Lock()
clientCount = 0
e = threading.Event()
serverNum = 4
closest = 2 ** 16
not_guessed = True

def worker(cs):
    global winnerThread, threads, mylock, clientCount, e, not_guessed
    
    curr = clientCount
    
    msg = f"Welcome client #{curr}"
    cs.sendall(bytes(msg, 'ascii'))
    
    print("Client #",  curr, " has connected from ", cs.getpeername(), cs)
    
    while not_guessed:
        try:
            clientNum = cs.recv(4)
            clientNum = struct.unpack('!I', clientNum)[0]
            
            if(clientNum == serverNum):
                mylock.acquire()
                winnerThread = threading.get_ident()
                not_guessed = False
                mylock.release()
        except socket.error as err:
            print(err.strerror)
            break
        
        if winnerThread == threading.get_ident():
            print(f"Client #{curr} won!")
            
    
if __name__ == "__main__":
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as err:
        print(err)
        exit(-1)
    while True:    
        client_socket, client_address = rs.accept()
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        clientCount += 1
        t.start()
    

    
    
    
    
    