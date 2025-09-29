#include "../include/task.hpp"
#include <vector>
using namespace std;
class Repository
{
private:
    vector<Task> tasks;
public:
    Repository();
    vector<Task> getTasks();
};