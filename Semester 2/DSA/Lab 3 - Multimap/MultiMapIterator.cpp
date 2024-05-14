#include "MultiMapIterator.h"
#include "MultiMap.h"


MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
	//TODO - Implementation
	this->current = &this->col.head;
}

TElem MultiMapIterator::getCurrent() const{
	if(this->valid())
		return this->current->value;
	else
		throw std::exception();
}

bool MultiMapIterator::valid() const {
	//TODO - Implementation
	if(this->current != nullptr && this->current->value != NULL_TELEM)
		return true;
	return false;
}

void MultiMapIterator::next() {
	//TODO - Implementation
	if(this->valid() == false)
		throw std::exception();
	this->current = this->current->next;
}

void MultiMapIterator::first() {
	//TODO - Implementation
	this->current = &this->col.head;
}

