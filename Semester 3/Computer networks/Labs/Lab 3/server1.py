import socket
import threading
import random
import struct
import sys
import time

random.seed()
mutex = threading.Lock()
start=1; stop=2**17-1
my_num= random.randint(start,stop)
print('Server number: ',my_num)
over=False
min_diff = 2**17-1
id_min_diff = 0
e = threading.Event()
e.clear()
threads = []
client_count=0

def worker(cs):
    global mylock, over, my_num, client_count,e, min_diff, id_min_diff, mutex

    my_idcount=client_count
    print('client #',client_count,'from: ',cs.getpeername(), cs )
    message='Hello client #'+str(client_count)+' ! You are entering the number guess competion now !'
    cs.sendall(bytes(message,'ascii'))

    cnumber = cs.recv(4)
    cnumber = struct.unpack('!I', cnumber)[0]

    while not over:
        try:
            if abs(my_num - cnumber) < min_diff:
                min_diff = abs(my_num - cnumber)
                id_min_diff = threading.get_ident()
                if min_diff == 0:
                    mutex.acquire()
                    over = True
                    mutex.release()

        except socket.error as msg:
            print('Error:',msg.strerror)
            break

    if over:
        if threading.get_ident() == id_min_diff:
            cs.sendall(b'W')
            print('We have a winner', cs.getpeername())
            print("Thread ",my_idcount," winner")
            e.set()
        else:
            cs.sendall(b'L')
            print("Thread ",my_idcount," looser")
    time.sleep(1)
    cs.close()
    print("Worker Thread ",my_idcount, " end")


def resetSrv():
    global over, id_min_diff, min_diff, my_num, threads,e
    while True:
        e.wait()
        for t in threads:
            t.join()
        print("all threads are finished now")
        e.clear()
        threads = []
        over = False
        min_diff = 2 ** 17 - 1
        id_min_diff = 0
        my_num = random.randint(start,stop)
        print('Server number: ',my_num)

def update_no_connection():
    global over, mutex
    print("Time expired!")
    mutex.acquire()
    over = True
    mutex.release()
    for t in threads:
        t.join()



if __name__=='__main__':
    try:
        rs=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind( ('0.0.0.0',1234) )
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    t=threading.Thread(target=resetSrv, daemon=True)
    t.start()

    connection_timer = None

    while True:
        if connection_timer is not None:
            connection_timer.cancel()

        connection_timer = threading.Timer(5.0, update_no_connection)
        connection_timer.start()

        client_socket,addrc = rs.accept()
        connection_timer.cancel()
        no_connection = False
        t = threading.Thread(target=worker, args=(client_socket,) )
        threads.append(t)
        client_count+=1
        t.start()
