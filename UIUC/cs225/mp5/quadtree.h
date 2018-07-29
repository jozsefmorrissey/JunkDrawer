// **************************************************************
// *		   
// *  quadtree.h
// *		   
// *  Quadtree class
// *		   
// *  CS 225 Spring 2008
// *		   
// **************************************************************

#ifndef QUADTREE_H
#define QUADTREE_H

#include "png.h"

class Quadtree
{
	public:
	Quadtree();
	Quadtree(PNG const & sorceObject, int d);
	Quadtree(Quadtree const & other);
	~Quadtree();
	void buildTree(PNG const & source, int resolution);
	RGBAPixel getPixel(int x, int y)const;
	PNG decompress()const;
	Quadtree const & operator=(Quadtree const & other);
	void clockwiseRotate();
	void prune(int tolerance);
	int pruneSize(int tolerance)const;
	int idealPrune(int numLeaves)const;
	void printTree(int numLeaves)const;
	

	
	
	private:
	
	// A simple class representing a single node of a Quadtree.
	// You may want to add to this class; in particular, it could probably
	// use a constructor or two...
	class QuadtreeNode
	{
		public:
		QuadtreeNode* nwChild;  // pointer to northwest child
		QuadtreeNode* neChild;  // pointer to northeast child
		QuadtreeNode* swChild;  // pointer to southwest child
		QuadtreeNode* seChild;  // pointer to southeast child

		QuadtreeNode();

		RGBAPixel element;  // the pixel stored as this node's "data"
		RGBAPixel avgChild;
	};
	void buildHelp(PNG const & source, QuadtreeNode * curr, int level, int depth, int px, int py);
	RGBAPixel helpGetPixel(int x, int y, QuadtreeNode * curr, int level)const;
	void clean(QuadtreeNode * pickels);
	void decompressHelper(PNG & decomp, QuadtreeNode * curr, int level, int px, int py, int depth)const;
	void copy(QuadtreeNode * cop, QuadtreeNode * Og);	
	void childAVG(QuadtreeNode * node)const;
	void pruneHelp(QuadtreeNode * node, int tolerance);
	void rotateRecurse(QuadtreeNode * node);
	int pruneSizeHelp(QuadtreeNode * node, int tolerance, int depth, int level)const;
	bool leafTolerance(QuadtreeNode * node, RGBAPixel tol, int tolerance)const;
	int idealSearch(int numLeaves, int start, int end)const;

	QuadtreeNode* root;    // pointer to root of quadtree
	int n;
	
	/**** Functions added for testing/grading                ****/
	/**** Do not remove this line or copy its contents here! ****/
	#include "quadtree_given.h"
};

#endif








































