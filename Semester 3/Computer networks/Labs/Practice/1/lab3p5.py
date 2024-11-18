import socket, threading, time, sys
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1) # broadcast

s.bind(('0.0.0.0', 7777))
broadcastAddress = "255.255.255.255" # default, we will read this later

dateQueryString = b"DATEQUERY"
timeQueryString = b"TIMEQUERY"
port = 7777
def sendTimeQuery():
    global s, timeQueryString, broadcastAddress
    while True:
        query = s.sendto(timeQueryString, (broadcastAddress, port))
        if query != len(timeQueryString):
            print("Failed to send time query!")
        time.sleep(10)

def sendDateQuery():
    global s, dateQueryString, broadcastAddress
    while True:
        query = s.sendto(dateQueryString, (broadcastAddress, port))
        if query != len(dateQueryString):
            print("Failed to send date query!")
        time.sleep(3)

def responseQuery():
    global s, dateQueryString, timeQueryString
    while True:
        msg, addr = s.recvfrom(1024)
        print('Message received ' + msg.decode())
        if msg == timeQueryString:
            myTime = time.strftime("TIME %H:%M:%S")
            print(f"Responding with {myTime} to ip: {addr[0]}:{addr[1]}")
            myTime = myTime.encode()
            b = s.sendto(myTime, addr)
            if b != len(myTime):
                print("Failed to send time response")
        elif msg == dateQueryString:
            myDate = time.strftime("DATE %d:%m:%Y")
            print("Sending {myDate} to ip: {addr[0]}:{addr[1]}")
            myDate = myDate.encode()
            b = s.sendto(myDate, addr)
            if b != len(myDate):
                print("Failed to send date")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Specify broadcast address")
        exit(1)

    broadcastAddress = sys.argv[1]
    sendTimeThread = threading.Thread(target = sendTimeQuery)
    sendDateThread = threading.Thread(target = sendDateQuery)

    sendTimeThread.start()
    sendDateThread.start()

    responseQuery()

    sendTimeThread.join()
    sendDateThread.join()


