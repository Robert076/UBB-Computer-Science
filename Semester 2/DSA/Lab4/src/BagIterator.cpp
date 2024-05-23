#include <exception>
#include "../include/BagIterator.h"
#include "../include/Bag.h"

using namespace std;

BagIterator::BagIterator(const Bag &c) : bag(c)
{
	this->array = new TElem[c.m];
	int pos = 0;
	for (int i = 0; i < c.capacity; i++)
		if (c.hashtable[i] != -111111 && c.hashtable[i] != DELETED)
			this->array[pos++] = c.hashtable[i];
	if(this->bag.m > 0)
		this->current = 0;
	else
		this->current = -1;
}

void BagIterator::first()
{
	this->current = 0;
}

void BagIterator::next()
{
	if (!this->valid())
		throw exception();
	this->current++;
}

bool BagIterator::valid() const
{
	return this->current >= 0 && this->current < this->bag.m;
}

TElem BagIterator::getCurrent() const
{
	if(!this->valid())
		throw exception();
	return this->array[this->current];
}
