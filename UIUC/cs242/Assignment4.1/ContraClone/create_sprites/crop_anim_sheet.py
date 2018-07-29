import png
from PIL import Image
import Image, ImageDraw, ImageFont
import math
import numpy as np
from operator import sub
import sys
from sys import argv
import subprocess
import matplotlib.pyplot as plt
from decimal import Decimal
import PIL


def save_images(image):
	return

def find_crop_bfs(width, height, visited, image_width, image_height, pix):
	colored = []
	notcolored = []
	maxH = minH = height
	maxW = minW = width

	colored.append((width, height, 0))
	while(colored or notcolored):
		if(colored):
			loc = colored.pop(0)
		else:
			loc = notcolored.pop(0)

		maxW = max(loc[0], maxW)
		maxH = max(loc[1], maxH)
		minW = min(loc[0], minW)
		minH = min(loc[1], minH)

		if(loc[2] < buff_dist):
			add_pix(move_loc(0,1,loc), colored, notcolored, visited, image_width, image_height)
			add_pix(move_loc(0,-1,loc), colored, notcolored, visited, image_width, image_height)
			add_pix(move_loc(1,0,loc), colored, notcolored, visited, image_width, image_height)
			add_pix(move_loc(-1,0,loc), colored, notcolored, visited, image_width, image_height)

	return maxH, maxW, minH, minW 


def move_loc(x, y, loc):
	newloc = [loc[0], loc[1], loc[2]]
	if(x != 0):
		newloc[0] += x
	else:
		newloc[1] += y
	return newloc



def add_pix(loc, colored, notcolored, visited, image_width, image_height):
	width = loc[0]
	height = loc[1]
	miss_count = loc[2]

	out_of_bounds = (width < 0 or height < 0 or image_width <= width or image_height <= height)
	if(not out_of_bounds and not visited[1][width, height]):
		visited[0][width, height] = True
		visited[1][width, height] = True
		if(pix[width, height] == 0):
			notcolored.append((width, height, miss_count + 1))
		else:
			colored.append((width, height, 0))





fileLocation = "../enemy_images.png"		#location of image to be cropped
saveFileLocation = "cropped_"					#location of cropped images
buff_dist = 1										# allows for sparse images to be isolated	
name_images = False								#allows you to name images as they are cropped

image = Image.open(fileLocation)
image.show()

width, height = image.size

pix = image.load()

image_search = np.zeros((width,height), dtype=bool)
explore_image = np.zeros((width,height), dtype=bool)
visited = [image_search, explore_image]

count = 0;
for h in range (0, height):
	for w in range (0, width):
		if(pix[w,h] != 0 and not visited[0][w,h]):
			count += 1

			maxH, maxW, minH, minW = find_crop_bfs(w, h, visited, width, height, pix)
			cropped = image.crop((minW, minH, maxW, maxH))
			
			if(name_images):
				cropped.show()
				fileName = raw_input().rstrip() + '.png';
			else:
				fileName = saveFileLocation + str(count) + '.png'
	
			f = open(fileName, 'w+')
			cropped.save(f, "PNG")
			f.close()

		else:
			visited[0][w,h] = True	

print image.size

