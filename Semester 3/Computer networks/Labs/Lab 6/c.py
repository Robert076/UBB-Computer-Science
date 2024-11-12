import socket
import select
import sys

# Constants
BUFFER_SIZE = 256

def main():
    if len(sys.argv) < 3:
        print("Usage:\n{} <hostname or IP address> <portno>".format(sys.argv[0]))
        sys.exit(1)

    # Parse server address and port
    host = sys.argv[1]
    port = int(sys.argv[2])

    # Create a socket
    try:
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error as e:
        print("Socket creation failed:", e)
        sys.exit(1)

    # Resolve hostname if needed
    try:
        server_ip = socket.gethostbyname(host)
    except socket.gaierror as e:
        print("Error resolving hostname:", e)
        sys.exit(1)

    # Connect to server
    try:
        client_socket.connect((server_ip, port))
        print(f"Connected to server {server_ip}:{port}")
    except socket.error as e:
        print("Connection failed:", e)
        sys.exit(1)

    # Use select to monitor stdin (keyboard) and the server socket
    while True:
        # Define the readable sockets for select
        sockets_list = [sys.stdin, client_socket]
        
        # Use select to wait for readable events
        read_sockets, _, _ = select.select(sockets_list, [], [])
        
        for sock in read_sockets:
            # If input is ready from the server
            if sock == client_socket:
                message = sock.recv(BUFFER_SIZE).decode()
                if not message:
                    # Server closed the connection
                    print("Server has closed the connection.")
                    client_socket.close()
                    sys.exit(0)
                else:
                    # Print the received message
                    sys.stdout.write(message)
                    sys.stdout.flush()
            
            # If input is ready from the keyboard (stdin)
            elif sock == sys.stdin:
                # Read user input, send to the server
                user_input = sys.stdin.readline().strip()
                client_socket.sendall(user_input.encode())

                if user_input.upper() == "QUIT":
                    print("Disconnecting from server.")
                    client_socket.close()
                    sys.exit(0)

if __name__ == "__main__":
    main()

