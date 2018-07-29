#include <iostream>
#include <math.h> 

/**
 * @file list.cpp
 * Doubly Linked List (MP 3).
 *
 * @author Chase Geigle
 * @date (created) Fall 2011
 * @date (modified) Spring 2012, Fall 2012
 *
 * @author Jack Toole
 * @date (modified) Fall 2011
 */

/**
 * Destroys the current List. This function should ensure that
 * memory does not leak on destruction of a list.
 */
template <class T>
List<T>::~List()
{
    /// @todo Graded in MP3.1
	clear();

}

/**
 * Destroys all dynamically allocated memory associated with the current
 * List class.
 */
template <class T>
void List<T>::clear()
{
    /// @todo Graded in MP3.1
	while(head->next != NULL)
	{
		ListNode * temp = head->next;
		delete head;
		head = temp;
	}
	delete head;
}

/**
 * Inserts a new node at the front of the List.
 * This function **SHOULD** create a new ListNode.
 *
 * @param ndata The data to be inserted.
 */
template <class T>
void List<T>::insertFront(T const & ndata)
{
    /// @todo Graded in MP3.1
	if(head == NULL)
	{
		head = new ListNode(ndata);
		tail = head;
		length = 1;
	}
	else
	{
		ListNode * temp = new ListNode(ndata);
		temp -> next = head;
		head = temp;
		temp = temp->next;	
		
		temp -> prev = head;
		length++;
		//clear();
	}
}

/**
 * Inserts a new node at the back of the List.
 * This function **SHOULD** create a new ListNode.
 *
 * @param ndata The data to be inserted.
 */
template <class T>
void List<T>::insertBack( const T & ndata )
{
    /// @todo Graded in MP3.1
	if(tail == NULL)
	{
	head = new ListNode(ndata);
	tail = head;
	length = 1;
	}
	else
	{		
		ListNode * temp = new ListNode(ndata);
		tail->next = temp;
  		temp->prev = tail;
		tail = temp;
		length++;
	}
}


/**
 * Reverses the current List.
 */
template <class T>
void List<T>::reverse()
{
    reverse(head, tail);		
}

/**
 * 2: curr = (List<int>::ListNode *) 0x6690d0
 * 1: curr->data = 3
 * 
 * Helper function to reverse a sequence of linked memory inside a List,
 * starting at startPoint and ending at endPoint. You are responsible for
 * updating startPoint and endPoint to point to the new starting and ending
 * points of the rearranged sequence of linked memory in question.
 *
 * @param startPoint A pointer reference to the first node in the sequence
 *  to be reversed.
 * @param endPoint A pointer reference to the last node in the sequence to
 *  be reversed.
 */
template <class T>
void List<T>::reverse( ListNode * & startPoint, ListNode * & endPoint )
{	
	
	int i = 1;
    /// @todo Graded in MP3.1
	if(startPoint != NULL && endPoint != NULL)
	{
		ListNode * worker = startPoint;
		
			while(worker != NULL && worker != endPoint)
			{	
		
				ListNode * temp = worker->next;
								
				worker->next = worker->prev;
				worker->prev = temp;
				
				
							
				
				worker = worker->prev;			
		
				//std::cout<<  << <<  endl;
			}
			if(worker != NULL)
			{
				ListNode * temp = worker->next;
								
				worker->next = worker->prev;
				worker->prev = temp;
			}
		
			ListNode * temp1 = startPoint;
			startPoint = endPoint;
			endPoint = temp1;
			
			temp1 = startPoint->prev;
			startPoint->prev = endPoint->next;
			endPoint->next = temp1;
			//std::cout << "nope it came out to play ";
			if(temp1 != NULL)
				temp1->prev = endPoint;
			temp1 = startPoint->prev;
			if(startPoint->prev != NULL)
				 temp1->next = startPoint;
			//endPoint = endPoint->next;
		
	}
}

/*template <class T>
ListNode List<T>::findN(ListNode & start, int j)
{
	for(int i=1; i<j; i++)
	{
		if(start->next == NULL)
			return NULL;
		else
			start = start->next;
	}
	return start;
}*/


/**
 * Reverses blocks of size n in the current List. You should use your
 * reverse( ListNode * &, ListNode * & ) helper function in this method!
 *
 * @param n The size of the blocks in the List to be reversed.
 */
template <class T>
void List<T>::reverseNth( int n )
{
    /// @todo Graded in MP3.1
	ListNode * initial = head;
	ListNode * end = initial;
    for(int i = 0; i < (double)length/(double)n; i++)
    {	std::cout<< i << endl;
		if(i != 0)
			{
			if(end->next!=NULL)
				end = end->next;
				initial = end;
			}	
		for(int j=1; j<n; j++)
		{	
				
			if(end->next != NULL)	
				end = end->next;
		}
   		if(initial == head && end == tail)
			reverse(head, tail);
		else if(initial == head)
			reverse(head, end);
		else if(end == tail)
			reverse(initial, tail);
		else
			reverse(initial, end);
    }
	//if(end != tail && end != NULL)
		//reverse(end->next, tail);
	//std::cout<< "here now" << endl;
}


/**
 * Modifies the List using the waterfall algorithm.
 * Every other node (starting from the second one) is removed from the
 * List, but appended at the back, becoming the new tail. This continues
 * until the next thing to be removed is either the tail (**not necessarily
 * the original tail!**) or NULL.  You may **NOT** allocate new ListNodes.
 * Note that since the tail should be continuously updated, some nodes will
 * be moved more than once.
 */
