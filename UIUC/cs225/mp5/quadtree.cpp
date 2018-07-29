// **************************************************************
// *		   
// *  quadtree.cpp
// *		   
// *  Quadtree class
// *		   
// *  CS 225 Spring 2008
// *		   
// **************************************************************

#include "quadtree.h"
#include <math.h>

Quadtree::Quadtree(){
	root = NULL;
}


Quadtree::Quadtree(PNG const & sorceObject, int d)
{
	root = NULL;
	buildTree(sorceObject, d);
}


Quadtree::Quadtree(Quadtree const & other)
{
	n = other.n;
	root = new QuadtreeNode();
	copy(root, other.root);
}


Quadtree::~Quadtree()
{
	clean(root);
}


void Quadtree::clean(QuadtreeNode * pickels)
{	
	if(pickels == NULL)
		return;

	clean(pickels->nwChild);
	clean(pickels->neChild);
	clean(pickels->seChild);
	clean(pickels->swChild);


	delete pickels;
}


Quadtree const & Quadtree::operator=(Quadtree const & other)
{
	if(this != &other)
	{
		clean(root);
		n = other.n;
		root = new QuadtreeNode();

		copy(root, other.root);
	}	

	return *this;
}


void Quadtree::copy(QuadtreeNode * cop, QuadtreeNode * Og)
{
	if(Og == NULL)
	{	
		return;
	}
	else if(Og->neChild != NULL)
	{
		cop->nwChild = new QuadtreeNode();
		cop->neChild = new QuadtreeNode();
		cop->swChild = new QuadtreeNode();
		cop->seChild = new QuadtreeNode();
	}

	cop->element = Og->element;

	copy(cop->nwChild, Og->nwChild);
	copy(cop->neChild, Og->neChild);
	copy(cop->swChild, Og->swChild);
	copy(cop->seChild, Og->seChild);	
}


void Quadtree::buildTree(PNG const & source, int resolution)
{
	clean(root);
	root = new QuadtreeNode();
	int depth = log(resolution*resolution)/log(4);
	n = resolution;
	buildHelp(source, root, 0, depth, 0, 0);
}


void Quadtree::buildHelp(PNG const & source, QuadtreeNode * curr, int level, int depth, int px, int py)
{
	if(level == depth)
	{
		curr->element = *source(px, py);
		return;
	}

	curr->nwChild = new QuadtreeNode();
	curr->neChild = new QuadtreeNode();
	curr->seChild = new QuadtreeNode();
	curr->swChild = new QuadtreeNode();

	int divider = pow(2, level+1);

	buildHelp(source, curr->nwChild, level+1, depth, px, py);
	buildHelp(source, curr->neChild, level+1, depth, px+n/divider, py);
	buildHelp(source, curr->swChild, level+1, depth, px, py+n/divider);
	buildHelp(source, curr->seChild, level+1, depth, px+n/divider, py+n/divider);
}


RGBAPixel Quadtree::getPixel(int x, int y)const
{
	return helpGetPixel(x, y, root, 0);

}


RGBAPixel Quadtree::helpGetPixel(int x, int y, QuadtreeNode * curr, int level)const
{
	RGBAPixel hiccup;

	if(curr->nwChild == NULL)
		return curr->element;

	int divider = pow(2, level+1);

	if(x < n/divider && y < n/divider){
		hiccup = helpGetPixel(x, y, curr->nwChild, level+1);
	}
	else if(x < n/divider){
		hiccup = helpGetPixel(x, y-n/divider, curr->swChild, level+1);

	}
	else if(y < n/divider){
		hiccup = helpGetPixel(x-n/divider, y, curr->neChild, level+1);
	}
	else{
		hiccup = helpGetPixel(x-n/divider, y-n/divider, curr->seChild, level+1);
	}
	
	return hiccup;
}


PNG Quadtree::decompress()const
{
	if(root == NULL)
		return PNG();
	PNG decomp = PNG(n, n);
	int depth = log(n*n)/log(4);
	decompressHelper(decomp, root, 0, 0, 0, depth);
	return decomp;
}


void Quadtree::decompressHelper(PNG & decomp, QuadtreeNode * curr, int level, int px, int py, int depth)const
{
	if(curr->neChild == NULL)
	{
		int subHieght = depth - level;
		int area =	pow(4, subHieght);	

		for(int i = px; i < px+pow(area, .5); i++)
			for(int j = py; j < py+pow(area, .5); j++) 
				*decomp(i, j) = curr->element;
	}

	else
	{
		int divider = pow(2, level+1);
	
		decompressHelper(decomp, curr->nwChild, level+1, px, py, depth);
		decompressHelper(decomp, curr->swChild, level+1, px, py+n/divider, depth);
		decompressHelper(decomp, curr->neChild, level+1, px+n/divider, py, depth);
		decompressHelper(decomp, curr->seChild, level+1, px+n/divider, py+n/divider, depth);
	}
}


//							MP5.1

void Quadtree::clockwiseRotate()
{
	rotateRecurse(root);

}


