#include "image.h"


int main()
{
	Image* myImage = new Image();
	myImage->readFromFile("in_01.png");
	myImage->invertcolors();
	myImage->writeToFile("inverted.png");

	myImage->flipleft();
	myImage->writeToFile("flipped.png");

	//for(int i = 0; i < 31; i++)
		//std::printf("\tor o%d(chain[%d], in[%d], chain[%d]);\n", i+1, i+1, i+1, i);
	
	myImage->adjustbrightness(20, 20, -40);
	myImage->writeToFile("brightened.png");

	delete myImage;
	
	return 0;
}
