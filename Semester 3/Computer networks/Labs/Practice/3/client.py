import socket, struct

if __name__ == "__main__":
    s = socket.create_connection(('0.0.0.0', 1234))
    while True:
        num = int(input("Guess: "))

        s.sendall(struct.pack("!I", num))

        ans = s.recv(1)
        if ans == b'C':
            print("Noone guessed... Continuing...")
        elif ans == b'L':
            print("We lost")
            break
        elif ans == b'W':
            print("We win")
            break
    s.close()
    
