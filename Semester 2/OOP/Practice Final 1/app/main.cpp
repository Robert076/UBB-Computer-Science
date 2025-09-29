#include "../include/departmentWindow.h"
#include <QApplication>
int main(int argc, char** argv)
{
    QApplication app(argc, argv);
    Services services;
    vector<DepartmentWindow*> windows;
    for (auto department : services.getDepartments())
    {
        DepartmentWindow* window = new DepartmentWindow(services, department);
        window->show();
        windows.push_back(window);
    }
    //GUI gui(services);
    return app.exec();
}