#pragma once
#include "../include/SortedBag.h"
#include <stack>

class SortedBag;

class SortedBagIterator {
    friend class SortedBag;

private:
    const SortedBag& bag;
    BSTNode* currentNode; // Pointer to the current node in the traversal
    std::stack<BSTNode*> nodeStack; // Stack for iterative traversal
    SortedBagIterator(const SortedBag& b);

public:
    TComp getCurrent();
    bool valid();
    void next();
    void first();
};
