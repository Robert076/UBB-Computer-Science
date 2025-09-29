#include "../include/services.hpp"
#include <QApplication>
#include <QPushButton>
#include <QWidget>
#include <QVBoxLayout>
#include <QListWidget>
#include <QLabel>
#include <QLineEdit>


class GUI : public QWidget
{
private:
    Services &services;


    QListWidget *itemsListWidget;

    QLineEdit *itemEdit;

    QPushButton *deleteButton;
    QPushButton *filterButton;

    void buildGUI();
    void populateItemsList();


public slots:
    void removeItem();
    void filterItems();
public:
    GUI(Services &services);
    void testDelete();
};