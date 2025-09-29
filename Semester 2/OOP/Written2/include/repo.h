#include <vector>
#include <fstream>
using namespace std;

class Repo
{
private:
    vector<int> data;
public:
    Repo(){};
    vector<int> getNumbers()
    {
        data.clear();
        ifstream file("data.txt");
        int number;
        while (file >> number)
        {
            data.push_back(number);
        }
        return data;
    }
    void addNumber(int number)
    {
        data.push_back(number);
        ofstream file("data.txt");
        for (int i = 0; i < data.size(); i++)
        {
            file << data[i] << " ";
        }
    }
};