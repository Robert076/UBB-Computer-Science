#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;


MultiMap::MultiMap(int capacity) {
	this->capacity = capacity;
	this->elems = new TElem[capacity];
	this->next = new int[capacity];
	this->head = -1;
	this->firstEmpty = 0;
	for (int i = 0; i < capacity - 1; i++) 
	{
		this->next[i] = -1;
	}
}

void MultiMap::resize()
{
	TElem* newElems = new TElem[this->capacity * 2];
	int* newNext = new int[this->capacity * 2];
	for (int i = 0; i < this->capacity; i++)
	{
		newElems[i] = this->elems[i];
		newNext[i] = this->next[i];
	}
	for (int i = this->capacity; i < this->capacity * 2 - 1; i++)
	{
		newNext[i] = -1;
	}
	delete[] this->elems;
	delete[] this->next;
	this->elems = newElems;
	this->next = newNext;
	this->firstEmpty = this->capacity;
	this->capacity *= 2;
}

void MultiMap::add(TKey c, TValue v) {
	if(this->size() == this->capacity)
	{
		this->resize();
	}
	if (this->head == -1)
	{
		this->elems[this->firstEmpty] = TElem(c, v);
		this->head = this->firstEmpty;
		this->firstEmpty = this->next[this->firstEmpty]; 
	}
	else
	{
		int current = this->head;
		while (this->next[current] != -1)
		{
			current = this->next[current];
		}
		this->elems[this->firstEmpty] = TElem(c, v);
		this->next[current] = this->firstEmpty;
		this->firstEmpty = this->next[this->firstEmpty];
	}
}

bool MultiMap::remove(TKey c, TValue v) {
	if (this->head == -1)
	{
		return false;
	}
	int current = this->head;
	int previous = -1;
	while (current != -1 && this->elems[current].first != c && this->elems[current].second != v)
	{
		previous = current;
		current = this->next[current];
	}
	if (current == -1)
	{
		return false;
	}
	if (current == this->head)
	{
		this->head = this->next[current];
		this->next[current] = this->firstEmpty;
		this->firstEmpty = current;
	}
	else
	{
		this->next[previous] = this->next[current];
		this->next[current] = this->firstEmpty;
		this->firstEmpty = current;
	}
	return false;
}


vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue> values;
	int current = this->head;
	while (current != -1)
	{
		if (this->elems[current].first == c)
		{
			values.push_back(this->elems[current].second);
		}
		current = this->next[current];
	}
	return values;
}


int MultiMap::size() const {
	int size = 0;
	int current = this->head;
	while(current != -1)
	{
		size++;
		current = this->next[current];
	}
}


bool MultiMap::isEmpty() const {
	return this->head == -1;
}

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}


MultiMap::~MultiMap() {
	delete[] this->elems;
	delete[] this->next;
}

