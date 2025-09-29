#include "services.h"
#include <QWidget>
#include <QVBoxLayout>
#include <QLabel>
#include <QListWidget>
#include <QLineEdit>
#include <QPushButton>

class Window : public QWidget, public Observer
{
    Q_OBJECT
private:
    Department d;
    Services &services;
    QVBoxLayout *layout;
    QLabel *name;
    QLabel *description;
    QListWidget *volunteers;
    QListWidget *unassignedVolunteers;
    QLineEdit *volunteerName;
    QLineEdit *volunteerEmail;
    QLineEdit *volunteerInterests;
    QPushButton *addVolunteerButton;
public:
    Window(Services &s, Department d) : services(s), d(d)
    {
        layout = new QVBoxLayout();
        name = new QLabel(QString::fromStdString(d.getName()));
        description = new QLabel(QString::fromStdString(d.getDescription()));
        volunteers = new QListWidget();
        unassignedVolunteers = new QListWidget();
        layout->addWidget(name);
        layout->addWidget(description);
        layout->addWidget(new QLabel("Volunteers:"));
        layout->addWidget(volunteers);
        layout->addWidget(new QLabel("Unassigned volunteers:"));
        layout->addWidget(unassignedVolunteers);
        volunteerName = new QLineEdit("name");
        volunteerEmail = new QLineEdit("email");
        volunteerInterests = new QLineEdit("interests");
        addVolunteerButton = new QPushButton("Add volunteer");
        connect(addVolunteerButton, &QPushButton::clicked, this, &Window::addVolunteer);
        layout->addWidget(volunteerName);
        layout->addWidget(volunteerEmail);
        layout->addWidget(volunteerInterests);
        layout->addWidget(addVolunteerButton);
        setLayout(layout);
        this->show();
    }
    void update() override
    {
        populateLists();
    }
    void populateLists()
    {
        volunteers->clear();
        unassignedVolunteers->clear();
        vector<Volunteer> v = services.getVolunteers();
        for (Volunteer &volunteer : v)
        {
            if (volunteer.getDepartmentName() == d.getName())
                volunteers->addItem(QString::fromStdString(volunteer.getName()));
            else if(volunteer.getDepartmentName() == "Unassigned")
                unassignedVolunteers->addItem(QString::fromStdString(volunteer.getName()));
        }
    }
    void addVolunteer()
    {
        Volunteer v(volunteerName->text().toStdString(), volunteerEmail->text().toStdString(), volunteerInterests->text().toStdString(), d.getName());
        services.addVolunteer(v);
        services.notify();
    }
};