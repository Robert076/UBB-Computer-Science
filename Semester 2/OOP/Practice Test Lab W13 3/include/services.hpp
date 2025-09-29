#include "../include/repo.hpp"

class Services
{
private:
    Repository repo;
public:
    Services()
    {
        this->repo = Repository();
    }
    vector<Document> getMatchingDocuments(string keyword)
    {
        vector<Document> documents = this->repo.getDocuments();
        vector<Document> matchingDocuments;
        for(auto doc : documents)
        {
            if(strstr(doc.getTitle().c_str(), keyword.c_str()) != NULL)
            {
                matchingDocuments.push_back(doc);
            }
        }
        return matchingDocuments;
    }
    Document getBestMatch(string keyword)
{
    Document bestMatch("", vector<string>(), vector<string>());
    double maxCount = 0; // Initialize max count to 0 for comparison
    for(auto doc : this->repo.getDocuments())
    {
        if(doc.getTitle().find(keyword) != string::npos)
        {
            double alike = static_cast<double>(keyword.length()) / doc.getTitle().length();
            if(alike > maxCount)
            {
                maxCount = alike;
                bestMatch = doc;
            }
        }
    }
    if(bestMatch.getTitle() == "")
    {
        return Document("No match", vector<string>(), vector<string>());
    }
    return bestMatch;
}

};
