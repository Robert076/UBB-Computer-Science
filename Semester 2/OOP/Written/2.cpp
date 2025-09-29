#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <deque>
#include <cstring>
#include <exception>
#include <cassert>
using namespace std;

class Action
{
public:
    virtual void execute() = 0;
};

class CreateAction : public Action
{
public:
    void execute() override
    {
        cout << "Create file\n";
    }
};

class ExitAction : public Action
{
public:
    void execute() override
    {
        cout << "Exit application\n";
    }
};

class MenuItem
{
private:
    string text;
    Action *action;

public:
    MenuItem(string t, Action* a) : text{t}, action{a} {};
    void print()
    {
        cout << text << "\n";
    }
    void clicked()
    {
        cout << text << " ";
        if (action != nullptr)
            action->execute();
    }
    string getText()
    {
        return text;
    }
};

class Menu : public MenuItem
{
private:
    vector<MenuItem *> menuItems;

public:
    void add(MenuItem *m)
    {
        menuItems.push_back(m);
    }
    void print()
    {
        for (auto x : menuItems)
            cout << x->getText();
    }
};

class MenuBar
{
private:
    vector<Menu *> menus;

public:
    void add(Menu *m)
    {
        menus.push_back(m);
    }
    void print()
    {
        for (auto x : menus)
            x->print();
    }
};

int main()
{
    Menu *file, *about;
    Menu *New, *exit;
    ExitAction* e = new ExitAction; 
    exit->add(new MenuItem("test", e));
    Menu *Cpp, *Text;
    CreateAction* c = new CreateAction;
    Cpp->add(new MenuItem("test", c));
    Text->add(new MenuItem("test", c));
    New->add(Text);
    New->add(Cpp);
    file->add(New);
    file->add(exit);
    Cpp->clicked();
    return 0;
}
