#include "MultiMapIterator.h"
#include "MultiMap.h"

MultiMapIterator::MultiMapIterator(const MultiMap& c) : col(c) {
    this->currentElement = this->col.head;
}

TElem MultiMapIterator::getCurrent() const {
    if(this->valid())
        return this->col.elems[this->currentElement];
    else
        throw exception();
}

bool MultiMapIterator::valid() const {
    if(this->currentElement != -1)
        return true;
    return false;
}

void MultiMapIterator::next() {
    if(this->valid())
        this->currentElement = this->col.next[currentElement];
    else
        throw exception();
}

void MultiMapIterator::first() {
    this->currentElement = this->col.head;
}
