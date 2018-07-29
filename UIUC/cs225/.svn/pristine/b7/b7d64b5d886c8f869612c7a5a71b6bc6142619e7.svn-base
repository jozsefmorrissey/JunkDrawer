#include "maze.h"
#include <stdio.h>



SquareMaze::SquareMaze():width(0), height(0)
{
	
}


SquareMaze::SquareMaze(int w, int h):width(w), height(h)
{
	std::vector<std::vector<bool>> visited;

	for(int i = 0; i < w; i ++)
	{
		isBlocked.push_back(vector<boolean>());
		for(int j= 0; j<h; j++)
			isBlocked[i].push_back(boolean());
	}
	end.y = h;
	end.x = w;
}

std::vector<int> SquareMaze::getDirections()
{
	point * runner = & end;

	std::vector<int> directions;

	while(runner->x != 0 && runner->y != 0)
	{
		if(runner->parent->x < runner->x)
			directions.push_back(0);
		if(runner->parent->y < runner->y)
			directions.push_back(1);
		if(runner->parent->x > runner->x)
			directions.push_back(2);
		if(runner->parent->y > runner->y)
			directions.push_back(3);
		runner = runner->parent;
		//std::cout << runner->x << " : " << runner->y << std::endl;
		//std::cout << runner->parent->x << " : " << runner->parent->y << std::endl;
	}
	return directions;
}
	


void SquareMaze::makeMaze(int w, int h)
{	
	SquareMaze temp = SquareMaze(w, h);
	width = temp.width;
	height = temp.height;
	isBlocked = temp.isBlocked;

	std::vector<std::vector<bool>> found;	

	for(int i = 0; i < width; i ++)
	{
		found.push_back(vector<bool>());
		for(int j= 0; j<height; j++)
			found[i].push_back(false);
	}

	DisjointSets roots;
	roots.addelements(width*height);
	
	srand(time(NULL));

	std::vector<point> stack;
		
	point origin = point();

	stack.push_back(origin);

	found[0][0] = true;

	size_t z = 0;

	while(z < stack.size())	
	{
		point current = stack[z];

		int rootLoc = current.y*width + current.x;
		bool noGo = true;
		deadEnd de = deadEnd();

						if(current.x + 1 < width && !found[current.x + 1][current.y])
						{
							point onDeck = point(current.x + 1, current.y, current, 0);
							stack.push_back(onDeck);
							found[current.x + 1][current.y] = true;
				//std::cout<< onDeck.x << " : " << onDeck.y << "\t\t";
						}
						if(current.y + 1 < height && !found[current.x][current.y + 1])
						{
							point onDeck = point(current.x, current.y + 1, current, 0);
							stack.push_back(onDeck);
							found[current.x][current.y + 1] = true;
				//std::cout<< onDeck.x << " : " << onDeck.y << "\t\t";
						}
						if(current.x > 0 && !found[current.x - 1][current.y])
						{
							point onDeck = point(current.x - 1, current.y, current, 0);
							stack.push_back(onDeck);
							found[current.x - 1][current.y] = true;
				//std::cout<< onDeck.x << " : " << onDeck.y << "\t\t";
						}
						if(current.y > 0 && !found[current.x][current.y - 1])
						{
							point onDeck = point(current.x, current.y - 1, current, 0);
							stack.push_back(onDeck);
							found[current.x][current.y - 1] = true;
				//std::cout<< onDeck.x << " : " << onDeck.y << "\t\t";
						}



		while(noGo)		
		{
			//std::cout << stack.size() << " : " << std::endl;
			int doorNumber = rand() % 4;

			if(doorNumber == 0)
			{
				de.right = true;
				if(current.x + 1 < width)
				{
					int neighborLoc = rootLoc + 1;
					if(roots.find(rootLoc) != roots.find(neighborLoc))
					{
						noGo = false;
						roots.setunion(rootLoc, neighborLoc);
						setWall(current.x, current.y, 0, false);
						de = deadEnd();
					}
				}
			}
			else if(doorNumber == 1)
			{
				de.down = true;
				if(current.y + 1 < height)
				{
					int neighborLoc = rootLoc + width;
					if(roots.find(rootLoc) != roots.find(neighborLoc))
					{
						noGo = false;
						roots.setunion(rootLoc, neighborLoc);
						setWall(current.x, current.y, 1, false);
						de = deadEnd();
					}
				}						
			}
			else if(doorNumber == 2)
			{
				de.left = true;
				if(current.x > 0)
				{
					int neighborLoc = rootLoc - 1;
					if(roots.find(rootLoc) != roots.find(neighborLoc))
					{
						noGo = false;
						roots.setunion(rootLoc, neighborLoc);
						setWall(current.x, current.y, 2, false);
						de = deadEnd();
					}
				}						
			}
			else if(doorNumber == 3)
			{
				de.up = true;
				if(current.y>0)
				{

					int neighborLoc = rootLoc - width;
					if(roots.find(rootLoc) != roots.find(neighborLoc))
					{
						noGo = false;
						roots.setunion(rootLoc, neighborLoc);
						setWall(current.x, current.y, 3, false);
						de = deadEnd();
					}
				}						
			}
			//std::cout << z << std::endl;
			if(de.imLost())
			{
				//z++;			
				noGo = false;
			//std::cout<< "skip" << std::endl;
			}		
		}
		z++;
	}
	//isBlocked[end.x][end.y].down = false;
//std::cout << roots.size();

}


