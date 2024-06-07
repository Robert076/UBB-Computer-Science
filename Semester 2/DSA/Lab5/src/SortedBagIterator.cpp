#include "../include/SortedBagIterator.h"
#include "../include/SortedBag.h"
#include <exception>
#include <stack>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
    // Initialize the iterator by setting up for in-order traversal
    first();
}

TComp SortedBagIterator::getCurrent() {
    if (!valid()) {
        throw std::exception();
    }
    return currentNode->info;
}

bool SortedBagIterator::valid() {
    return currentNode != nullptr;
}

void SortedBagIterator::next() {
    if (!valid()) {
        throw std::exception();
    }
    
    // Move to the next node in in-order traversal
    if (currentNode->right != nullptr) {
        currentNode = currentNode->right;
        while (currentNode->left != nullptr) {
            nodeStack.push(currentNode);
            currentNode = currentNode->left;
        }
    } else if (!nodeStack.empty()) {
        currentNode = nodeStack.top();
        nodeStack.pop();
    } else {
        currentNode = nullptr;
    }
}

void SortedBagIterator::first() {
    // Initialize the iterator to the first (leftmost) node
    while (!nodeStack.empty()) {
        nodeStack.pop();
    }
    currentNode = bag.root;
    while (currentNode != nullptr && currentNode->left != nullptr) {
        nodeStack.push(currentNode);
        currentNode = currentNode->left;
    }
}
