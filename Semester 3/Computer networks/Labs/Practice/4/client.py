import socket, threading

def listenForMessages(peerSocket):
    while True:
        try:
            message = peerSocket.recv(1024).decode()
            if message:
                print(f"[SERVER MESAGE] {message}")
        except:
            break

def udpListener(udpSocket):
    print(f"[UDP LISTENING] on port {udpSocket.getsockname()[1]}")
    
    while True:
        try:
            message, addr = udpSocket.recvfrom(1024)
            print(f"[MESSAGE FROM {addr}] {message.decode()}")
        except:
            break

def startClient():
    cs = socket.create_connection(("localhost", 1234))

    udpSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    udpSocket.bind(("localhost", 0)) # the os will assign a free port
    udpPort = udpSocket.getsockname()[1]

    print(f"[UDP PORT] Assigned: {udpPort}")

    threading.Thread(target = udpListener, args = (udpSocket, ), daemon = True).start()
    threading.Thread(target = listenForMessages, args = (cs, ), daemon = True).start()

    try:
        while True:
            message = input()
            if message == "DISCONNECT":
                cs.sendall(message.encode())
                break
            elif message.startswith("CHAT"):
                try:
                    _, peerHost, peerPort, chatMessage = message.split(" ", 3)
                    peerSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
                    peerSocket.sendto(chatMessage.encode(), (peerHost, int(peerPort)))
                    print(f"[SENT] {chatMessage} to {peerHost}:{peerPort}")
                except ValueError:
                    print("Invalid command. Use: CHAT <IP> <PORT> <MESSAGE>")
            else:
                print("Invalid command. Use CHAT <IP> <PORT> <MESSAGE> or DISCONNECT")
    finally:
        cs.close()
        udpSocket.close()

if __name__ == "__main__":
    startClient()

