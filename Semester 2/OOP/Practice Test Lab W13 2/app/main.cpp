#include "../include/gui.hpp"
#include <QApplication>

using namespace std;

int main(int argc, char** argv)
{
    QApplication app(argc, argv);
    QCoreApplication::setApplicationName("Lab test W13");
    Services services;
    GUI* gui = new GUI(services);
    gui->show();
    return app.exec();
}