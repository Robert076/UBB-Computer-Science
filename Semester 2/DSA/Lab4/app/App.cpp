#include "../include/Bag.h"
#include "../include/ShortTest.h"
#include "../include/ExtendedTest.h"
#include "../include/BagIterator.h"
#include <iostream>

using namespace std;

int main()
{
	testAll();
	cout << "Short tests over" << endl;
	testAllExtended();

	cout << "All test over" << endl;
	return 0;
}