bool SquareMaze::canTravel(int x, int y, int dir)const
{
	if(x>width-1 || x<0)
		return false;
	if(y>height-1 || y<0)
		return false;

	if(dir == 0)
		return !isBlocked[x][y].right;
	if(dir == 1)
		return !isBlocked[x][y].down;
	if(dir == 2)
		return canTravel(x-1, y, 0);
	if(dir == 3)
		return canTravel(x, y-1, 1);

	return false;
}


void SquareMaze::setWall(int x, int y, int dir, bool exists)
{
	if(x>width-1 || x<0)
		return;
	if(y>height-1 || y<0)
		return;

	if(dir == 0)
		isBlocked[x][y].right = exists;
	if(dir == 1)
		isBlocked[x][y].down = exists;
	if(dir == 2)
		setWall(x-1, y, 0, exists);
	if(dir == 3)
		setWall(x, y-1, 1, exists);

}


std::vector<int> SquareMaze::solveMaze()
{
	std::vector<std::vector<bool>> visited;
	end.x = width+1;
	end.y = height+1;
	
	if(solution.size()>0)
		return solution;	

	for(int i = 0; i < width; i ++)
	{
		visited.push_back(vector<bool>());
		for(int j= 0; j<height; j++)
			visited[i].push_back(false);
	}



	if(solution.size() == 0)
		solveMazeIter();
	
//std::cout<<"pop"<<std::endl;
	//backDoor();
	
	//solution.erase(solution.begin());

	//shrink_to_fit();

	return solution;
}

/*void SquareMaze::drawRecurse(int x, int y, PNG & maze, std::vector<std::vector<bool>> visited)const
{
	if(visited[x][y] && (x < 0 || y < 0 || x >= width || y >= height))
		return;

	visited[x][y] = true;

	bool stillSome = true;
	while(stillSome)
	{
		stillSome = false;
		std::cout << x << ", " << y << ": ";
		if(canTravel(x, y, 0) && !visited[x+1][y])
		{
			turnRight(x, y, maze);
			visited[x+1][y] = true;
			stillSome = true;
			x++;
		}
		if(canTravel(x, y, 1) && !visited[x][y+1])
		{
			headSouth(x, y, maze);
			visited[x][y+1] = true;
			stillSome = true;
			y++;
		}
		if(canTravel(x, y, 2) && !visited[x-1][y])
		{
			turnLeft(x, y, maze);
			visited[x-1][y] = true;
			stillSome = true;
			x--;
		}
		if(canTravel(x, y, 3) && !visited[x][y-1])
		{
			headNorth(x, y, maze);
			visited[x][y-1] = true;
			stillSome = true;
			y--;
		}
	}	
}*/

