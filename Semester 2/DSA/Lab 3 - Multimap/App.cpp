#include <iostream>
#include "MultiMap.h"
#include "ExtendedTest.h"
#include "ShortTest.h"
#include "MultiMapIterator.h"

using namespace std;


int main() {
	MultiMap m;
	m.add(1, 10);
	m.add(2, 20);
	m.add(3, 30);
	MultiMapIterator it = m.iterator();
	it.first();
	it.next();
	it.next();

	TElem e = it.getCurrent();
	cout << e.first << " " << e.second << endl;
	testAll();
	testAllExtended();
	cout << "End" << endl;
	return 0;
}
