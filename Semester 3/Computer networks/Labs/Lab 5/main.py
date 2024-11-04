import socket
import time
import sys
import threading

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

broadcast_address = "255.255.255.255"
port = 7777
s.bind(('0.0.0.0', port))
date_query_string = b"DATEQUERY\10"
time_query_string = b"TIMEQUERY\10"

def sendDateQuery():
    global s, port, date_query_string, broadcast_address
    while True:
        b = s.sendto(date_query_string, (broadcast_address, port))
        if b != len(date_query_string):
            print("failed to send date query")
        time.sleep(3)

def sendTimeQuery():
    global s, port, time_query_string, broadcast_address
    while True:
        b = s.sendto(time_query_string, (broadcast_address, port))
        if b != len(time_query_string):
            print("failed to send time query")
        time.sleep(10)

def responseQuery():
    global s, port, time_query_string, date_query_string

    while True:
        msg, addr = s.recvfrom(1024)
        print("Message received: " + msg.decode())

        if msg == time_query_string:
            my_time = time.strftime("TIME %H:%M:%S")
            print(f"Responding with {my_time} to ip: {addr[0]} port {addr[1]}")
            my_time = my_time.encode()
            b = s.sendto(my_time, addr)
            if b != len(my_time):
                print("failed to send response")
        elif msg == date_query_string:
            my_date = time.strftime("DATE %d:%m:%Y")
            print(f"Responding with {my_date} to ip: {addr[0]} port {addr[1]}")
            my_date = my_date.encode()
            b = s.sendto(my_date, addr)
            if b != len(my_date):
                print("failed to send response")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Specify the proadcast address")
        exit(1)
    broadcast_address = sys.argv[1]
    sndTimeThrd = threading.Thread(target=sendTimeQuery)
    sndDateThrd = threading.Thread(target=sendDateQuery)

    sndTimeThrd.start()
    sndDateThrd.start()

    responseQuery()

    sndTimeThrd.join()
    sndDateThrd.join()

