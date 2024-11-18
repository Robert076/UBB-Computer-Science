#A client sends to the server an array of numbers. Server returns the sum of the numbers.
import socket
import struct

# Create the server socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('0.0.0.0', 1234))
s.listen(5) # lasa un backlog de marime 5 adica maxim 5 clienti cu status de pending

print("Server is listening...")

# acceptam conexiunea de la client
client_socket, client_address = s.accept()
print(f"Connected to {client_address}")

# primim lungimea sirului
sizeOfArrayData = client_socket.recv(4)
sizeOfArray = struct.unpack('!I', sizeOfArrayData)[0]

# afisam lungimea sirului
print("Size of array:", sizeOfArray)

numbersData = client_socket.recv(4 * sizeOfArray)
numbers = struct.unpack('!' + 'I' * sizeOfArray, numbersData)


print(numbers)

sumaFatala = 0
for number in numbers:
    sumaFatala += number

pachetGreu = struct.pack('!I', sumaFatala)
client_socket.sendall(pachetGreu)

# inchidem socketurile
client_socket.close()
s.close()

