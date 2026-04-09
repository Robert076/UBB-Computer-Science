template <typename T>
class Future
{
    list<function<void(T)>> continuations;
    T val;
    bool hasValue;
    mutex mtx;

public:
    void set(T v)
    {
        unique_lock<mutex> lck(mtx);
        hasValue = true;
        val = v;
        lck.unlock();
        for (function<void(T)> &f : continuations)
        {
            f(v);
        }
        continuations.clear();
    }

    void addContinuation(function<void(T)> f)
    {
        if (hasValue)
        {
            f(val);
        }
        else
        {
            unique_lock<mutex> lck(mtx);
            continuations.push_back(f);
        }
    }
}