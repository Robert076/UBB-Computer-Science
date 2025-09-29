#include <string>
#include <vector>
using namespace std;

class Document
{
private:
    string title;
    vector<string> keywords;
    vector<string> content;
public:
    Document(string title, vector<string> keywords, vector<string> content)
    {
        this->title = title;
        this->keywords = keywords;
        this->content = content;
    }
    string getTitle()
    {
        return this->title;
    }
    vector<string> getKeywords()
    {
        return this->keywords;
    }
    vector<string> getContent()
    {
        return this->content;
    }
};