void SquareMaze::findEnd()
{
	std::vector<std::vector<bool>> found;	

	for(int i = 0; i < width; i ++)
	{
		found.push_back(vector<bool>());
		for(int j= 0; j<height; j++)
			found[i].push_back(false);
	}

	bool deadEnd = true;

	std::vector<point> stack;		
	point origin = point();
	stack.push_back(origin);
	found[0][0] = true;

	while(!stack.empty())
	{
		point current = stack[stack.size()-1];
		stack.pop_back();
		if(canTravel(current.x, current.y, 0) && !found[current.x+1][current.y])
		{
			point onDeck = point(current.x + 1, current.y, current, current.dist + 1);
			stack.push_back(onDeck);
			found[current.x][current.y] = true;
		}
		if(canTravel(current.x, current.y, 1) && !found[current.x][current.y+1])
		{
			point onDeck = point(current.x, current.y + 1, current, current.dist + 1);
			stack.push_back(onDeck);
			found[current.x][current.y] = true;
		}
		if(canTravel(current.x, current.y, 2) && !found[current.x-1][current.y])
		{
			point onDeck = point(current.x - 1, current.y, current, current.dist + 1);
			stack.push_back(onDeck);
			found[current.x][current.y] = true;
		}
		if(canTravel(current.x, current.y, 3) && !found[current.x][current.y-1])
		{
			point onDeck = point(current.x, current.y - 1, current, current.dist + 1);
			stack.push_back(onDeck);
			found[current.x][current.y] = true;
		}
		if(current.y == height - 1 && current.dist > end.dist)
			end = current;
		//std::cout << end.x << ":" <<end.y << endl;
	}
	isBlocked[end.x][end.y].down = false;

}



std::vector<int> SquareMaze::solveMazeIter()
{
	std::vector<std::vector<bool>> found;	

	for(int i = 0; i < width; i ++)
	{
		found.push_back(vector<bool>());
		for(int j= 0; j<height; j++)
			found[i].push_back(false);
	}


	std::vector<int> path;
	int x = 0;
	int y = 0;
	found[0][0] = true;

	std::vector<point> loc;
	point origin;
	loc.push_back(origin);


	bool deadEnd = false;
	do
	{
		
		if(deadEnd)
		{
			if(!path.empty())
			{
				path.pop_back();
				loc.pop_back();
				x = loc[loc.size()-1].x;
				y = loc[loc.size()-1].y;
			}
			else
			{
				x = 0;
				y = 0;
			}
			deadEnd = false;
		}


//std::cout<< x << ":" << y << "\t\t" << path.size() << "\t\t" << solution.size() << std::endl;

		if(y == height - 1 && path.size() >= solution.size())
		{
			if(y == height - 1 && path.size() == solution.size() && x < end.x)
			{
					solution = path;
					end.x = x;
					end.y = y;
			}
			else if(y == height - 1 && path.size() >= solution.size())
					solution = path;
					end.x = x;
					end.y = y;
		}

		if(canTravel(x, y, 0) && !found[x+1][y])
		{
			found[++x][y] = true;
			path.push_back(0);
			point current = point(x, y, loc[loc.size()-1], 0);
			loc.push_back(current);
		}
		else if(canTravel(x, y, 1) && !found[x][y+1])
		{
			found[x][++y] = true;
			path.push_back(1);
			point current = point(x, y, loc[loc.size()-1], 0);
			loc.push_back(current);
		}
		else if(canTravel(x, y, 2) && !found[x-1][y])
		{
			found[--x][y] = true;
			path.push_back(2);
			point current = point(x, y, loc[loc.size()-1], 0);
			loc.push_back(current);
		}
		else if(canTravel(x, y, 3) && !found[x][y-1])
		{
			found[x][--y] = true;
			path.push_back(3);
			point current = point(x, y, loc[loc.size()-1], 0);
			loc.push_back(current);
		}
		else
			deadEnd = true;
	}while(loc.size()>1 || !deadEnd);
//std::cout<<"exit"<<std::endl;
	return solution;
}








