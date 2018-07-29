#include "rgbapixel.h"




RGBAPixel::RGBAPixel()
{
	red = 255;
	blue = 255;
	green = 255;
	this->alpha = 255;
}

RGBAPixel::RGBAPixel(uint8_t red, uint8_t green, uint8_t blue)
{
	this->red = red;
	this->blue = blue;
	this->green = green;
	this->alpha = 255;

}
//int RGBAPixel::components()
//{
//	return 0;
//}
;
