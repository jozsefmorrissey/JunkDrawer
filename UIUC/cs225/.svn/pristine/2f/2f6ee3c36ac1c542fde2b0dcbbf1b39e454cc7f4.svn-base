#include "scene.h"

	Scene::Scene(int max)
	{
		maxNimages = max;
		images = new Image *[max];

		for(int i = 0; i < maxNimages; i++)
			images[i] = NULL;	
		
	}

	Scene::~Scene()
	{
		clean();
	}


	Scene::Scene(const Scene &source)
	{
		
		copy(source);
	}

	void Scene::copy(const Scene &source)
	{
		maxNimages = source.maxNimages;
		images = new Image *[maxNimages];

		for(int i = 0; i < maxNimages; i++)
			images[i] = new Image();

		for(int i = 0; i < maxNimages; i++)
		{	

			if(source.images[i] != NULL) 
			{
				images[i]->resize((int)(source.images[i]->width()), (int)(source.images[i]->height()));
				for(int j=0; j < (int)(source.images[i]->width()); j++)
					for(int k=0; k < (int)(source.images[i]->height()); k++)
					{
						(*images[i])(j,k)->red = (*source.images[i])(j,k)->red;
						(*images[i])(j,k)->green = (*source.images[i])(j,k)->green;
						(*images[i])(j,k)->blue = (*source.images[i])(j,k)->blue;
						
					}
				images[i]->x = source.images[i]->x;
				images[i]->y = source.images[i]->y;
			}
		}		
		
	}

	void Scene::clean()
	{	
		if(images != NULL)
			delete [] images;
	}	

	const Scene& Scene::operator = (const Scene &source)
	{
		if(this != &source)
		{
			clean();
			copy(source);
		}
		return *this;
	}


	void Scene::addpicture(const char *FileName, int index, int x, int y)
	{
		if(images[index]!=NULL)
			std::cout << "index out of bounds" << endl;
		else
		{	
			if(images[index]!=NULL)
				delete images[index];
			images[index] = new Image();
			images[index]->readFromFile(FileName);
			images[index] -> x = x;
			images[index] -> y = y;
		}
	}

	Image* Scene::copy(int limit)
	{
		Image* copy = new Image[limit];
		for(int i = 0; i < limit; i++)
			 copy[i] = (*images)[i];
		
		return copy;
	}
	
	void Scene::changemaxlayers(int newmax)
	{
		int max;
		for(int i=0; i<maxNimages; i++)
			if(images[i]!=NULL)
				max = i+1;
	std::cout << "\t\tmax \t" << max << endl;
		
		if(newmax < max || newmax < 0)
			std::cout << "invalid newmax" << endl;

		else
		{
			Image** falafle = new Image *[newmax];
			for(int i = 0; i < newmax; i++)
				falafle[i] = NULL;
			std::cout << "start" << endl;
			for(int i = 0; i < max; i++)
				(falafle)[i]  = (images)[i];
			
			std::cout << "finish" << endl;
			clean();
			this->images = (falafle);
			maxNimages = newmax;
		}
	}

	void Scene::indexError()
		{
			std::cout << "invalid index" << endl;
		}

	void Scene::changelayer(int index, int newindex)
	{

		if(index >= maxNimages || newindex >= maxNimages ||  newindex < 0  || index < 0)
			indexError();
		else if(index != newindex)
		{
			if(images[newindex]!=NULL)
				delete images[newindex];

			//images[newindex]=new Image();

			images[newindex] = images[index];
			
			images[index]=NULL;
		}
	}

	

	void Scene::translate(int index, int xcoord, int ycoord)
	{
		if(index >= maxNimages || images[index] == NULL)
			indexError();
		else
		{
			this->images[index]->x = xcoord;
			this->images[index]->y = ycoord;
		}
	}


	void Scene::deletepicture(int index)
	{
		if(index >= maxNimages || images[index] == NULL)
			indexError();
		else
		{
			delete images[index];
			images[index] = NULL;
		}

	}


	Image* Scene::getpicture(int index)const
	{
		if(index >= maxNimages || images[index] == NULL)
			std::cout << "invalid index" << endl;
		
		else
			return images[index];
		
		return NULL;
	}


	Image Scene::drawscene()const
	{
		size_t maxWidth = 0;
		size_t maxHeight = 0;
	
		if(images != NULL)	
		{	for(int i = 0; i < maxNimages; i++)
			{
				
 				if(images[i] != NULL && images[i]->width()> 0  && images[i]->height()>0)
				{	
					if(((int)(images[i]->x) + (int)(images[i]->width())) > (int)maxWidth)
						maxWidth = images[i]->x + images[i]->width();

					if((int)(images[i]->y) + (int)(images[i]->height()) > (int)maxHeight)
						maxHeight = images[i]->x + images[i]->height();
				}		
			}
		}

		Image * bigIn = new Image();
		bigIn->resize(maxWidth, maxHeight);
		
		for(int i = 0; i<maxNimages; i++)
			if(images[i] != NULL && images[i]->width()> 0  && images[i]->height()>0)
			{
				for(int j = 0; j < (int)images[i]->width(); j++)
					for(int k = 0; k < (int)(images[i]->height()); k++)
					{		
							(*bigIn)(j+images[i]->x,k+images[i]->y)->red=(*images[i])(j,k)->red;
							(*bigIn)(j+images[i]->x,k+images[i]->y)->green =(*images[i])(j,k)->green;
							(*bigIn)(j+images[i]->x,k+images[i]->y)->blue=(*images[i])(j,k)->blue;	
					}
			}
		return *bigIn;

	}

