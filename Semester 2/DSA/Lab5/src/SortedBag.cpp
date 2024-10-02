#include "../include/SortedBag.h"
#include "../include/SortedBagIterator.h"

SortedBag::SortedBag(Relation r) {
    this->r = r;
    this->siz = 0; // Correct assignment
    root = nullptr;
}

void SortedBag::add(TComp e) {
    // Create a new node
    BSTNode* newNode = new BSTNode;
    newNode->info = e;
    newNode->left = nullptr;
    newNode->right = nullptr;

    if (root == nullptr) {
        root = newNode;
        siz++; // Increment size
        return;
    }

    BSTNode* current = root;
    while (true) {
        if (r(e, current->info)) {
            if (current->left == nullptr) {
                current->left = newNode;
                siz++; // Increment size
                return;
            }
            current = current->left;
        }
        else {
            if (current->right == nullptr) {
                current->right = newNode;
                siz++; // Increment size
                return;
            }
            current = current->right;
        }
    }
}

bool SortedBag::remove(TComp e) {
    BSTNode* current = root;
    BSTNode* parent = nullptr;

    while (current != nullptr) {
        if (current->info == e) {
            break;
        }
        parent = current;
        if (r(e, current->info)) {
            current = current->left;
        }
        else {
            current = current->right;
        }
    }

    if (current == nullptr) {
        return false; // Element not found, cannot remove
    }

    if (current->left == nullptr || current->right == nullptr) {
        BSTNode* newCurrent = (current->left != nullptr) ? current->left : current->right;

        if (parent == nullptr) {
            root = newCurrent;
        }
        else {
            if (parent->left == current) {
                parent->left = newCurrent;
            }
            else {
                parent->right = newCurrent;
            }
        }
        delete current;
    }
    else {
        BSTNode* mostLeft = current->right;
        BSTNode* mostLeftParent = current;
        while (mostLeft->left != nullptr) {
            mostLeftParent = mostLeft;
            mostLeft = mostLeft->left;
        }

        current->info = mostLeft->info;
        if (mostLeftParent->left == mostLeft) {
            mostLeftParent->left = mostLeft->right;
        }
        else {
            mostLeftParent->right = mostLeft->right;
        }
        delete mostLeft;
    }

    siz--; // Decrement size after successful removal
    return true; // Removal successful
}

bool SortedBag::search(TComp elem) const {
    BSTNode* current = root;
    while (current != nullptr) {
        if (current->info == elem) {
            return true;
        }
        else if (r(current->info, elem)) {
            current = current->right;
        }
        else {
            current = current->left;
        }
    }
    return false;
}

int SortedBag::nrOccurrences(TComp elem) const {
    if (root == nullptr) {
        return 0; // If the tree is empty, return 0
    }

    int count = 0;
    BSTNode* current = root;
    std::stack<BSTNode*> nodeStack;

    // In-order traversal to count occurrences of elem
    while (!nodeStack.empty() || current != nullptr) {
        if (current != nullptr) {
            nodeStack.push(current);
            current = current->left;
        } else {
            current = nodeStack.top();
            nodeStack.pop();

            if (current->info == elem) {
                count++;
            }

            current = current->right;
        }
    }

    return count;
}


int SortedBag::size() const {
    return this->siz;
}

bool SortedBag::isEmpty() const {
    return this->siz == 0;
}

SortedBagIterator SortedBag::iterator() const {
    return SortedBagIterator(*this);
}

SortedBag::~SortedBag() {
    destroyTree(root);
}

void SortedBag::destroyTree(BSTNode* node) {
    if (node != nullptr) {
        destroyTree(node->left);
        destroyTree(node->right);
        delete node;
    }
}
