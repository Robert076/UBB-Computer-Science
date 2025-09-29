#include <vector>
#include <fstream>
#include <sstream>
#include <cstring>
#include <iostream>
#include "../include/domain.h"

class Repository
{
    private:
        std::vector<Disease> diseases;
    public:
        Repository()
        {
            this->diseases = std::vector<Disease>();
        }    
        std::vector<Disease> getDiseases()
        {
            diseases.clear();
            std::ifstream fin("../data.txt");
            std::string line;
            while(std::getline(fin, line))
            {
                std::string name, category, symptoms;
                std::stringstream ss(line);
                std::getline(ss, name, '|');
                std::getline(ss, category, '|');
                std::getline(ss, symptoms);
                Disease d(name, category, symptoms);
                this->diseases.push_back(d);
            }
            return this->diseases;
        }
};