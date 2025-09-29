#include "observer.h"
#include <vector>
using namespace std;

class ObserverManager
{
private:
    vector<Observer *> observers;
public:
    ObserverManager(){};
    void registerObserver(Observer *observer)
    {
        observers.push_back(observer);
    }
    void notify()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            observers[i]->update();
        }
    }
    ~ObserverManager()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            delete observers[i];
        }
    }
};