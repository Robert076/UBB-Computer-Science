class Observer
{
public:
    virtual ~Observer() {}
    virtual void update() = 0;
};