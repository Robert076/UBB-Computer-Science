#include "../include/domain.hpp"
#include <fstream>
#include <vector>
#include <cstring>
#include <sstream>
#include <iostream>
using namespace std;

class Repository
{
private:
    vector<Document> documents;
public:
    Repository()
    {
        this->documents = vector<Document>();
        this->documents = this->getDocuments();
    }
    vector<Document> getDocuments()
    {
        vector<Document> documents;
        ifstream file("../data.txt");
        string line;
        while (getline(file, line))
        {
            vector<string> tokens;
            stringstream ss(line);
            string token;
            while (getline(ss, token, '|'))
            {
                tokens.push_back(token);
            }
            string title = tokens[0];
            vector<string> keywords;
            stringstream ss2(tokens[1]);
            while (getline(ss2, token, ','))
            {
                keywords.push_back(token);
            }
            vector<string> content;
            stringstream ss3(tokens[2]);
            while (getline(ss3, token, ','))
            {
                content.push_back(token);
            }
            Document document(title, keywords, content);
            documents.push_back(document);
        }
        return documents;
    }
    
};