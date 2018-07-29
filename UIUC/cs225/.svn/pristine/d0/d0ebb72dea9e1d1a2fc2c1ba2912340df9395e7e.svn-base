/**
 * @file heap.cpp
 * Implementation of a heap class.
 */

template <class T, class Compare>
size_t heap<T, Compare>::root() const {
    /// @todo Update to return the index you are choosing to be your root.
    return 1;
}

template <class T, class Compare>
size_t heap<T, Compare>::leftChild( size_t currentIdx ) const {
    /// @todo Update to return the index of the left child.
    return 2*currentIdx;
}

template <class T, class Compare>
size_t heap<T, Compare>::rightChild( size_t currentIdx ) const {
    /// @todo Update to return the index of the right child.
    return 2*currentIdx+1;
}

template <class T, class Compare>
size_t heap<T, Compare>::parent( size_t currentIdx ) const {
    /// @todo Update to return the index of the parent.
    return currentIdx/2;
}

template <class T, class Compare>
bool heap<T, Compare>::hasAChild( size_t currentIdx ) const {
    /// @todo Update to return whether the given node has a child
    return _elems.size()>leftChild(currentIdx);
}

template <class T, class Compare>
size_t heap<T, Compare>::maxPriorityChild( size_t currentIdx ) const {
    /// @todo Update to return the index of the child with highest priority
    ///   as defined by higherPriority()

    return higherPriority(_elems[leftChild(currentIdx)], _elems[rightChild(currentIdx)]) ? leftChild(currentIdx) : rightChild(currentIdx);
}

template <class T, class Compare>
void heap<T, Compare>::heapifyDown( size_t currentIdx ) {
    /// @todo Implement the heapifyDown algorithm.

	size_t leftIdx = leftChild(currentIdx);
	size_t rightIdx = rightChild(currentIdx);
	size_t parentIdx = parent(currentIdx);
    size_t priority;

    if( leftIdx >= _elems.size())
        return;
	if( rightIdx >= _elems.size())
		priority = leftIdx;		

	else if( higherPriority( _elems[ leftIdx ], _elems[ rightIdx ] ) ) 
		priority = leftIdx;
	else
		priority = rightIdx;

	if( higherPriority( _elems[ priority ], _elems[ currentIdx ] ) )
		std::swap( _elems[ priority ], _elems[currentIdx] );

	heapifyDown(priority);
}

template <class T, class Compare>
void heap<T, Compare>::heapifyUp( size_t currentIdx ) {
    if( currentIdx == root() )
        return;
    size_t parentIdx = parent( currentIdx );
    if( higherPriority( _elems[ currentIdx ], _elems[ parentIdx ] ) ) {
        std::swap( _elems[ currentIdx ], _elems[ parentIdx ] );
        heapifyUp( parentIdx );
    }
}

template <class T, class Compare>
heap<T, Compare>::heap() {
    /// @todo Depending on your implementation, this function may or may
    ///   not need modifying
	_elems.push_back(T());
}

template <class T, class Compare>
heap<T, Compare>::heap( const std::vector<T> & elems ) {
    /// @todo Construct a heap using the buildHeap algorithm
	_elems = std::vector<T>(elems);
	_elems.insert(_elems.begin(),T());

	for(int i = parent(_elems.size()-1); i>0; i--)
		heapifyDown(i);
}

template <class T, class Compare>
void heap<T, Compare>::heapCopy(size_t currentIdx) {
    if( currentIdx >= root() )
        return;
    size_t parentIdx = parent( currentIdx );
    if( higherPriority( _elems[ currentIdx ], _elems[ parentIdx ] ) ) 
        std::swap( _elems[ currentIdx ], _elems[ parentIdx ] );
	heapCopy(currentIdx-2);
}

template <class T, class Compare>
T heap<T, Compare>::pop() {
    /// @todo Remove, and return, the element with highest priority
	T retVal = peek();
	std::swap(_elems[1], _elems[_elems.size()-1]);
	_elems.erase(_elems.end()-1);
	heapifyDown(1);

    return retVal;
}

template <class T, class Compare>
T heap<T, Compare>::peek() const {
	if(empty())
		return T();
    /// @todo Return, but do not remove, the element with highest priority
    return _elems[1];
}

template <class T, class Compare>
void heap<T, Compare>::push( const T & elem ) {
    /// @todo Add elem to the heap
	_elems.push_back(elem);
	heapifyUp(_elems.size()-1);
}

template <class T, class Compare>
bool heap<T, Compare>::empty() const {
    /// @todo Determine if the heap is empty
    return _elems.size() <= 1;
}
