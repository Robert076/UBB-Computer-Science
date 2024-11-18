import socket, struct, threading

mylock = threading.Lock()
threads = []
e = threading.Event()
clientCount = 0

def msort3(x):
    if len(x) < 2:
        return x
    result = []
    mid = int(len(x) / 2)
    y = msort3(x[:mid])
    z = msort3(x[mid:])
    i = 0
    j = 0
    while i < len(y) and j < len(z):
        if y[i] > z[j]:
            result.append(z[j])
            j += 1
        else:
            result.append(y[i])
            i += 1
    result += y[i:]
    result += z[j:]
    return result

def worker(cs):
    global mylock, e, clientCount
    
    myCount = clientCount
    msg = "Welcome client #" + str(clientCount)
    cs.sendall(bytes(msg, 'ascii'))
    print('Client #', clientCount, ' has connected from: ', cs.getpeername(), cs)
    
    try:
        n = cs.recv(4)
        n = struct.unpack('!I', n)[0]
        print('Received n: ', n)
        nums = cs.recv(4 * n)
        nums = struct.unpack('!' + 'I' * n, nums)
        mylock.acquire()
        list = []
        for num in nums:
            list.append(num)
        list = msort3(list)
        print(list)
        mylock.release()
    except socket.error as se:
        print(se.strerror)


if __name__ == "__main__":
    try:
        rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind(('0.0.0.0', 1234))
        rs.listen(5)
    except socket.error as err:
        print(err)
        exit(-1)
    while True:    
        client_socket, client_address = rs.accept()
        
        t = threading.Thread(target=worker, args=(client_socket,))
        threads.append(t)
        clientCount += 1
        t.start()
    
