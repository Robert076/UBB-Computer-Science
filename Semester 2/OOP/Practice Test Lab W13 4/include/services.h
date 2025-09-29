#include "../include/repo.h"

class Services
{
    private:
        Repository repo;
    public:
        Services()
        {
            this->repo = Repository();
        }
        std::vector<Disease> getDiseases()
        {
            return this->repo.getDiseases();
        }
};