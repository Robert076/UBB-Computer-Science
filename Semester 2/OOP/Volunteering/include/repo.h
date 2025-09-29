#include "department.h"
#include "volunteer.h"
#include <vector>
#include <sstream>
#include <fstream>

class Repository
{
private:
    vector<Department> departments;
    vector<Volunteer> volunteers;
public:
    vector<Volunteer> getVolunteers()
    {
        ifstream fin("volunteers.txt");
        string line;
        volunteers.clear();
        while (getline(fin, line))
        {
            stringstream ss(line);
            string name, email, interests, departmentName;
            getline(ss, name, '|');
            getline(ss, email, '|');
            getline(ss, interests, '|');
            getline(ss, departmentName);
            Volunteer v(name, email, interests, departmentName);
            volunteers.push_back(v);
        }
        return volunteers;
    }
    vector<Department> getDepartments()
    {
        ifstream fin("departments.txt");
        string line;
        departments.clear();
        while (getline(fin, line))
        {
            stringstream ss(line);
            string name, description;
            getline(ss, name, '|');
            getline(ss, description);
            Department d(name, description);
            departments.push_back(d);
        }
        return departments;
    }
    void addVolunteer(Volunteer v)
    {
        volunteers.push_back(v);
        ofstream fout("volunteers.txt");
        for (Volunteer &volunteer : volunteers)
            fout << volunteer.getName() << "|" << volunteer.getEmail() << "|" << volunteer.getInterests() << "|" << volunteer.getDepartmentName() << endl;
    }
};
