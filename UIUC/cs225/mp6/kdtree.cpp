/**
 * @file kdtree.cpp
 * Implementation of KDTree class.
 */

template<int Dim>
bool KDTree<Dim>::smallerDimVal(const Point<Dim> & first, const Point<Dim> & second, int curDim) const
{
    /**
     * @todo Implement this function!
     */
	if(first[curDim] == second[curDim]) 
		return first < second;
	else
		return first[curDim] < second[curDim];
}


template<int Dim>
bool KDTree<Dim>::shouldReplace(const Point<Dim> & target, const Point<Dim> & currentBest, const Point<Dim> & potential) const
{
    /**
     * @todo Implement this function!
     */
	int totalOG = 0;
	int totalNewb = 0;

 	for(int i = 0; i<Dim; i++)
	{
		totalOG += (target[i] - currentBest[i])*(target[i] - currentBest[i]);
		totalNewb += (target[i] - potential[i])*(target[i] - potential[i]);; 
	}
	
	if(totalOG == totalNewb)
		return potential < currentBest;
	
    	return totalNewb<totalOG ? true : false;
}

template<int Dim>
KDTree<Dim>::KDTree(const vector< Point<Dim> > & newPoints)
{
    /**
     * @todo Implement this function!
     */	
	for(size_t i=0; i<newPoints.size(); i++)
		points.push_back(newPoints[i]);	

	buildTree(0, points.size()-1, 0);

}

template<int Dim>
Point<Dim> KDTree<Dim>::findNearestNeighbor(const Point<Dim> & query) const
{
    /**
     * @todo Implement this function!
     */
	



   return findNearestRecurse(query, 0, points.size()-1, 0);
}


template<int Dim>
Point<Dim> KDTree<Dim>::findNearestRecurse(const Point<Dim> & query, int begin, int end, int dim)const
{
	if(begin >= end)
		return points[begin];
		
	Point<Dim> currentBest;
	int median = (begin + end)/2;
	if(smallerDimVal(query, points[median], dim))
		currentBest = findNearestRecurse(query, begin, median-1, (dim+1)%Dim);
	else
		currentBest = findNearestRecurse(query, median+1, end, (dim+1)%Dim);	

	
	if(shouldReplace(query, currentBest, points[median]))
		currentBest = points[median];	

	int midDist = (points[median][dim] - query[dim])*(points[median][dim] - query[dim]);
	if(distance(query, currentBest) >= midDist)
	{	
		Point<Dim> recursedBest;
		if(smallerDimVal(query, points[median], dim))
			recursedBest = findNearestRecurse(query, median+1, end, (dim+1)%Dim);
		else
			recursedBest = findNearestRecurse(query, begin, median-1, (dim+1)%Dim);	

		if(shouldReplace(query, currentBest, recursedBest))
			currentBest = recursedBest;	
	}

	return currentBest;
}

template<int Dim>
int KDTree<Dim>::distance(const Point<Dim> & point1, const Point<Dim> & point2)const
{
	int distance = 0;
	for(int i=0; i<Dim; i++)
		distance += (point1[i] - point2[i])*(point1[i] - point2[i]);
	return distance;
} 




template<int Dim>
void KDTree<Dim>::buildTree(int begin, int end, int dim)
{
	if(begin < end)
	{
		int median = (begin+end)/2; 
	
		select(dim, begin, end, median);

		buildTree(begin, median-1, (dim + 1)%Dim);
		buildTree(median+1, end, (dim + 1)%Dim);
	}
}

template<int Dim>
void KDTree<Dim>::select(int dim, int begin, int end, int median)
{
	int returnedPart = (begin+end)/2;
	while(1)
	{
		returnedPart = partition(median, begin, end, dim);			//partitions the vector according to pivot
		if(returnedPart == median)	//if k was median or if begin and end surround midpoint				
			return;
		else if(median < returnedPart)		//if k was greater then median, the median is in the range(begin, sp)
			end = returnedPart - 1;
		else		//if k was less then median, the median is in the range(sp, end)
			begin = returnedPart + 1;
	}
	
}

template<int Dim>
int KDTree<Dim>::partition(int returnedPart, int begin, int right, int dim)
{
	Point<Dim> testValue = points[returnedPart];
	std::swap(points[returnedPart], points[right]);	//moves partition value to end of partition
	int retVal = begin;		//keeps track of where partition ended
	
	for(int i = begin; i < right; i++)		//runs through all values withing the area to be partitioned 
	{
		if(smallerDimVal(points[i], testValue, dim))		//if less than partitioning value increment partition
		{
			std::swap(points[i], points[retVal]);
			retVal++;
		}
	}
	std::swap(points[right], points[retVal]); 
	return retVal;
}


/*template<int Dim>
void KDTree<Dim>::swap( int i, int j)	//swap some shit
{
	Point<Dim> temp = points[i];
	points[i] = points[j];
	points[j] = temp;
} */








