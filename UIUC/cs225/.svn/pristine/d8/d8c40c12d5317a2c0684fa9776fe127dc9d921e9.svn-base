#include <vector>
#include "png.h"
#include "rgbapixel.h"
#include "dsets.h"

using namespace std;


class SquareMaze 
{
	public:
	
		SquareMaze();
		void makeMaze(int width, int height);
		bool canTravel(int x, int y, int dir)const;
		void setWall(int x, int y, int dir, bool exists);
		std::vector<int> solveMaze();
		PNG * drawMaze()const;
		PNG * drawMazeWithSolution();
		SquareMaze(int w, int h);
	
	
	private:
		class boolean
		{
			public:
			boolean(){right = true; down = true;}
			bool right;
			bool down;
			bool dirty;
		};
		class point
		{
			public:
			point(){x = 0; y = 0; dist = 0;}
			point(int tx, int ty, point tparent, int tdist){x = tx; y = ty; parent = & tparent; dist = tdist;}
			int x;
			int y;
			SquareMaze::point * parent;
			int dist;
		};
		class deadEnd
		{
			public:
				deadEnd(){up = false; down = false; right = false; left = false;}
				bool up;
				bool down;
				bool right;
				bool left;
				bool imLost(){return up && down && right && left;}
		};
		int width;
		int height;
		std::vector<std::vector<boolean>> isOpen;
		std::vector<std::vector<boolean>> isBlocked;
		std::vector<int> solution;
		point end = point(100000, 100000, point(), 0);

		void drawWall(int x, int y, int dir, PNG & maze)const;
		void drawRight(int x, int y, PNG & maze)const;
		void drawDown(int x, int y, PNG & maze)const;
		void drawTopAndRight(PNG & maze)const;

	void headNorth(int x, int y, PNG & maze) const;
	void turnLeft(int x, int y, PNG & maze) const;
	void turnRight(int x, int y, PNG & maze)const;
	void headSouth(int x, int y, PNG & maze)const;

	std::vector<int> getDirections();

	std::vector<int> solveMazeIter();
	void findEnd();
	void drawSolution(std::vector<int> solution, PNG & maze)const;
};
