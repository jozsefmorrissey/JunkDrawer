/**
 * @file avltree.cpp
 * Definitions of the binary tree functions you'll be writing for this lab.
 * You'll need to modify this file.
 */

template <class K, class V>
V AVLTree<K, V>::find(const K & key) const
{
	return find(root, key);
}

template <class K, class V>
V AVLTree<K, V>::find(Node * subtree, const K & key) const
{
	if (subtree == NULL)
		return V();
	else if (key == subtree->key)
		return subtree->value;
	else
	{
		if (key < subtree->key)
			return find(subtree->left, key);
		else
			return find(subtree->right, key);
	}
}

template <class K, class V>
void AVLTree<K, V>::rotateLeft(Node * & t)
{
	*_out << __func__ << endl; // Outputs the rotation name (don't remove this)
    // your code here
	if(t == NULL)
		return;

	Node * temp = t->right;
	t->right = temp ->left;
	temp->left = t;

	int right = heightOrNeg1(t->right);
	int left = heightOrNeg1(t->left);

	int th = t->height = (right > left ? right : left) + 1; 

	int pLeft = heightOrNeg1(temp->left);
	int pRight = heightOrNeg1(temp->right);
	temp->height = (pRight > pLeft ? pRight : pLeft) + 1;

	t = temp;
}

template <class K, class V>
void AVLTree<K, V>::rotateLeftRight(Node * & t)
{
	*_out << __func__ << endl; // Outputs the rotation name (don't remove this)
	// Implemented for you:
	rotateLeft(t->left);
	rotateRight(t);
}

template <class K, class V>
void AVLTree<K, V>::rotateRight(Node * & t)
{
	*_out << __func__ << endl; // Outputs the rotation name (don't remove this)
    // your code here
	Node * temp = t->left;
	t->left = temp->right;
	temp->right = t;

	int right = heightOrNeg1(t->right);
	int left = heightOrNeg1(t->left);

	t->height = (right > left ? right : left) + 1; 

	int pright = heightOrNeg1(temp->right);
	int pleft = heightOrNeg1(temp->right);
	temp->height = (pleft > pright ? pright : pright) + 1;

	t = temp;
}

template <class K, class V>
void AVLTree<K, V>::rotateRightLeft(Node * & t)
{
	*_out << __func__ << endl; // Outputs the rotation name (don't remove this)
    // your code here
	rotateRight(t->right);
	rotateLeft(t);

}


template <class K, class V>
void AVLTree<K, V>::insert(const K & key, const V & value)
{
	insert(root, key, value);
}


template <class K, class V>
void AVLTree<K, V>::insert(Node* & subtree, const K & key, const V & value)
{
    // your code here
	if (subtree == NULL)
	{
		subtree = new Node(key, value);	
	}
	else if (find(subtree, key) != V())
		return;
	else
	{	if (key < subtree->key)			//left side
		{

			insert(subtree->left, key, value);

			int mainBalance = heightOrNeg1(subtree->left) - heightOrNeg1(subtree->right);
			int subBalance = 0;
	

			subBalance = heightOrNeg1(subtree->left->left) - heightOrNeg1(subtree->left->right);

			if(mainBalance == 2)
			{
				if(subBalance == -1)
					rotateLeftRight(subtree);
				else
					rotateRight(subtree);
			}
		}
		else if(key > subtree->key)
		{			
			insert(subtree->right, key, value);

			int mainBalance = heightOrNeg1(subtree->left) - heightOrNeg1(subtree->right);
			int subBalance = 0;


			subBalance =  heightOrNeg1(subtree->right->right) - heightOrNeg1(subtree->right->left);

			if(mainBalance == -2)
			{
				if(subBalance == -1)
					rotateRightLeft(subtree);
				else
					rotateLeft(subtree);
			}
		}
	}
	int one = heightOrNeg1(subtree->left);
	int two = heightOrNeg1(subtree->right);
	subtree->height = (one > two ? one : two) + 1;
}










