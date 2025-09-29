#include <string>

class Disease
{
    private:
        std::string category, name, symptoms;
    public:
        Disease(std::string category, std::string name, std::string symptoms)
        {
            this->category = category;
            this->name = name;
            this->symptoms = symptoms;
        }
        std::string getCategory()
        {
            return this->category;
        }
        std::string getName()
        {
            std::string newName;
            for(int i = 0; i < this->name.length(); i++)
                if(this->name[i] != ' ')
                    newName += this->name[i];
            return newName;
        }
        std::string getSymptoms()
        {
            return this->symptoms;
        }
};