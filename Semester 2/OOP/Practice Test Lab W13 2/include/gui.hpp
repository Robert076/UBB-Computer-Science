#include "../include/services.hpp"
#include <QWidget>
#include <QListWidget>
#include <QLineEdit>
#include <QPushButton>
#include <QVBoxLayout>
#include <vector>
#include <QFont>
using namespace std;

class GUI : public QWidget
{
private:
    Services services;

    QListWidget* tasksList;
    QPushButton* prioOne;
    QLineEdit* prioEdit;
    QPushButton* filterButton;
public:
    GUI(Services& services);
    void buildGUI();
public slots:
    void populateTasks();
    void showPrioOne();
    void filterPrio();
};