#include <QApplication>
#include "../include/window.h"

int main(int argc, char** argv)
{
    QApplication app(argc, argv);
    Services services{};
    vector<Window*> windows;
    for(int i = 0; i < 3; i++)
    {
        Window* window = new Window(services);
        window->show();
        windows.push_back(window);
    }
    return app.exec();
}