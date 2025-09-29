#include "../include/gui.hpp"
#include <iostream>

using namespace std;

int main(int argc, char **argv)
{
    QApplication app(argc, argv);
    QCoreApplication::setApplicationName("Test lab 13");
    Services services;
    GUI gui = GUI(services);
    gui.show();
    return app.exec();
}