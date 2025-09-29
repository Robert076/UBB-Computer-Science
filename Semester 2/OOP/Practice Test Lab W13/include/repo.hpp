#include "../include/item.hpp"
#include <vector>
#include <assert.h>


class Repository
{
private:
    vector<Item> items;
public:
    Repository();
    bool deleteItem(string name);
    vector<Item> getItems();
};