#ifndef SCENE_H
#define SCENE_H

#include <cstdint>
#include <ostream>
#include "image.h"

using std::uint8_t;

class Scene
{	
    public:
	Scene(int max);
	~Scene();
	Scene(const Scene &source);
	const Scene& operator = (const Scene &source);
	void addpicture(const char *FileName, int index, int x, int y);
	void changelayer(int index, int newindex);
	void changemaxlayers(int newmax);
	void copy(const Scene &source);
	Image* copy(int limit);
	void indexError();
	void clean();
	void translate(int index, int xcoord, int ycoord);
	void deletepicture(int index);
	Image* getpicture(int index)const;
	Image drawscene()const;
	
	Image** images;
	int maxNimages;
};
#endif //SCENE_H
