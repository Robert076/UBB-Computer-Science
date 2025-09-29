#include "repository.h"
#include "observerManager.h"

class Services : public ObserverManager
{
private:
    Repository repository;
public:
    Services() { this->repository = Repository(); };
    void addVolunteer(string name, string email, string interests) { repository.addVolunteer(Volunteer(name, email, interests)); };
    void addDepartment(string name, string description){ repository.addDepartment(Department(name, description));};
    vector<Volunteer> getVolunteers() { return repository.getVolunteers(); };
    vector<Department> getDepartments() { return repository.getDepartments(); };
    void setVolunteers(vector<Volunteer> volunteers) { repository.setVolunteers(volunteers); };
    void setDepartments(vector<Department> departments) { repository.setDepartments(departments); };
};
