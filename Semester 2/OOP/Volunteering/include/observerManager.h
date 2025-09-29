#include "observer.h"
#include <vector>
using namespace std;

class ObserverManager
{
    private:
    vector<Observer*> observers;
    public:
    ObserverManager() {}
    ~ObserverManager()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            delete observers[i];
        }
    }
    void addObserver(Observer* o)
    {
        observers.push_back(o);
    }
    void notify()
    {
        for (int i = 0; i < observers.size(); i++)
        {
            observers[i]->update();
        }
    }
};