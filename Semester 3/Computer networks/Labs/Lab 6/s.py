import socket
import select

# Server configuration
PORT = 9034  # Port to listen on
BUFFER_SIZE = 256  # Buffer size for receiving messages
BACKLOG = 10  # Number of queued connections

# Initialize variables
master_set = set()  # To store all client sockets
client_count = 0

def get_ip_address(client_socket):
    """Get the IP address of a client socket."""
    return client_socket.getpeername()[0]

def get_port(client_socket):
    """Get the port of a client socket."""
    return client_socket.getpeername()[1]

def send_to_all(message, exclude_socket=None):
    """Send a message to all clients except the specified socket."""
    for sock in master_set:
        if sock != exclude_socket and sock != listener:
            try:
                sock.sendall(message.encode())
            except:
                print(f"Failed to send message to {get_ip_address(sock)}:{get_port(sock)}")

# Create the listener (server) socket
listener = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listener.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
listener.bind(('0.0.0.0', PORT))
listener.listen(BACKLOG)

print(f"Server listening on port {PORT}")
master_set.add(listener)  # Add the listener to the master set

# Main loop
try:
    while True:
        # Use select to wait for readable sockets
        readable_sockets, _, _ = select.select(master_set, [], [])
        
        for sock in readable_sockets:
            if sock == listener:
                # Handle new connection
                client_socket, client_address = listener.accept()
                master_set.add(client_socket)
                client_count += 1
                print(f"New connection from {client_address} on socket {client_socket.fileno()}")
                
                # Send welcome message to the new client
                welcome_message = (
                    f"Hi, you are client [{client_socket.fileno()}] ({get_ip_address(client_socket)}:{get_port(client_socket)}) "
                    f"connected to the server {listener.getsockname()[0]}\n"
                    f"There are {client_count} clients connected\n"
                )
                client_socket.sendall(welcome_message.encode())
                
            else:
                # Handle data from an existing client
                try:
                    data = sock.recv(BUFFER_SIZE).decode().strip()
                    if not data:  # Client disconnected
                        raise ConnectionResetError
                except (ConnectionResetError, ConnectionAbortedError):
                    # Client disconnected
                    print(f"Client {sock.fileno()} forcibly hung up")
                    master_set.remove(sock)
                    client_count -= 1
                    sock.close()
                    disconnect_message = f"<Server>: Client [{sock.fileno()}] disconnected\n"
                    send_to_all(disconnect_message)
                
                else:
                    if data.upper() == "QUIT":
                        # Handle quit command
                        quit_message = f"Request granted [{sock.fileno()}] - {get_ip_address(sock)}. Disconnecting...\n"
                        sock.sendall(quit_message.encode())
                        print(f"<Server>: Client {sock.fileno()} disconnected")
                        master_set.remove(sock)
                        client_count -= 1
                        sock.close()
                        
                        # Notify all clients of the disconnection
                        disconnect_message = f"<{get_ip_address(sock)} - [{sock.fileno()}]> disconnected\n"
                        send_to_all(disconnect_message)
                    else:
                        # Broadcast the received message to all other clients
                        broadcast_message = f"<{get_ip_address(sock)} - [{sock.fileno()}]> {data}\n"
                        send_to_all(broadcast_message, exclude_socket=sock)

except KeyboardInterrupt:
    print("\nServer shutting down.")
finally:
    # Close all connections
    for sock in master_set:
        sock.close()

