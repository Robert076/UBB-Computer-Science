# Multiple clients connect to the server
# The server starts the game 10 seconds after no new connection was established
# The game goes on like this:
# At the beginning, the server generates a random number between 1-5
# After that, the server asks each client ONE at a time, to guess
# At the end, it displays all the guesses and from who the guess is, and declares the winner

import socket, struct, threading, time, random
from threading import Timer

threads = []
clientCount = 0
winnerThread = -1
mylock = threading.Lock()
acceptConnections = True
timer = None
serverNumber = random.randint(0, 9)

def worker(clientSocket):
    global threads, clientCount, winnerThread, mylock
    myCount = clientCount
    clientGuess = struct.unpack('!I', clientSocket.recv(4))[0]
    if clientGuess == serverNumber and winnerThread == -1:
        mylock.acquire()
        winnerThread = threading.get_ident()
        mylock.release()
    
    if threading.get_ident() == winnerThread:
        print("We have a winner")
        clientSocket.sendall(b'W')
    else:
        clientSocket.sendall(b'L')

        

def updateNoConnection():
    global acceptConnections
    acceptConnections = False
    print("No new connection established within the last 10 seconds. Closing entries.")

def startTimer():
    global timer
    if timer is not None:
            timer.cancel()  
    timer = threading.Timer(10, updateNoConnection)
    timer.start()

if __name__ == "__main__":
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # TCP socket
    except socket.error as se:
        print(se.strerror)
        exit(-1)
        

    IP = '0.0.0.0'
    PORT = 1234
    s.bind(('0.0.0.0', 1234))
    s.listen(5)
    print("Server is listening")
    while acceptConnections:
        startTimer()

        clientSocket, clientAddress = s.accept()
        if acceptConnections:
            print(f"Incoming connection from {clientSocket}")
            t = threading.Thread(target=worker, args = (clientSocket, ))
            threads.append(t)
            clientCount += 1
            t.start()