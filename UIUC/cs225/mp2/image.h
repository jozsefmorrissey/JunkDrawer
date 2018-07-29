#ifndef IMAGE_H
#define IMAGE_H
#include "png.h"


class Image : public PNG
{
public:
	//Image(int i, int l);
	
	void flipleft();
	void adjustbrightness(int r, int g, int b);
	void invertcolors();

	int index;
	int x;
	int y;
};
#endif
