#include "../include/task.hpp"
#include <string>

using namespace std;

Task::Task(string name, int time, int prio)
{
    this->name = name;
    this->time = time;
    this->prio = prio;
}

string Task::getName()
{
    return this->name;
}

int Task::getPrio()
{
    return this->prio;
}

int Task::getTime()
{
    return this->time;
}