#include "../include/repo.hpp"
#include <vector>
#include <fstream>
#include <sstream>
#include <cstring>
#include <string>
using namespace std;

Repository::Repository()
{
    this->tasks = vector<Task>();
}

vector<Task> Repository::getTasks()
{
    ifstream fin("../tasks.txt");
    string line;
    this->tasks.clear();
    while(getline(fin, line))
    {
        // file is : name | time | prio
        stringstream ss(line);
        string name;
        int time, prio;
        getline(ss, name, '|');
        string timeStr;
        getline(ss, timeStr, '|');
        time = stoi(timeStr);
        string prioStr;
        getline(ss, prioStr, '|');
        prio = stoi(prioStr);
        Task t = Task(name, time, prio);
        this->tasks.push_back(t);
    }
    sort(this->tasks.begin(), this->tasks.end(), [](Task a, Task b) { return a.getPrio() > b.getPrio(); });
    return this->tasks;
}