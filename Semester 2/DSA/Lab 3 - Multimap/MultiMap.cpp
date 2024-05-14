#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;


MultiMap::MultiMap() {
	head.next = nullptr;
	head.value = NULL_TELEM;
}


void MultiMap::add(TKey c, TValue v) {
	node* newNode = new node;
    newNode->value = TElem(c, v);
    newNode->next = nullptr;

	if(this->head.value == NULL_TELEM)
	{
		this->head = *newNode;
	}
	else
	{
		node* current = &this->head;
		while(current->next != nullptr)
			current = current->next;
		current->next = newNode;
	}
}

bool MultiMap::remove(TKey c, TValue v) {
    if (this->head.value == NULL_TELEM) {
        return false;
    }
    
    bool removed = false;

    // Handle removal of head node
    if (this->head.value.first == c && this->head.value.second == v) {
        if (this->head.next == nullptr) {
            this->head.value = NULL_TELEM;
            return true;
        } else {
            node* toDelete = this->head.next;
            this->head = *this->head.next;
            delete toDelete;
            return true;
        }
    }

    // Handle removal of nodes after head
    node* current = &this->head;
    while (current->next != nullptr) {
        if (current->next->value.first == c && current->next->value.second == v) {
            node* toDelete = current->next;
            current->next = current->next->next;
            delete toDelete;
            return true;
        } else {
            current = current->next;
        }
    }

    return removed;
}


vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue> result;
	const node* current = &this->head;
	while(current != nullptr)
	{
		if(current->value.first == c)
			result.push_back(current->value.second);
		current = current->next;
	}
	return result;
}


int MultiMap::size() const {
	int size = 0;
	if(this->head.value != NULL_TELEM)
	{
		const node* current = &this->head;
		size = 1;
		while(current->next != nullptr)
		{
			current = current->next;
			++size;
		}
		return size;
	}
	return 0;
}


bool MultiMap::isEmpty() const {
	if(this->head.value == NULL_TELEM)
		return true;
	return false;
}

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}


MultiMap::~MultiMap() {
    node* current = this->head.next; // Start from the first actual node, not the head
    while(current != nullptr)
    {
        node* toDelete = current;
        current = current->next;
        delete toDelete;
    }
}

