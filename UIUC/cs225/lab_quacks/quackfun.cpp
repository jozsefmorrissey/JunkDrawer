/**
 * @file quackfun.cpp
 * This is where you will implement the required functions for the
 *  stacks and queues portion of the lab.
 */

/**
 * Sums items in a stack.
 * @param s A stack holding values to sum.
 * @return The sum of all the elements in the stack, leaving the original
 *  stack in the same state (unchanged).
 *
 * @note You may modify the stack as long as you restore it to its original
 *  values.
 * @note You may use only two local variables of type T in your function.
 *  Note that this function is templatized on the stack's type, so stacks of
 *  objects overloading the + operator can be summed.
 * @note We are using the Standard Template Library (STL) stack in this
 *  problem. Its pop function works a bit differently from the stack we
 *  built. Try searching for "stl stack" to learn how to use it.
 * @hint Think recursively!
 */
template <typename T>
T QuackFun::sum(stack<T> & s)
{
    // Your code here
	if(s.empty())
		return 0;
	else
	{
		T x = s.top();
		s.pop();
		T y = x + sum(s);
		s.push(x);	
    	return y; // stub return value (0 for primitive types). Change this!
    }            // Note: T() is the default value for objects, and 0 for
                // primitive types
}

/**
 * Reverses even sized blocks of items in the queue. Blocks start at size
 * one and increase for each subsequent block.
 * @param q A queue of items to be scrambled
 *
 * @note Any "leftover" numbers should be handled as if their block was complete.
 * @note We are using the Standard Template Library (STL) queue in this
 *  problem. Its pop function works a bit differently from the stack we
 *  built. Try searching for "stl stack" to learn how to use it.
 * @hint You'll want to make a local stack variable.
 */
template <typename T>
void QuackFun::scramble(queue<T> & q)
{
    stack<T> s;
    // optional: queue<T> q2;
	int next = 2;
	int rev = 0;
	int current = 0;
	T end = q.front();
	if(!q.empty())
	{
		q.push(q.front());
		q.pop();
	}
	
	while(!q.empty() && q.front()!=end)
	{
		if(next%2 == 0)
		{		
			for(int i = 0; i < next; i++)
			{
				if(q.front() != end)
				{
					s.push(q.front());
					q.pop();
				}
			}		
			for(int i = 0; i < next; i++)
			{
				if(!s.empty())
				{
					q.push(s.top());
					s.pop();
				}
			}
		}
		else
		{		
		
			for(int i = 0; i < next; i++)
			{
				if(q.front() != end)
				{
					q.push(q.front());
					q.pop();
				}
			}
		}
	
		
		next++;
	}

    // Your code here
}

/**
 * @return true if the parameter stack and queue contain only elements of exactly
 *  the same values in exactly the same order; false, otherwise.
 *
 * @note You may assume the stack and queue contain the same number of items!
 * @note There are restrictions for writing this function.
 * - Your function may not use any loops
 * - In your function you may only declare ONE local boolean variable to use in your return statement,
 *      and you may only declare TWO local variables of parametrized type T to use however you wish.
 *   No other local variables can be used.
 * - After execution of verifySame, the stack and queue must be unchanged. Be sure to comment your code VERY well.
 */
template <typename T>
bool QuackFun::verifySame(stack<T> & s, queue<T> & q)
{
    bool retval = true; // optional
    T temp1; // rename me
    //T temp2; // rename :)
	if(s.empty())//base case, return true once recursive calls have emptied the stack.
		return retval;
	
	temp1 = s.top();   //stores the stack variable that is removed.
	s.pop();
	retval = verifySame(s, q) && temp1 == q.front();//sets return value to true iff the current value matches and all prior cases also match.
	q.push(q.front());//pushes the front value to the back.
	q.pop();//removes front value.
	s.push(temp1);//returns temp1 value to its rightfull location.

    return retval;
}

