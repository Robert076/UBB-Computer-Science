#include <string>
using namespace std;
class Task
{
private:
    string name;
    int time, prio;
public:
    Task(string name, int time, int prio);
    string getName();
    int getTime();
    int getPrio();
};