PNG * SquareMaze::drawMaze()const
{
	PNG * retVal = new PNG(width*10 + 1, height*10 + 1);

	for(int i = 0; i < width; i++)
	for(int j = 0; j < height; j++)
	{
		if(isBlocked[i][j].down)
			drawDown(i, j, * retVal);
		if(isBlocked[i][j].right)
			drawRight(i, j, * retVal);
	}
	
	drawTopAndRight(* retVal);

	

	return retVal;
}


void SquareMaze::drawSolution(std::vector<int> solution, PNG & maze)const
{
	int x = 0;
	int y = 0;
	for(int i = 0; i<(int)solution.size(); i++)
	{
		if(solution[i] == 0)
		{
			turnRight(x, y, maze);
			x++;
		}
		if(solution[i] == 1)
		{
			headSouth(x, y, maze);
			y++;
		}
		if(solution[i] == 2)
		{
			turnLeft(x, y, maze);
			x--;
		}
		if(solution[i] == 3)
		{
			headNorth(x, y, maze);
			y--;
		}

	}
}


PNG * SquareMaze::drawMazeWithSolution()
{
	std::vector<int> solution = solveMaze();
	

	int x = 0;
	int y = 0;

	for(size_t i = 0; i < solution.size(); i++)
	{
		if(solution[i] == 0)
			x++;
		if(solution[i] == 1)
			y++;
		if(solution[i] == 2)
			x--;
		if(solution[i] == 3)
			y--;
	}
	isBlocked[x][y].down = false;




	PNG * retVal = drawMaze();


	drawSolution(solution, *retVal);

	return retVal;;
}


void SquareMaze::drawWall(int x, int y, int dir, PNG & maze)const
{
	if(x>width || x<0)
		return;
	if(y>height || y<0)
		return;

	if(dir == 0)
		drawRight(x, y, maze);
	if(dir == 1)
		drawDown(x, y, maze);
	if(dir == 2)
		drawWall(x+1, y, 0, maze);
	if(dir == 3)
		drawWall(x, y+1, 1, maze);

}

void SquareMaze::drawRight(int x, int y, PNG & maze)const
{
	int startx = x*10 + 10;
	int starty = y*10;

	RGBAPixel black = RGBAPixel(0, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(startx,starty+i) = black;
}


void SquareMaze::drawDown(int x, int y, PNG & maze)const
{
	int startx = x*10;
	int starty = y*10 + 10;

	RGBAPixel black = RGBAPixel(0, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(startx+i,starty) = black;
}

void SquareMaze::drawTopAndRight(PNG & maze)const
{
	RGBAPixel black = RGBAPixel(0, 0, 0);
	for(int i = 10; i<=width*10; i++)
		*maze(i, 0) = black;	
	for(int i = 0; i<=height*10; i++)
		*maze(0, i) = black;	

}

void SquareMaze::headSouth(int x, int y, PNG & maze)const
{
	x *= 10;
	y *= 10;

	x += 5;
	y += 5;

	RGBAPixel red = RGBAPixel(255, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(x, y + i) = red;
}

void SquareMaze::turnRight(int x, int y, PNG & maze)const 
{
	x *= 10;
	y *= 10;

	x += 5;
	y += 5;

	RGBAPixel red = RGBAPixel(255, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(x + i, y) = red;
}

void SquareMaze::turnLeft(int x, int y, PNG & maze) const
{
	x *= 10;
	y *= 10;

	x += 5;
	y += 5;

	RGBAPixel red = RGBAPixel(255, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(x - i, y) = red;
}

void SquareMaze::headNorth(int x, int y, PNG & maze) const
{
	x *= 10;
	y *= 10;

	x += 5;
	y += 5;

	RGBAPixel red = RGBAPixel(255, 0, 0);

	for(int i = 0; i<11; i++)
		*maze(x, y - i) = red;
}








