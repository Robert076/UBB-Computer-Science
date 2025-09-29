#include <string>

using namespace std;

class Item
{
private:
    string category, name;
    int quantity;
public:
    Item(string name, string category, int quantity);
    string getCategory();
    string getName();
    int getQuantity();
};