#pragma once
#include "MultiMap.h"

class MultiMap;

class MultiMapIterator
{
	friend class MultiMap;

private:
	const MultiMap& col;
	//TODO - Representation
<<<<<<< HEAD
	int* current;
=======
	int currentElement;
>>>>>>> f589eefe6cb250fc0ac333a85800b6c66d9c1614
	//DO NOT CHANGE THIS PART
	MultiMapIterator(const MultiMap& c);

public:
	TElem getCurrent()const;
	bool valid() const;
	void next();
	void first();
};

