class Observer
{
public:
    ~Observer(){};
    virtual void update() = 0;
};