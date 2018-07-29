#include "rgbapixel.h"
#include "png.h"

int main()
{
	PNG x = PNG();
	PNG y = PNG("in.png");
	//y.resize(200,300);
 	y.writeToFile("in_01.png");	
	//std::cout << y.height();
	//std::cout << "\n";
	//std::cout << y.width();
	//std::cout << "\n\n";
	x.resize(y.width(), y.height());
	//x.writeToFile("in_01.png");

	for(int i = 0; i < (int)y.height(); i++)
	{	
		//std::cout << "\n";
		for(int j = 0; j < (int)y.width(); j++)
		{
			//RGBAPixel black = RGBAPixel(0,0,0);
			RGBAPixel* ptr = y.operator()(j,i);
			RGBAPixel* ptr2 = x.operator()(x.width()-1-j,x.height()-1-i);
			//RGBAPixel* bl = black;
			ptr2 -> green = ptr -> green;
			ptr2 -> red = ptr -> red;
			ptr2 -> blue = ptr -> blue;
			ptr2 -> alpha = ptr -> alpha;
			//std::cout << ptr2.components();
			//x.operator()(x.width()-1-j,x.height()-1-i) -> y.operator()(j,i);
			ptr2 = ptr;
		}	
	}
	x.writeToFile("out.png");

	return 0;
}
