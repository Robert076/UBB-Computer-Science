__author__ = 'dadi'
import socket, struct, random,sys, time


if __name__ == '__main__':
    try:
        # un socket e o pereche formata din ip si port
        # aici incercam sa ne conectam la socketul localhost:1234
        s = socket.create_connection( ('localhost',1234))
    except socket.error as msg:
        print("Error: ",msg.strerror)
        exit(-1)

    finished=False
    sr = 1; er=2**17-1
    random.seed()

    data=s.recv(1024) # primim un mesaj de la server
    print(data.decode('ascii')) # si il printam ( e acela de bine ati venit)
    step_count=0
    while not finished:
        my_num=random.randint(sr,er)
        try:
            s.sendall( struct.pack('!I',my_num) ) # !I e de la big endian
            answer=s.recv(1) # primim inapoi un raspuns
        except socket.error as msg:
            print('Error: ',msg.strerror)
            s.close()
            exit(-2)
        step_count+=1
        print('Sent ',my_num,' Answer ',answer.decode('ascii'))
        if answer==b'H':
            sr = my_num
        if answer==b'S':
            er = my_num
        if answer==b'G' or answer==b'L':
            finished=True
        time.sleep(0.25)

    s.close()
    if answer==b'G':
        print("I am the winner with",my_num,"in", step_count,"steps")
    else:
        print("I lost !!!")
