#include "image.h"


/*Image::Image(int i, int l)
{
	for(int j = 0; j < i; j++)
		for(int k = 0; k < l; k++)
		{		
				(*this)(j,k)->red=0;
				(*this)(j,k)->blue=0;
				(*this)(j,k)->green=0;
		}
}*/

void Image::flipleft()
{
	for(int i = 0; i < (int)this->width()/2; i++)
		for(int j = 0; j < (int)this->height(); j++)
		{
			RGBAPixel *temp = new RGBAPixel();
			temp->red = (*this)(i,j)->red;
			temp->blue = (*this)(i,j)->blue;			
			temp->green = (*this)(i,j)->green;

			//std::cout << "can you see me";
			
			(*this)(i,j)->red = (*this)((int)this->width()-i-1,j)->red;
			(*this)(i,j)->green = (*this)((int)this->width()-i-1,j)->green;
			(*this)(i,j)->blue = (*this)((int)this->width()-i-1,j)->blue;
			
			(*this)((int)this->width()-i-1,j)->red = temp->red;
			(*this)((int)this->width()-i-1,j)->green = temp->green;
			(*this)((int)this->width()-i-1,j)->blue = temp->blue;
			
		}

}

void Image::adjustbrightness(int r, int g, int b)
{
	for(int i = 0; i < (int)this->width(); i++)
		for(int j = 0; j < (int)this->height(); j++)
		{
			if((int)(*this)(i,j)->green + g > 255)
				(*this)(i,j)->green = 255;
			else if((int)(*this)(i,j)->green + g < 0)
				(*this)(i,j)->green = 0;
			else
				(*this)(i,j)->green = g + (*this)(i,j)->green;
		
			if((int)(*this)(i,j)->blue + b > 255)
				(*this)(i,j)->blue = 255;
			else if((int)(*this)(i,j)->blue + b < 0)
				(*this)(i,j)->blue = 0;
			else
				(*this)(i,j)->blue = b + (*this)(i,j)->blue;


			if((int)(*this)(i,j)->red +r > 255)
				(*this)(i,j)->red = 255;
			else if((int)(*this)(i,j)->red + r < 0)
				(*this)(i,j)->red = 0;
			else			
				(*this)(i,j)->red = r + (*this)(i,j)->red;

		}
}

void Image::invertcolors()
{
	for(int i = 0; i < (int)this->width(); i++)
		for(int j = 0; j < (int)this->height(); j++)
		{
			(*this)(i,j)->red = 255-(*this)(i,j)->red;
			(*this)(i,j)->blue = 255-(*this)(i,j)->blue;
			(*this)(i,j)->green = 255-(*this)(i,j)->green;
		}
}
