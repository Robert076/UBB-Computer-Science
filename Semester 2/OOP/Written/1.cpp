#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <deque>
#include <cstring>
#include <exception>
#include <cassert>
using namespace std;


/*
3. Write a C++ application for message encoding, as follows:
a. The class Encoder is abstract, its role is to encode a message in a specific way. (0.3p)
b. Create the class AlienEncoder. The method encode in this class adds the given header to the beginning of the received message. (0.4p)
c. Create the class MorseEncoder. The method encode in this class replaces each character in the message with the character "". (0.3р)
d. Create the class Mixer. The method encode in this class creates a message by alternating the letters in the two aggregate encoders. (1p)
e. The class Communication can contain one or more messages. The method communicate returns all the given messages, encoded. Before encoding, the messages are sorted alphabetically. (0.8p)
f. Create 2 Communication objects: one uses a MorseEncoder and the other one uses a Mixer. The Mixer mixes an AlienEncoder (with "ET" as header) with a MorseEncoder. Each Communication object contains 2 messages. Show the encoded messages of both objects. Take memory management into consideration and implement it correctly(1.2p)
*/


class Encoder
{
public:
    virtual string encode(string message) = 0;
};

class AlienEncoder : public Encoder
{
private:
    string header;

public:
    AlienEncoder(string h) : header{h} {};
    string encode(string message) override
    {
        message = header + message;
        return message;
    }
    ~AlienEncoder() {}
};

class MorseEncoder : public Encoder
{
public:
    MorseEncoder(){};
    string encode(string message) override
    {
        for (int i = 0; i < message.length(); i++)
            message[i] = '.';
        return message;
    }
};

class Mixer : public Encoder
{
private:
    Encoder *e1, *e2;

public:
    Mixer(string header)
    {
        e1 = new AlienEncoder(header);
        e2 = new MorseEncoder();
    }
    string encode(string message) override
    {
        string message1 = e1->encode(message);
        string message2 = e2->encode(message);
        for (int i = 0; i < message.length(); i++)
            if (i % 2 == 0)
                message[i] = message1[i];
            else
                message[i] = message2[i];
        return message;
    }
    ~Mixer()
    {
        delete e1;
        delete e2;
    }
};

class Communication
{
private:
    vector<string> messages;
    Encoder *e;

public:
    Communication(Encoder* en)
    {
        e = en;
    }
    void addMessage(string message)
    {
        messages.push_back(message);
    }
    string communicate()
    {
        string final = "";
        sort(final.begin(), final.end());
        for (auto x : messages)
        {
            final += e->encode(x);
        }
        return final;
    }
    ~Communication()
    {
        delete e;
    }
};

int main(int argc, char **argv)
{
    Communication c1{ new MorseEncoder() };
    Communication c2{ new Mixer("ET") };
    
    c1.addMessage("message1");
    c1.addMessage("message2");

    c2.addMessage("message2");
    c2.addMessage("message2");
    
    cout << c1.communicate();
    cout << c2.communicate();

    return 0;
}