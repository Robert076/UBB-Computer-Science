#include "../include/Bag.h"
#include "../include/BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;

Bag::Bag()
{
	this->m = 0;
	this->capacity = 16902;
	this->hashtable = new TElem[this->capacity];
	this->alpha = 0.7;
	for (int i = 0; i < this->capacity; i++)
		this->hashtable[i] = -111111;
}

int Bag::hash(TElem elem, int i) const
{
	int hash1 = (elem >> 16) % this->capacity;
	int hash2 = 1 + (abs(elem) % (this->capacity - 1));
	return (hash1 + i * hash2) % this->capacity;
}

void Bag::add(TElem elem)
{

	int i = 0;
	while (i < this->capacity && this->hashtable[hash(elem, i)] != -111111)
		i++;
	if (i == this->capacity || this->getAlphaFactor() >= alpha)
		this->resize();

	this->hashtable[hash(elem, i)] = elem;
	this->m++;
}

void Bag::resize()
{
	int oldCapacity = this->capacity;
	this->capacity *= 2;
	TElem *newHashtable = new TElem[this->capacity];
	for (int i = 0; i < this->capacity; i++)
		newHashtable[i] = -111111;
	for (int i = 0; i < oldCapacity; i++)
		if (this->hashtable[i] != -111111 && this->hashtable[i] != DELETED)
		{
			int elem = this->hashtable[i];
			int j = 0;
			while (newHashtable[hash(elem, j)] != -111111)
				j++;
			newHashtable[hash(elem, j)] = elem;
		}
	delete[] this->hashtable;
	this->hashtable = newHashtable;
}

double Bag::getAlphaFactor()
{
	return (double)(this->m + 1) / this->capacity;
}

bool Bag::remove(TElem elem)
{
	int i = 0;
	while (this->hashtable[hash(elem, i)] != -111111)
	{
		if (this->hashtable[hash(elem, i)] == elem)
		{
			this->hashtable[hash(elem, i)] = DELETED;
			this->m--;
			return true;
		}
		i++;
	}
	return false;
}

bool Bag::search(TElem elem) const
{
	int i = 0;
	while (this->hashtable[hash(elem, i)] != -111111)
	{
		if (this->hashtable[hash(elem, i)] == elem)
			return true;
		i++;
	}
	return false;
}

int Bag::nrOccurrences(TElem elem) const
{
	int count = 0, i = 0;
	while (this->hashtable[hash(elem, i)] != -111111)
	{
		if (this->hashtable[hash(elem, i)] == elem)
			count++;
		i++;
	}
	return count;
}

int Bag::size() const
{
	return this->m;
}

bool Bag::isEmpty() const
{
	return this->m == 0;
}

BagIterator Bag::iterator() const
{
	return BagIterator(*this);
}

Bag::~Bag()
{
	delete[] this->hashtable;
}