void Quadtree::rotateRecurse(QuadtreeNode * node)
{
	if(node == NULL)
		return;

	QuadtreeNode * nw = node->nwChild;
	QuadtreeNode * ne = node->neChild;
	QuadtreeNode * sw = node->swChild;
	QuadtreeNode * se = node->seChild;

	node->nwChild = sw;
	node->neChild = nw;
	node->swChild = se;
	node->seChild = ne;

	rotateRecurse(node->nwChild);
	rotateRecurse(node->neChild);
	rotateRecurse(node->swChild);
	rotateRecurse(node->seChild);
}


void Quadtree::prune(int tolerance)
{
	childAVG(root);
	pruneHelp(root, tolerance);

}


void Quadtree::pruneHelp(QuadtreeNode * node, int tolerance)
{
	if(node->neChild == NULL)
		return;

	if(leafTolerance(node, node->avgChild, tolerance))
	{
		clean(node->nwChild);
		clean(node->neChild);
		clean(node->swChild);
		clean(node->seChild);
	
		node->element = node->avgChild;
		node->nwChild = NULL;
		node->neChild = NULL;
		node->swChild = NULL;
		node->seChild = NULL;
	}
	else
	{
		pruneHelp(node->nwChild, tolerance);
		pruneHelp(node->neChild, tolerance);
		pruneHelp(node->swChild, tolerance);
		pruneHelp(node->seChild, tolerance);
	}

}

void Quadtree::childAVG(QuadtreeNode * node)const
{
	if(node->neChild->neChild == NULL)
	{
		size_t redAVG = (node->neChild->element.red + node->nwChild->element.red + node->seChild->element.red + node->swChild->element.red)/4; 
		size_t blueAVG = (node->neChild->element.blue + node->nwChild->element.blue + node->seChild->element.blue + node->swChild->element.blue)/4; 
		size_t greenAVG = (node->neChild->element.green + node->nwChild->element.green + node->seChild->element.green + node->swChild->element.green)/4; 

		node->avgChild = RGBAPixel(redAVG, greenAVG, blueAVG);		

		return;
	}
	childAVG(node->nwChild);
	childAVG(node->neChild);
	childAVG(node->swChild);
	childAVG(node->seChild);

	size_t redAVG = (node->neChild->avgChild.red + node->nwChild->avgChild.red + node->seChild->avgChild.red + node->swChild->avgChild.red)/4; 
	size_t blueAVG = (node->neChild->avgChild.blue + node->nwChild->avgChild.blue + node->seChild->avgChild.blue + node->swChild->avgChild.blue)/4; 
	size_t greenAVG = (node->neChild->avgChild.green + node->nwChild->avgChild.green + node->seChild->avgChild.green + node->swChild->avgChild.green)/4; 
	
	node->avgChild = RGBAPixel(redAVG, greenAVG, blueAVG);
}


int Quadtree::pruneSize(int tolerance)const
{
	childAVG(root);

	int depth = log(n*n)/log(4);

	int removedLeaves = pruneSizeHelp(root, tolerance, depth, 0);

	return n*n - removedLeaves;
}


int Quadtree::pruneSizeHelp(QuadtreeNode * node, int tolerance, int depth, int level)const
{
	if(node->neChild == NULL)
		return 0;
	
	int removedLeaves = 0;	


	if(leafTolerance(node, node->avgChild, tolerance))
	{
		int subHieght = depth - level;
		int area =	pow(4, subHieght);
		return area - 1;

	}
	else
	{
		removedLeaves += pruneSizeHelp(node->nwChild, tolerance, depth, level + 1);
		removedLeaves += pruneSizeHelp(node->neChild, tolerance, depth, level + 1);
		removedLeaves += pruneSizeHelp(node->swChild, tolerance, depth, level + 1);
		removedLeaves += pruneSizeHelp(node->seChild, tolerance, depth, level + 1);
	}
	return removedLeaves;

}


bool Quadtree::leafTolerance(QuadtreeNode * node, RGBAPixel tol, int tolerance)const
{
	if(node->neChild == NULL)
		return tolerance >= (int)(pow(node->element.red - tol.red, 2) + pow(node->element.blue - tol.blue, 2) + pow(node->element.green - tol.green, 2));

	bool siOno = true;

	siOno = siOno && leafTolerance(node->nwChild, tol, tolerance);
	siOno = siOno && leafTolerance(node->neChild, tol, tolerance);
	siOno = siOno && leafTolerance(node->swChild, tol, tolerance);
	siOno = siOno && leafTolerance(node->seChild, tol, tolerance);
	
	return siOno;
}


int Quadtree::idealPrune(int numLeaves)const
{
	return idealSearch(numLeaves, 0, 255*255*3);
}


int Quadtree::idealSearch(int numLeaves, int start, int end)const
{
	if(end<=start)
		return end;
	int midPoint = start + (end - start)/2;
	//std::cout << midPoint << "  ";
	if(numLeaves > pruneSize(midPoint))
		return idealSearch(numLeaves, start, midPoint - 1);
	else if(numLeaves < pruneSize(midPoint))
		return idealSearch(numLeaves, midPoint+1, end);
	else
		return midPoint;
}


Quadtree::QuadtreeNode::QuadtreeNode()
{	
		nwChild = NULL;
		neChild = NULL;
		swChild = NULL;
		seChild = NULL;
}
