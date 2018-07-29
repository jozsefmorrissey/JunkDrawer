#ifndef RGBAPIXEL_H
#define RGBAPIXEL_H

//#include <stdint.h>
#include <cstdint>
using std::uint8_t;

class RGBAPixel
{

public:
	//components();
	RGBAPixel();
	RGBAPixel(uint8_t red, uint8_t green, uint8_t blue);
	uint8_t blue;
	uint8_t green;
	uint8_t alpha;
	uint8_t red;
};

#endif
