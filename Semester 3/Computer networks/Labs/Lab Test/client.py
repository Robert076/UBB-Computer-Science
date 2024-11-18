import socket, threading, struct

def listenFromServer(peerSocket):
    while True:
        try:
            message = peerSocket.recv(1024).decode()
            if message:
                print(f"[OTHER USERS] {message}")
        except:
            break

def listenFromPeers(udpSocket):
    print(f"[UDP LISTENING] on port {udpSocket.getsockname()[1]}")
    
    while True:
        try:
            message, addr = udpSocket.recvfrom(1024)
            print(f"[MESSAGE FROM {addr}] {message.decode()}")
            
            message = message.decode()
            message = message.split(',')
            peerSubject = message[0][3:]
            peerStyle = message[1][3:]
            peerDays = message[2][3:].split(':')
            
            print(f"Our subject: {subject}, our style: {style}, our days: {days}")
            print(f"Their subject: {peerSubject}, their style: {peerStyle}, their days: {peerDays}")
            
            match = True

            if peerStyle != style:
                match = False
                print("We do not have the same style. NO MATCH")
            if peerSubject != subject:
                print("We do not have the same subject. NO MATCH")
                match = False        
        
            daysMatch = False
            for i in range(0, 7):
                if int(peerDays[i]) == days[i] and days[i] != -1:
                    daysMatch = True
            

            if match and daysMatch:
                print("We match")
            else:
                print("No match")
                        
        except:
            break

days = [-1, 3, 2, 1, -1, -1, 3]
style = 'h'
subject = 'Maths'

def startClient():
    global days, style, subject
    cs = socket.create_connection(('localhost', 1234))
    
    udpSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    udpSocket.bind(("localhost", 0)) # the os will assign a free port
    udpPort = udpSocket.getsockname()[1]
    cs.sendall(struct.pack('!I', udpPort))
    print(f"[UDP PORT] Assigned: {udpPort}")

    threading.Thread(target = listenFromPeers, args = (udpSocket, ), daemon = True).start()
    threading.Thread(target = listenFromServer, args = (cs, ), daemon = True).start()

    # primesc toate ipurile de la server
    # dau comanda de exemplu : MATCH <ip> <port> 
    manevra = 'SB:' + subject + ','
    manevra = manevra + 'ST:' + style + ','
    manevra = manevra + 'AD:'
    for day in days:
        manevra = manevra + str(day) + ':'

    manevra = manevra[0:-1]
    print(manevra)
    
    try:
        while True:
            message = input()
            if message == "DISCONNECT":
                cs.sendall(message.encode())
                break
            elif message.startswith("MATCH"):
                try:
                    _, peerHost, peerPort = message.split(" ", 2)
                    peerSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
                    peerSocket.sendto(manevra.encode(), (peerHost, int(peerPort)))
                    print(f"[SENT] {manevra} to {peerHost}:{peerPort}")
                except ValueError:
                    print("Ce faci vericule?")
            else:
                print("Comanda nu exista vericu")
    finally:
        cs.close()
        udpSocket.close()

if __name__ == "__main__":
    startClient()

