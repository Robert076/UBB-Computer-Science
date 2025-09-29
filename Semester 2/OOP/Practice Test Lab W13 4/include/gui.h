#include "../include/services.h"
#include <algorithm>
#include <QWidget>
#include <cstring>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QPushButton>
#include <QListWidget>
#include <QListWidgetItem>

bool cmp(Disease a, Disease b)
{
    return a.getCategory() <= b.getCategory();
}

class GUI : public QWidget
{
    private:
        Services& services;
        QListWidget* diseaseList;
    public:
        GUI(Services& services) : services(services)
        {
            buildGUI();
            populateDiseases();
        }
        void buildGUI()
        {
            QVBoxLayout* layout = new QVBoxLayout();
            setLayout(layout);

            diseaseList = new QListWidget();
            layout->addWidget(diseaseList);

            QLineEdit* keyboardInput = new QLineEdit();
            layout->addWidget(keyboardInput);
            connect(keyboardInput, &QLineEdit::textChanged, [=]() {
                diseaseList->clear();
                std::vector<Disease> diseases = this->services.getDiseases();
                for(auto d : diseases)
                    if(strstr(d.getCategory().c_str(), keyboardInput->text().toStdString().c_str()) || strstr(d.getName().c_str(), keyboardInput->text().toStdString().c_str()))
                        diseaseList->addItem(QString::fromStdString(d.getCategory() + " | " + d.getName() + " | " + d.getSymptoms()));

            });

            QPushButton* searchButton = new QPushButton("Show symptoms");
            layout->addWidget(searchButton);
            connect(searchButton, &QPushButton::clicked, [=]() {
                std::string keyword = keyboardInput->text().toStdString();
                bool fnd = false;
                for(auto d : this->services.getDiseases())
                {
                    if(d.getName() == keyword)
                    {
                        diseaseList->clear();
                        fnd = true;
                        std::string symptoms = d.getSymptoms();
                        // display one by one
                        for(int i = 0; i < symptoms.size(); i++)
                        {
                            std::string s = "";
                            while(i < symptoms.size() && symptoms[i] != ',')
                            {
                                s += symptoms[i];
                                i++;
                            }
                            diseaseList->addItem(QString::fromStdString(s));
                        }
                    }
                }
                if(!fnd)
                {
                    diseaseList->clear();
                    diseaseList->addItem(QString::fromStdString("No disease found ! Error !"));
                }
            });
        }
        void populateDiseases()
        {
            std::vector<Disease> diseases = this->services.getDiseases();
            
            std::sort(diseases.begin(), diseases.end(), cmp);
            for(auto disease : diseases)
            {
                diseaseList->addItem(QString::fromStdString(disease.getCategory() + " | " + disease.getName() + " | " + disease.getSymptoms()));
            }
        }

};