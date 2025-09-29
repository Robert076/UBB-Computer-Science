#include "../include/Observer.h"
#include "../include/UserMessage.h"
#include <vector>

using namespace std;

class Subject
{
private:
    vector<Observer*> observers;
public:
    void registerObserver(Observer& observer)
    {
        observers.push_back(&observer);
    }
    void unregisterObserver(Observer& observer)
    {
        auto observerToRemove = find(observers.begin(), observers.end(), &observer);
        observers.erase(observerToRemove);
    }
    void notify()
    {
        for (auto observer : observers)
        {
            observer->update();
        }
    }
};

class ChatSession : public Subject
{
    void addMessage(UserMessage message)
    {
        
    }
};