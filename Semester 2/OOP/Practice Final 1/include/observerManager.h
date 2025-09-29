#include "observer.h"
#include <vector>
using namespace std;
class ObserverManager
{
private:
    vector<Observer*> observers;
public:
    ObserverManager() {};
    ~ObserverManager() { for (auto observer : observers) delete observer; }
    void registerObserver(Observer* observer) { observers.push_back(observer); }
    void notify() { for (auto observer : observers) observer->update(); }
};