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
<<<<<<< HEAD
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
=======
	for(int i = 0; i < this->capacity - 1; i++)
		this->next[i] = i + 1;
	this->next[capacity - 1] = -1;
	this->firstEmpty = 0;
}

void MultiMap::add(TKey c, TValue v) {
	if(this->firstEmpty == -1)
	{
		TElem* newElems = new TElem[this->capacity * 2];
		int* newNext = new int[this->capacity * 2];
		for(int i = 0; i < this->capacity; i++)
		{
			newElems[i] = this->elems[i];
			newNext[i] = this->next[i];
		}
		for(int i = this->capacity; i < this->capacity * 2 - 1; i++)
		{
			newNext[i] = i + 1;
		}
		newNext[this->capacity * 2 - 1] = -1;
		delete[] this->elems;
		delete[] this->next;

		this->elems = newElems;
		this->next = newNext;
		this->firstEmpty = this->capacity;
		this->capacity *= 2;
>>>>>>> f589eefe6cb250fc0ac333a85800b6c66d9c1614
	}
	int newPosition = this->firstEmpty;
	this->elems[newPosition] = TElem(c, v);
	this->firstEmpty = this->next[firstEmpty];
	this->next[newPosition] = this->head;
	this->head = newPosition;
}

bool MultiMap::remove(TKey c, TValue v) {
<<<<<<< HEAD
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
=======
	int nodC = this->head;
	int prevNode = -1;
	while(nodC != -1 && this->elems[nodC] != TElem(c, v))
	{
		prevNode = nodC;
		nodC = this->next[nodC];
	}
	if(nodC != -1)
	{
		if(nodC == this->head)
			this->head = this->next[head];
		else
			this->next[prevNode] = this->next[nodC];
		this->next[nodC] = this->firstEmpty;
		this->firstEmpty = nodC;
	}
	else
	{
		return false;
	}
	return true;
>>>>>>> f589eefe6cb250fc0ac333a85800b6c66d9c1614
}


vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue> values;
<<<<<<< HEAD
	int current = this->head;
	while (current != -1)
	{
		if (this->elems[current].first == c)
		{
			values.push_back(this->elems[current].second);
		}
		current = this->next[current];
=======
	int nodC = this->head;
	while(nodC != -1)
	{
		if(this->elems[nodC].first == c)
			values.push_back(this->elems[nodC].second);
		nodC = this->next[nodC];
>>>>>>> f589eefe6cb250fc0ac333a85800b6c66d9c1614
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
<<<<<<< HEAD
=======
	return size;
>>>>>>> f589eefe6cb250fc0ac333a85800b6c66d9c1614
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

