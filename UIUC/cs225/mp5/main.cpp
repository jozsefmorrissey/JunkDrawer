#include <iostream>
#include "png.h"
#include "quadtree.h"

using std::cout;
using std::endl;

int main() {

   PNG imgIn, imgOut;
   imgIn.readFromFile("in.png");

   // test constructor, decompress
   Quadtree halfTree(imgIn, 128);
//	for(int i = 0; i < 128; i++)
//	for(int j = 0; j <128; j++)
//	{
//		if(halfTree.getPixel(i, j) != *imgIn(i, j))
//			cout << "("<< i << "," << j << ")" << "\t" << halfTree.getPixel(i, j)<< *imgIn(i, j) << endl;
//		else
//			cout << (halfTree.getPixel(i, j) == *imgIn(i, j));
	
//	}
   imgOut = halfTree.decompress();
   imgOut.writeToFile("outHalf.png");

   // now for the real tests
   Quadtree fullTree;
   fullTree.buildTree(imgIn, 256);

   // you may want to experiment with different commands in this section

   // test pruneSize and idealPrune (slow in valgrind, so you may want to
   // comment these out when doing most of your testing for memory leaks)
   cout << "fullTree.pruneSize(0) = " << fullTree.pruneSize(0) << endl;
   cout << "fullTree.pruneSize(100) = " << fullTree.pruneSize(100) << endl;
   cout << "fullTree.pruneSize(1000) = " << fullTree.pruneSize(1000) << endl;
   cout << "fullTree.pruneSize(10000) = " << fullTree.pruneSize(10000) << endl;
   cout << "fullTree.pruneSize(100000) = " << fullTree.pruneSize(100000) << endl;

   cout << "fullTree.idealPrune(1000) = "  << fullTree.idealPrune(1000)<< "\t" << fullTree.pruneSize(fullTree.idealPrune(1000)) << endl;
   cout << "fullTree.idealPrune(10000) = " << fullTree.idealPrune(10000) << "\t" << fullTree.pruneSize(fullTree.idealPrune(10000)) << endl;
   cout << "fullTree.idealPrune(10) = "  << fullTree.idealPrune(10)<< "\t" << fullTree.pruneSize(fullTree.idealPrune(10)) << endl;
   cout << "fullTree.idealPrune(1) = " << fullTree.idealPrune(1) << "\t" << fullTree.pruneSize(fullTree.idealPrune(1)) << endl;
   cout << "fullTree.idealPrune(96) = "  << fullTree.idealPrune(96)<< "\t" << fullTree.pruneSize(fullTree.idealPrune(96)) << endl;
   cout << "fullTree.idealPrune(20) = " << fullTree.idealPrune(20) << "\t" << fullTree.pruneSize(fullTree.idealPrune(20)) << endl;
   cout << "fullTree.idealPrune(51) = "  << fullTree.idealPrune(51)<< "\t" << fullTree.pruneSize(fullTree.idealPrune(51)) << endl;
   cout << "fullTree.idealPrune(4017) = " << fullTree.idealPrune(4017) << "\t" << fullTree.pruneSize(fullTree.idealPrune(4017)) << endl;
   cout << "fullTree.idealPrune(39256) = "  << fullTree.idealPrune(39256)<< "\t" << fullTree.pruneSize(fullTree.idealPrune(39256)) << endl;
   cout << "fullTree.idealPrune(446) = " << fullTree.idealPrune(446) << "\t" << fullTree.pruneSize(fullTree.idealPrune(446)) << endl;





   // Test some creation/deletion functions
   Quadtree fullTree2;
   fullTree2 = fullTree;
   imgOut = fullTree2.decompress();
   imgOut.writeToFile("outCopy.png");


   // test clockwiseRotate
   fullTree.clockwiseRotate();
   imgOut = fullTree.decompress();
   imgOut.writeToFile("outRotated.png");


   // test prune
   fullTree = fullTree2;
   fullTree.prune(1000);
   imgOut = fullTree.decompress();
   imgOut.writeToFile("outPruned.png");


   // test several functions in succession
   Quadtree fullTree3(fullTree2);
   fullTree3.clockwiseRotate();
   fullTree3.prune(10000);
   fullTree3.clockwiseRotate();
   fullTree3.clockwiseRotate();
   fullTree3.clockwiseRotate();
   imgOut = fullTree3.decompress();
   imgOut.writeToFile("outEtc.png");

   // ensure that printTree still works
   Quadtree tinyTree(imgIn, 32);
   cout << "Printing tinyTree:\n";
   tinyTree.prune(100);
   tinyTree.printTree();

   return 0;
}
