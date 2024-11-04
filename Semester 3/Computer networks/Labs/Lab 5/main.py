import socket
import time
import sys
import threading
import re

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

broadcastAddress = "255.255.255.255"
port = 7777
s.bind(('0.0.0.0', port))
dateQueryString = b"DATEQUERY\10"
timeQueryString = b"TIMEQUERY\10"
peersList = dict() 
malformed = []

def sendDateQuery():
    global s, port, dateQueryString, broadcastAddress
    while True:
        b = s.sendto(dateQueryString, (broadcastAddress, port))
        if b != len(dateQueryString):
            print("failed to send date query")
        time.sleep(3)

def sendTimeQuery():
    global s, port, timeQueryString, broadcastAddress
    while True:
        b = s.sendto(timeQueryString, (broadcastAddress, port))
        if b != len(timeQueryString):
            print("failed to send time query")
        time.sleep(10)

def responseQuery():
    global s, port, timeQueryString, dateQueryString, peersList

    while True:
        msg, addr = s.recvfrom(1024)
        print("Message received: " + msg.decode())
        if msg == timeQueryString:
            my_time = time.strftime("TIME %H:%M:%S")
            print(f"Responding with {my_time} to ip: {addr[0]} port {addr[1]}")
            my_time = my_time.encode()
            b = s.sendto(my_time, addr)
            if b != len(my_time):
                print("failed to send response")
        elif msg == dateQueryString:
            my_date = time.strftime("DATE %d:%m:%Y")
            print(f"Responding with {my_date} to ip: {addr[0]} port {addr[1]}")
            my_date = my_date.encode()
            b = s.sendto(my_date, addr)
            if b != len(my_date):
                print("failed to send response")
        else:
            if regexMatch(msg = msg, pattern = "TIME [0-9]{2}:[0-9]{2}:[0-9]{2}") is None:
                malformed.append(msg)
            # inca unu asa pt data
            # si dupa inca un thread pt afisare
                
                
        
def regexMatch(msg, pattern):
    pattern = re.compile(pattern)
    return pattern.fullmatch(msg)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Specify the broadcast address")
        exit(1)
    broadcastAddress = sys.argv[1]
    sndTimeThrd = threading.Thread(target=sendTimeQuery)
    sndDateThrd = threading.Thread(target=sendDateQuery)

    sndTimeThrd.start()
    sndDateThrd.start()

    responseQuery()

    sndTimeThrd.join()
    sndDateThrd.join()
