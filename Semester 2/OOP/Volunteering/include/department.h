#include <string>
using namespace std;

class Department
{
private:
    string name, description;
public:
    Department(string n, string d)
    {
        name = n;
        description = d;
    }
    string getName()
    {
        return name;
    }
    string getDescription()
    {
        return description;
    }
};