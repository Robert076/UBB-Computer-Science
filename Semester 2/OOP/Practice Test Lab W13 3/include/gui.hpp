#include "../include/services.hpp"
#include <QWidget>
#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QListWidget>
#include <QPushButton>
#include <QFormLayout>

class GUI : public QWidget 
{
private:
    Services& services;
public:
    GUI(Services& services) : services{services}
    {
        buildGUI();
        populateDocuments();
    }
    void buildGUI()
    {
        QVBoxLayout* mainLayout = new QVBoxLayout();
        setLayout(mainLayout);

        QListWidget *documentsList = new QListWidget();
        mainLayout->addWidget(documentsList);

        QLineEdit *keywordInput = new QLineEdit();
        mainLayout->addWidget(keywordInput);

        QPushButton *searchButton = new QPushButton("Search");
        mainLayout->addWidget(searchButton);
        // textChanged signal to update live search
        connect(keywordInput, &QLineEdit::textChanged, [=](){
            documentsList->clear();
            vector<Document> documents = this->services.getMatchingDocuments(keywordInput->text().toStdString());
            for(auto doc : documents)
            {
                documentsList->addItem(QString::fromStdString(doc.getTitle() + " - " + doc.getKeywords()[0] + " - " + doc.getContent()[0]));
            }
        });

        QPushButton *bestMatch = new QPushButton("Best Match");
        mainLayout->addWidget(bestMatch);
        connect(bestMatch, &QPushButton::clicked, [=](){
            documentsList->clear();
            Document d = this->services.getBestMatch(keywordInput->text().toStdString());
            documentsList->addItem(QString::fromStdString(d.getTitle() + " - " + d.getKeywords()[0] + " - " + d.getContent()[0]));
        });
    }
    void populateDocuments()
    {
        vector<Document> documents = this->services.getMatchingDocuments("");
        QListWidget *documentsList = this->findChild<QListWidget*>();
        for(auto doc : documents)
        {
            documentsList->addItem(QString::fromStdString(doc.getTitle() + " - " + doc.getKeywords()[0] + " - " + doc.getContent()[0]));
        }
    }
};