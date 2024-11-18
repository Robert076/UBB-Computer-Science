import socket
import threading
import struct
clients = {}
clientsUdp={}
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
    for clientAddress, clientConnection in clients.items():
        try:
            clientConnection.send(str(clients.keys()).encode())
            clientConnection.send(str(clientsUdp.values()).encode())
        except:
            pass

def startServer():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)

    print("[SERVER STARTED] Listening on port 1234...")

    while True:
        clientSocket, clientAddress = s.accept()
        udpPort = struct.unpack('!I', clientSocket.recv(4))[0]
        print(f"[NEW CONNECTION] {clientAddress} connected with UDP port: {udpPort}")
        clients[clientAddress] = clientSocket
        clientsUdp[clientAddress] = udpPort
        notifyClients()
        threading.Thread(target = handleClient, args = (clientSocket, clientAddress)).start()

if __name__ == "__main__":
    startServer()
