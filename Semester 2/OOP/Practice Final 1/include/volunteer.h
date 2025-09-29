#include <string>
using namespace std;

class Volunteer
{
private:
    string name, email, interests, departmentName;
public:
    Volunteer(string name, string email, string interests) { this->name = name; this->email = email; this->interests = interests; };
    string getName() { return name; }
    string getEmail() { return email; }
    string getInterests() { return interests; }
    string getDepartmentName() { return departmentName; }
    void setName(string name) { this->name = name; }
    void setEmail(string email) { this->email = email; }
    void setInterests(string interests) { this->interests = interests; }
    void setDepartmentName(string departmentName) { this->departmentName = departmentName; }
};