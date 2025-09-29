#include "../include/gui.hpp"

GUI::GUI(Services& services) : services(services)
{
    buildGUI();
    populateTasks();
}

void GUI::buildGUI()
{
    QVBoxLayout* mainLayout = new QVBoxLayout{this};
    setLayout(mainLayout);

    tasksList = new QListWidget();
    mainLayout->addWidget(tasksList);

    prioOne = new QPushButton();
    prioOne->setText("Show prio 1 tasks");
    connect(prioOne, &QPushButton::clicked, this, &GUI::showPrioOne);
    mainLayout->addWidget(prioOne);

    prioEdit = new QLineEdit();
    mainLayout->addWidget(prioEdit);
    filterButton = new QPushButton();
    filterButton->setText("Show duration");
    connect(filterButton, &QPushButton::clicked, this, &GUI::filterPrio);
    mainLayout->addWidget(filterButton);

    populateTasks();
}

void GUI::populateTasks()
{
    vector<Task> v = this->services.getTasks();
    tasksList->clear();
    for(auto task : v)
    {
        QListWidgetItem* taskItem = new QListWidgetItem(QString::fromStdString(task.getName() + " | " + to_string(task.getTime()) + " | " + to_string(task.getPrio())));
        if(task.getPrio() == 1)
        taskItem->setFont(QFont("Arial", 12, QFont::Bold));
        tasksList->addItem(taskItem);
    }
}

void GUI::showPrioOne()
{
    vector<Task> v = this->services.getTasks();
    tasksList->clear();
    for(auto task : v)
    {
        if(task.getPrio() == 1)
        {
            QListWidgetItem* taskItem = new QListWidgetItem(QString::fromStdString(task.getName() + " | " + to_string(task.getTime()) + " | " + to_string(task.getPrio())));
            tasksList->addItem(taskItem);
        }
    }
}

void GUI::filterPrio()
{
    vector<Task> v = this->services.getTasks();
    tasksList->clear();
    if(prioEdit->text() != "")
        {int prio = stoi(prioEdit->text().toStdString());
        for(auto task : v)
        {
            if(task.getPrio() == prio)
            {
                QListWidgetItem* taskItem = new QListWidgetItem(QString::fromStdString(task.getName() + " | " + to_string(task.getTime())));
                tasksList->addItem(taskItem);
            }
        }
    }
    else
    {
        for(auto task : v)
        {
            QListWidgetItem* taskItem = new QListWidgetItem(QString::fromStdString(task.getName() + " | " + to_string(task.getTime()) + " | " + to_string(task.getPrio())));
            tasksList->addItem(taskItem);
        }
    }
}