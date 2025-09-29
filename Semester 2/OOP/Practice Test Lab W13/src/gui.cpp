#include "../include/gui.hpp"

GUI::GUI(Services &services) : services(services)
{
   buildGUI();
   populateItemsList();
}

void GUI::buildGUI()
{
    QVBoxLayout *mainLayout = new QVBoxLayout();
    setLayout(mainLayout);

    itemsListWidget = new QListWidget();
    mainLayout->addWidget(itemsListWidget);

    itemEdit = new QLineEdit();
    mainLayout->addWidget(itemEdit);

    deleteButton = new QPushButton("Delete");
    mainLayout->addWidget(deleteButton);
    connect(deleteButton, &QPushButton::clicked, this, &GUI::removeItem);

    filterButton = new QPushButton("Filter");
    mainLayout->addWidget(filterButton);
    connect(filterButton, &QPushButton::clicked, this, &GUI::filterItems);

    populateItemsList();
}

void GUI::populateItemsList()
{
    vector<Item> items = services.getItems();
    itemsListWidget->clear();
    for (Item item : items)
    {
        QListWidgetItem *itemItem = new QListWidgetItem(QString::fromStdString(item.getCategory() + " | " + item.getName() + " | " + to_string(item.getQuantity())));
        itemsListWidget->addItem(itemItem);
    }
}


void GUI::removeItem()
{
    string name = itemEdit->text().toStdString();
    this->services.deleteItem(name);
    populateItemsList();
}

void GUI::filterItems()
{
    string category = itemEdit->text().toStdString();
    vector<Item> items = services.getItems();
    itemsListWidget->clear();
    if(category == "")
    {
        populateItemsList();
        return;
    }
    for (Item item : items)
    {
        if (item.getCategory() == category)
        {
            QListWidgetItem *itemItem = new QListWidgetItem(QString::fromStdString(item.getCategory() + " | " + item.getName() + " | " + to_string(item.getQuantity())));
            itemsListWidget->addItem(itemItem);
        }
    }
}
