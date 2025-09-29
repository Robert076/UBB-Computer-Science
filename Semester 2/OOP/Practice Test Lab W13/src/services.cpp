#include "../include/services.hpp"

Services::Services()
{
    this->repository = Repository();
}

vector<Item> Services::getItems()
{
    return this->repository.getItems();
}

bool Services::deleteItem(string name)
{
    return this->repository.deleteItem(name);
}