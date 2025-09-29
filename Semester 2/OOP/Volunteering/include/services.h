#include "repo.h"
#include "observerManager.h"

class Services : public ObserverManager
{
private:
    Repository repo;

public:
    Services()
    {
        repo = Repository();
    }
    vector<Volunteer> getVolunteers()
    {
        return repo.getVolunteers();
    }
    vector<Department> getDepartments()
    {
        return repo.getDepartments();
    }
    void addVolunteer(Volunteer v)
    {
        repo.addVolunteer(v);
        notify();
    }
};