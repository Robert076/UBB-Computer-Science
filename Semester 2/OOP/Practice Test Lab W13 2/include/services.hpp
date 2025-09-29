#include "../include/repo.hpp"

class Services
{
private:
    Repository repo;
public:
    Services();
    vector<Task> getTasks();
};