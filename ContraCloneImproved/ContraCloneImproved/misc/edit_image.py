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
	buff_dist = 5

	colored.append((width, height, 0))
	while(colored or notcolored):
		if(colored):
			loc = colored.pop(0)
		else:
			loc = notcolored.pop(0)

		if(loc[2] < buff_dist):

			maxW = max(loc[0], maxW)
			maxH = max(loc[1], maxH)
			minW = min(loc[0], minW)
			minH = min(loc[1], minH)
		
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
	if(not out_of_bounds and not visited[width, height]):
		visited[width, height] = True
		if(pix[width, height] == 0):
			notcolored.append((width, height, miss_count + 1))
		else:
			colored.append((width, height, 0))





def find_crop_image(width, height, visited, image_width, image_height):
	out_of_bounds = (width < 0 or height < 0 or image_width <= width or image_height <= height)
	if(out_of_bounds or pix[width, height] == 0 or visited[width, height]):
		visited = True
		return 0, 0, Decimal('Infinity'), Decimal('Infinity')
	visited[width, height] = True
	maxH1, maxW1, minH1, minW1 = find_crop_image(width + 1, height + 1, visited, image_width, image_height)
	maxH2, maxW2, minH2, minW2 = find_crop_image(width - 1, height + 1, visited, image_width, image_height)
	maxH3, maxW3, minH3, minW3 = find_crop_image(width + 1, height - 1, visited, image_width, image_height)
	maxH4, maxW4, minH4, minW4 = find_crop_image(width - 1, height - 1, visited, image_width, image_height)

	maxH = max(maxH1, maxH2, maxH3, maxH4, height)
	maxW = max(maxW1, maxW2, maxW3, maxW4, width)
	minH = min(minH1, minH2, minH3, minH4, height)
	minW = min(minW1, minW2, minW3, minW4, width)

	maxH1, maxW1, minH1, minW1 = find_crop_image(width, height + 1, visited, image_width, image_height)
	maxH2, maxW2, minH2, minW2 = find_crop_image(width - 1, height, visited, image_width, image_height)
	maxH3, maxW3, minH3, minW3 = find_crop_image(width, height - 1, visited, image_width, image_height)
	maxH4, maxW4, minH4, minW4 = find_crop_image(width + 1, height, visited, image_width, image_height)

	maxH = max(maxH1, maxH2, maxH3, maxH4, maxH)
	maxW = max(maxW1, maxW2, maxW3, maxW4, maxW)
	minH = min(minH1, minH2, minH3, minH4, minH)
	minW = min(minW1, minW2, minW3, minW4, minW)

	return maxH, maxW, minH, minW
	




image = Image.open("contra_images.png")

scale = 1
width, height = image.size

image = image.resize((int(scale*width), int(scale*height)), PIL.Image.ANTIALIAS)
pix = image.load()

visited = np.zeros((width,height), dtype=bool)
visited2 = np.zeros((width,height), dtype=bool)

count = 0;
for h in range (0, height):
	for w in range (0, width):
		if(pix[w,h] != 0 and not visited2[w,h]):
			count += 1
			print count

			maxH, maxW, minH, minW = find_crop_bfs(w, h, visited2, width, height, pix)
			print minW, maxW, minH, maxH
			cropped = image.crop((minW, minH, maxW, maxH))
			cropped.show()

			raw_input();
			print maxH, maxW, minH, minW
		else:
			visited2[w,h] = True	
		#print "coordinates: ", width, height
	print

print image.size

image.show()
image.crop((0, 0, width, height)).show()
print height
print width
