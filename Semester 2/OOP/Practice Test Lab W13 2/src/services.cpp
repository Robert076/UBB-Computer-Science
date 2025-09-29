#include "../include/services.hpp"
#include <vector>
using namespace std;

Services::Services()
{
    this->repo = Repository();
}

vector<Task> Services::getTasks()
{
    return this->repo.getTasks();
}