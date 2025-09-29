#include "../include/repository.h"

void Repository::readFromFile()
{
	ifstream vol("volunteers.txt");
	ifstream deps("departments.txt");
	char dep = ';';
	string line;
	while (getline(deps, line)) {
		istringstream ss(line);
		string name, desc;
		getline(ss, name, dep);
		getline(ss, desc, dep);
		Department* dep = new Department(name, desc);
		departments.push_back(dep);
	}
	while (getline(vol, line)) {
		istringstream ss(line);
		string name, email, department, listt;
		getline(ss, name, dep);
		getline(ss, email, dep);
		getline(ss, department, dep);
		getline(ss, listt, dep);
		istringstream su(listt);
		vector<string> interests;
		string inter;
		while (getline(su, inter, ',')) {
			interests.push_back(inter);
		}
		Volunteer* volunteer = new Volunteer(name, email, interests, department);
		volunteers.push_back(volunteer);
	}

	vol.close();
	deps.close();
}

void Repository::writeToFile()
{
	ofstream vol("volunteers.txt");
	for (Volunteer* volunteer : volunteers) {
		vol << volunteer->toFile();
	}
	vol.close();
}