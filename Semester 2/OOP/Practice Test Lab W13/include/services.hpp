#include "../include/repo.hpp"
class Services
{
private:
    Repository repository;

public:
    Services();
    
    bool deleteItem(string name);
    vector<Item> getItems();
};