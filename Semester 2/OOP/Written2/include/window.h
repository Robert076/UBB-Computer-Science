#include "services.h"
#include <QWidget>
#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QListWidget>

class Window : public QWidget, public Observer
{
private:
    Services& services;
    QVBoxLayout* layout;
    QLabel* label;
    QLineEdit* lineEdit;
    QPushButton* button;
    QListWidget* list;
public:
    Window(Services& services) : services(services) 
    {
        this->services.registerObserver(this);
        layout = new QVBoxLayout();
        label = new QLabel("Numbers:");
        list = new QListWidget();
        update();
        lineEdit = new QLineEdit();
        button = new QPushButton("Add");
        connect(button, &QPushButton::clicked, [&]() {
            services.addNumber(lineEdit->text().toInt());
        });
        layout->addWidget(label);
        layout->addWidget(list);
        layout->addWidget(lineEdit);
        layout->addWidget(button);
        setLayout(layout);

    }
    void update()
    {
        list->clear();
        vector<int> numbers = services.getNumbers();
        for (int i = 0; i < numbers.size(); i++)
        {
            list->addItem(QString::number(numbers[i]));
        }
    }
};