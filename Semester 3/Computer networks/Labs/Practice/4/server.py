import socket
import threading

clients = {}

def handleClient(clientSocket, clientAddress):
    try:
        while True:
            data = clientSocket.recv(1024).decode()
            if data == "DISCONNECT":
                print(f"[DISCONNECTED] {clientAddress}")
                del clients[clientAddress]
                notifyClients()
                break
    finally:
        clientSocket.close()
    
def notifyClients():
    for clientAddress, clientConection in clients.items():
        try:
            clientConnection.send(str(clients.keys()).encode())
        except:
            pass

def startServer():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)

    print("[SERVER STARTED] Listening on port 1234...")

    while True:
        clientSocket, clientAddress = s.accept()
        print(f"[NEW CONNECTION] {clientAddress} connected")
        threading.Thread(target = handleClient, args = (clientSocket, clientAddress)).start()

if __name__ == "__main__":
    startServer()
