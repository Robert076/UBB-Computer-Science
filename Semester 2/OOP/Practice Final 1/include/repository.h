#pragma once
#include "volunteer.h"
#include "department.h"
#include <vector>
#include <algorithm>
#include <fstream>
#include <sstream>

class Repository
{
private:
	vector<Volunteer*> volunteers;
	vector<Department*> departments;
public:
	Repository() { readFromFile(); }
	~Repository() {
		writeToFile();
		for (Volunteer* vol : volunteers) {
			delete vol;
		}
		for (Department* dep : departments) {
			delete dep;
		}
	}

	vector<Volunteer*> getVolunteers() {
		return this->volunteers;
	}
	vector<Department*> getDepartments() { return this->departments; }
	void readFromFile();
	void writeToFile();
	void addVolunteer(Volunteer* vol) { this->volunteers.push_back(vol); }
};
