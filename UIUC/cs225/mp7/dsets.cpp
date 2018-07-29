#include "dsets.h"


void DisjointSets::addelements(int num) 	
{
	for(int i = 0; i<num; i++)
		stuff.push_back(-1);
}


int DisjointSets::find(int elem) 	
{
	if(stuff[elem] < 0)
		return elem;
	else
		return find(stuff[elem]);
}


void DisjointSets::setunion(int a, int b) 	
{

	int aa = find(a);
	int bb = find(b);
	if(aa == bb)
		return;
	int newSize = stuff[aa] + stuff[bb];
	if(stuff[aa]>=stuff[bb])
	{
		stuff[bb] = a;
		stuff[aa] = newSize;
	}	
	else
	{
		stuff[aa] = b;
		stuff[bb] = newSize;
	}
}
