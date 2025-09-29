#include "../include/repo.hpp"
#include <fstream>
#include <cstring>
#include <sstream>
#include <iostream>
using namespace std;
Repository::Repository()
{
    this->items = vector<Item>();
}

bool Repository::deleteItem(string name)
{
    this->getItems();
    for (int i = 0; i < this->items.size(); i++)
    {
        if (this->items[i].getName() == name)
        {
            this->items.erase(this->items.begin() + i);
            ofstream file("../items.txt");
            for (Item item : this->items)
            {
                file << item.getCategory() << "|" << item.getName() << "|" << item.getQuantity() << endl;
            }
            return true;
        }
    }
    return false;
}

vector<Item> Repository::getItems()
{
    ifstream file("../items.txt");
    string line;
    this->items.clear();
    while (getline(file, line))
    {
        stringstream ss(line);
        string category, name;
        int quantity;
        getline(ss, category, '|');
        getline(ss, name, '|');
        ss >> quantity;
        this->items.push_back(Item(category, name, quantity));
    }
    return this->items;
}
