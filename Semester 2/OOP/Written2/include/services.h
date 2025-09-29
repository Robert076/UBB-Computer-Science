#include "repo.h"
#include "manager.h"

class Services : public ObserverManager
{
private:
    Repo repo;
public:
    Services() {}
    void addNumber(int number)
    {
        repo.addNumber(number);
        notify();
    }
    vector<int> getNumbers()
    {
        return repo.getNumbers();
    }
};