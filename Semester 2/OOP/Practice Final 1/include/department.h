#include <string>
using namespace std;

class Department
{
private:
    string name, description;
public:
    Department(string name, string description) { this->name = name; this->description = description;};
    string getName() { return name; }
    string getDescription() { return description; }
    void setName(string name) { this->name = name; }
    void setDescription(string description) { this->description = description; }
};