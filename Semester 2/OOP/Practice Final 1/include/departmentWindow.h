#include "observer.h"
#include "services.h"
#include <QWidget>
#include <QLabel>
#include <QVBoxLayout>
#include <QListWidget>
#include <QListWidgetItem>

class DepartmentWindow : public QWidget, public Observer
{
    Q_OBJECT
private:
    Services& services;
    Department department;
public:
    DepartmentWindow(Services& services, Department dep) : services{services}, department{dep} 
    {
        services.registerObserver(this);
        this->setWindowTitle(QString::fromStdString(department.getName()));
        QLabel* description = new QLabel(QString::fromStdString(department.getDescription()));
        QVBoxLayout* layout = new QVBoxLayout();
        layout->addWidget(description);
        QListWidget* volunteers = new QListWidget();
        for (auto volunteer : services.getVolunteers())
        {
            if(volunteer.getDepartmentName() == department.getName())
            {
                QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(volunteer.getName()));
                volunteers->addItem(item);
            }
        }
        layout->addWidget(volunteers);
        this->setLayout(layout);
    } 
    void update() override
    {

    }  
};