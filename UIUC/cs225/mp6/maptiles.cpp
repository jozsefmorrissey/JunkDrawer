/**
 * @file maptiles.cpp
 * Code for the maptiles function.
 */
	 			
#include <iostream>
#include <map>
#include "maptiles.h"

using namespace std;

MosaicCanvas * mapTiles(SourceImage const & theSource, vector<TileImage> const & theTiles)
{
    /**
     * @todo Implement this function!
     */

	MosaicCanvas * mp6point2 = new MosaicCanvas(theSource.getRows(), theSource.getColumns());

	std::map<Point<3>, TileImage> pixSearch;
	auto pixS_itter = pixSearch.begin();
	vector<Point<3>> averageColor;
	for(size_t i = 0; i < theTiles.size(); i++)
	{
		RGBAPixel pix = theTiles[i].getAverageColor();
		Point<3> temp = Point<3>(pix.red, pix.green, pix.blue);        	//does this construct properly?
		averageColor.push_back(temp);
		int key = temp[0]*temp[1]*temp[2];
		std::pair<Point<3>, TileImage> pleaseWork = std::pair<Point<3>, TileImage>(temp, theTiles[i]);
		pixSearch.insert(pixS_itter, pleaseWork);
	}

	KDTree<3> treeMap = KDTree<3>(averageColor);


	for(int i = 0; i<theSource.getRows(); i++)
		for(int j = 0; j<theSource.getColumns(); j++)
		{
			RGBAPixel tempPix = theSource.getRegionColor(i, j);
			Point<3> tempPoint = Point<3>(tempPix.red, tempPix.green, tempPix.blue);
			
			Point<3> located = treeMap.findNearestNeighbor(tempPoint);

			TileImage gotcha = pixSearch.find(located)->second;
			
			mp6point2->setTile(i, j, gotcha);
		} 



	return mp6point2;
}
