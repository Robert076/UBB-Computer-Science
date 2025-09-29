#include "../include/window.h"

#include <QApplication>

int main(int argc, char** argv)
{
    Services serv{};
    QApplication a(argc, argv);
    vector<Window*> windows;
    for(Department dep : serv.getDepartments())
    {
        Window* w = new Window(serv, dep);
        windows.push_back(w);
        w->show();
    }
    return a.exec();
}