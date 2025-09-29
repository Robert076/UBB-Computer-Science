class Observer
{
public:
    virtual void update() = 0;
};

class ChatWindow : public Observer
{

};  