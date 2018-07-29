#include <cstdlib>
#include "png.h"
#include <iostream>
using namespace std;

//
// sets up the output image
PNG * setupOutput(int w, int h)
{
	PNG * image = new PNG(w, h);
	return image;
}


// Returns my favorite color
RGBAPixel * myFavoriteColor(int intensity)
{
	RGBAPixel *color = new RGBAPixel(0,96,192);
	//color.red   = 0;
	//color.green = intensity/2;
	//color.blue  = intensity;
	return color;
}


void sketchify()
{	
		// Load in.png
	PNG *original = new PNG();

	//PNG *correct = new PNG();
	//correct->readFromFile("out.png");
	//for(int i = 100; i < 150; i++)
		//for(int j = 100; j < 150; j++)
		//{
		//	cout << (int)(*correct)(i,j)->red << endl;
		//	cout << (int)(*correct)(i,j)->blue << endl;
		//	cout << (int)(*correct)(i,j)->green << endl;
		//}
	
	original->readFromFile("in.png");
	
	int width = original->width();
	int height = original->height();

	// Create out.png
	PNG * output = setupOutput(width, height);	

	// Load our favorite color to color the outline
	RGBAPixel * myPixel = myFavoriteColor(192);
		// Go over the whole image, and if a pixel differs from that to its upper
		// cout << "wtf" << endl;
		// left, color it my favorite color in the output
	//cout << "45" << endl;

	for (int y = 1; y < height; y++)
	{
		for (int x = 1; x < width; x++)
		{	//cout << "x: " << x << "	y: " << y << endl;
			//std::cin.get();
			//cout << "52" << endl;

			// Calculate the pixel difference
			RGBAPixel * prev = (*original)(x-1, y-1);
			RGBAPixel * curr = (*original)(x  , y  );
			int diff = abs(curr->red   - prev->red  ) +
					   abs(curr->green - prev->green) +
					   abs(curr->blue  - prev->blue );
			
			//cout << "61" << endl;

			// If the pixel is an edge pixel,
			// color the output pixel with my favorite color
			RGBAPixel * currOutPixel = (*output)(x,y);
			if (diff > 100)
			{
				(*output)(x,y) -> green = myPixel -> green;
				(*output)(x,y) -> blue = myPixel -> blue;
				(*output)(x,y) -> red = myPixel -> red;
			}	
			//cout << "68" << endl;
			
		}
	}
	
	
	// Save the output file
	output->writeToFile("out.png");
	//cout << "71" << endl;

	// Clean up memory

	delete output;
	delete original;
	//cout << "81" << endl;

}
