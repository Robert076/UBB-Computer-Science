#include <string>
using namespace std;

class Volunteer
{
private:
    string name, email, interests, departmentName;
public:
    Volunteer(string n, string m, string i, string d)
    {
        name = n;
        email = m;
        interests = i;
        departmentName = d;
    }
    string getName()
    {
        return name;
    }
    string getEmail()
    {
        return email;
    }
    string getInterests()
    {
        return interests;
    }
    string getDepartmentName()
    {
        return departmentName;
    }
    void setDepartmentName(string d)
    {
        departmentName = d;
    }
};