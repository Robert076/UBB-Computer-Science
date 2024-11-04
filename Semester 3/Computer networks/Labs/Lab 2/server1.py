import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind(('0.0.0.0', 1234))

s.listen(4)

try:
    while True:
        c, address = s.accept()

        content = c.recv(1024)
        print("New connection")
        text = content.decode()

        print(f"Text: {text}")

        file = open("client_file.txt", "w")

        file.write(text)
        file.close()
        textt = str(len(text))
        textEn = textt.encode()
        res = c.send(textEn)

except:
    print("Couldn't connect")
