#include "../include/gui.hpp"
#include <QApplication>
using namespace std;

int main(int argc, char** argv)
{
    QApplication app(argc, argv);
    Services services;
    GUI gui(services);
    gui.show();
    return app.exec();
}