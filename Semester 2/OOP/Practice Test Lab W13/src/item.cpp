#include "../include/item.hpp"

Item::Item(string category, string name, int quantity)
{
    this->name = name;
    this->category = category;
    this->quantity = quantity;
}

string Item::getName()
{
    return this->name;
}

string Item::getCategory()
{
    return this->category;
}

int Item::getQuantity()
{
    return this->quantity;
}