template <class T>
void List<T>::waterfall()
{
    /// @todo Graded in MP3.1
    int i = 0;
    ListNode * worker1 = head;
    ListNode * worker2 = worker1;
    do 
    {
	if(worker1 != NULL)
		if(worker2 != NULL)
		{	
			worker2 = worker1->next;
			if(worker2->next != NULL)
			{
				worker1->next = worker2->next;
				worker2->next->prev = worker1;
				insertBack(worker2->data);
			}
			else
				i=1;
		}
		else
			i=1;
	else
		i=1;
	worker1 = worker1->next;
    }while(i == 0);
}

/**
 * Splits the given list into two parts by dividing it at the splitPoint.
 *
 * @param splitPoint Point at which the list should be split into two.
 * @return The second list created from the split.
 */
template <class T>
List<T> List<T>::split(int splitPoint)
{
    if (splitPoint > length)
        return List<T>();

    if (splitPoint < 0)
        splitPoint = 0;

    ListNode * secondHead = split(head, splitPoint);

    int oldLength = length;
    if (secondHead == head)
    {
        // current list is going to be empty
        head = NULL;
        tail = NULL;
        length = 0;
    }
    else
    {
        // set up current list
        tail = head;
        while (tail->next != NULL)
            tail = tail->next;
        length = splitPoint;
    }

    // set up the returned list
    List<T> ret;
    ret.head = secondHead;
    ret.tail = secondHead;
    if (ret.tail != NULL)
    {
        while (ret.tail->next != NULL)
            ret.tail = ret.tail->next;
    }
    ret.length = oldLength - splitPoint;
    return ret;
}

/**
 * Helper function to split a sequence of linked memory at the node
 * splitPoint steps **after** start. In other words, it should disconnect
 * the sequence of linked memory after the given number of nodes, and
 * return a pointer to the starting node of the new sequence of linked
 * memory.
 *
 * This function **SHOULD NOT** create **ANY** new List objects!
 *
 * @param start The node to start from.
 * @param splitPoint The number of steps to walk before splitting.
 * @return The starting node of the sequence that was split off.
 */
template <class T>
typename List<T>::ListNode * List<T>::split(ListNode * start, int splitPoint)
{
    /// @todo Graded in MP3.2
for(int i = 0; i<splitPoint; i++)
{
	if(start->next!=NULL)
		start = start->next;
}
ListNode * helper = start;
if(helper->prev !=NULL)
{
	helper=helper->prev;
	helper->next = NULL;
}
ListNode * news;
news = start;

news->prev = NULL;

return news;
}

/**
 * Merges the given sorted list into the current sorted list.
 *
 * @param otherList List to be merged into the current list.
 */
template <class T>
void List<T>::mergeWith(List<T> & otherList)
{
    // set up the current list
    head = merge(head, otherList.head);
    tail = head;

    // make sure there is a node in the new list
    if(tail != NULL)
    {
        while (tail->next != NULL)
            tail = tail->next;
    }
    length = length + otherList.length;

    // empty out the parameter list
    otherList.head = NULL;
    otherList.tail = NULL;
    otherList.length = 0;
}

/**
 * Helper function to merge two **sorted** and **independent** sequences of
 * linked memory. The result should be a single sequence that is itself
 * sorted.
 *
 * This function **SHOULD NOT** create **ANY** new List objects.
 *
 * @param first The starting node of the first sequence.
 * @param second The starting node of the second sequence.
 * @return The starting node of the resulting, sorted sequence.
 */
template <class T>
typename List<T>::ListNode * List<T>::merge(ListNode * first, ListNode * second)
{
    /// @todo Graded in MP3.2
	ListNode * both;
	ListNode * helper;

	if(first == NULL && second == NULL)
		return NULL;
	if(first == NULL)
		return second;
	if(second == NULL)
		return first;
	if(first < second)
	{
		both = first;
		first = first->next;
		first->prev=NULL;
	}	
	else 
	{
		both = second;
		second = second->next;
		second->prev = NULL;
	}
	if(both->prev != NULL)
		both->prev = NULL;

	ListNode * walker = both;
	
	while(first!=NULL || second != NULL)
	{	

		if(first!=NULL && first < second)
		{
			walker->next = first;
			first = first->next;
			if(first != NULL && first->prev != NULL)
				first->prev = NULL;
			walker = walker->next; 
			if(walker->next != NULL)
				walker->next = NULL;
		}
		else
		{
			walker->next = second;
			second = second->next;
			if(second != NULL && second->prev != NULL)				
				second->prev = NULL;
			walker = walker->next;
			if(walker ->next != NULL)
				walker->next = NULL;  
		}

	}
    return both; // change me!
}

/**
 * Sorts the current list by applying the Mergesort algorithm.
 */
template <class T>
void List<T>::sort()
{
    if (empty())
        return;
    head = mergesort(head, length);
    tail = head;
    while (tail->next != NULL)
        tail = tail->next;
}

/**
 * Sorts a chain of linked memory given a start node and a size.
 * This is the recursive helper for the Mergesort algorithm (i.e., this is
 * the divide-and-conquer step).
 *
 * @param start Starting point of the chain.
 * @param chainLength Size of the chain to be sorted.
 * @return A pointer to the beginning of the now sorted chain.
 */
template <class T>
typename List<T>::ListNode * List<T>::mergesort(ListNode * start, int chainLength)
{
    /// @todo Graded in MP3.2

    return NULL; // change me!
}
