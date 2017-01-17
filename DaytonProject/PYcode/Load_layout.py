import png
#from PIL import Image
import Image, ImageDraw, ImageFont
import math
import numpy as np
from operator import sub
import sys
from sys import argv


def display_location(x, y):
	l = 10
	x1 = x - l
	x2 = x + l
	y1 = y - l
	y2 = y + l
	draw.line((x1,y1,x2,y2), width = 1, fill = (0,0,0));
	draw.line((x2,y1,x1,y2), width = 1, fill = (0,0,0));

def sort_data(data):
	for j in range (0, len(data) - 1):
		for i in range (0, len(data) - 1):
			if(len(data[i]) <= len(data[i+1])):
				if((len(data[i]) == len(data[i+1]) and data[i] > data[i + 1]) or len(data[i]) < len(data[i+1])):
					td = data[i]
					data[i] = data[i+1]
					data[i+1] = td

	return data

def get_load_plan():
	f = "../Info/Load_Plan.txt"

	txt = open(f)

	data = []

	for line in txt:
		print "line", line
		destination = line[0:3];
		load = []
		center = []
		head = []
		tail = []
		load.append(destination)
		x = line[4:]
		end_door = x.index(":")
		door = x[0: end_door]
		load.append(door)
		x = x[end_door + 2:]
		number_destinations = line.count("/")
		load.append(0)
		load.append(0)
		load.append(0)
		load.append(0)
		for i in range (0, number_destinations):
			next = x.index("/")
			dt = x[0:next]
			if "Head" in dt:
				load[4] += 1
				dt = dt[0: 3]
				head.append(dt)
			elif "Tail" in dt:
				load[5] += 1
				dt = dt[0: 3]
				tail.append(dt)
			else:
				center.append(dt)
			x = x[next + 1:]
		for i in range (0, len(head)):
			load.append(head[i])
		for i in range (0, len(center)):
			load.append(center[i])
		for i in range (0, len(tail)):
			load.append(tail[i])
		print load
		data.append(load)
	return data


def weightANDhu(data):
	f = "../Info/incoming.txt"

	txt = open(f)

	for line in txt:
		for i in range (0, len(data)):
			for j in range (6, len(data[i])):
				if data[i][j] in line:
					wstart = 4
					wend = line.index("/")
					data[i][2] += int(line[wstart: wend])
					huend = line.index("\n")
					data[i][3] += int(line[wend + 1: huend])
			
	return data
	
	
def erase_block(x,y,x2,y2):
	w = y - y2
	y = y + w/2
	print "x, x2, y, w: ", x, x2, y, w
	draw.line((x,y,x2,y), width = w, fill = (255,255,255));
	
	
def find_lowest_point(xy):
	maximum = 0
	for i in range (1, len(xy)):
		if(xy[maximum][1] > xy[i][1] and xy[i][1] != 0):
			maximum = i
	return maximum, xy[maximum][0], xy[maximum][1]
	

def draw_square(x, y, fx, fy, w):
	draw.line((x,y,x ,fy), width = w, fill = (0,0,0));
	draw.line((x, y, fx,y ), width = w, fill = (0,0,0));
	draw.line((fx,fy , x, fy), width = w, fill = (0,0,0));
	draw.line((fx,fy,fx,y ), width = w, fill = (0,0,0));	
	


#Data format {Destination, Door Number, Weight, Handling Units, Number of Head Load Destinations, Number of Tail Load Destinations, List of Destinations}
data = get_load_plan()
s = 1.5
data = weightANDhu(data)
data = sort_data(data)
size_bay_number_DT = int(s*25)
size_weight_hu = int(s*15)
size_dts = int(s*15)
tiny_size = int(s*10)

length = int(2*900)
width = int(4*300)

image = Image.new("RGBA", (length,width), (255,255,255))
draw = ImageDraw.Draw(image)
header_font = ImageFont.truetype("../TextFormats/FreeMonoOblique.ttf", size_bay_number_DT)
weight_font = ImageFont.truetype("../TextFormats/FreeMonoOblique.ttf", size_weight_hu)
dts_headTail_font = ImageFont.truetype("../TextFormats/FreeMonoBoldOblique.ttf", size_dts)
tiny_font = ImageFont.truetype("../TextFormats/FreeMonoOblique.ttf", tiny_size)

b = 30
back = 0
x = b
y = b
end_location = np.zeros((len(data), 2))
startx, starty = 0,0
for i in range(0, len(data)):
	if(data[i][4] > 0):
		dts_font = dts_headTail_font
	else:
		dts_font = ImageFont.truetype("../TextFormats/FreeMonoOblique.ttf", size_dts)
	br = 0
	for k in range(0, i):
		if(data[i][0] == data[k][0] and len(data[i]) == len(data[k])):
			check = 0
			for l in range (4, len(data[i])):
				if(data[i][l] != data[k][l]):
					print "l: ", l
					check = 1
			if check: break
			tx = x - s*80*(k - i +2.25) 
			st = (data[i][1] + "/" + data[k][1]).replace(" ", "")
			erase_block(tx, y, tx+s*80, y - size_bay_number_DT)
			draw.text((tx + 5*s, y), st, (0,0,0), font=header_font)

			#draw.text((tx + 5*s, y), "27/25", (0,0,0), font=header_font)
			br = 1
			break
	if br: continue
	if(x + s*80 > length and back != 1):
		back_i = i-1
		back = 1
	if(back):	
		back_i, x, y =find_lowest_point(end_location)
	startx, starty = x, y
		
	draw.text((x + 5*s, y), data[i][1], (0,0,0), font=header_font)
	y += size_bay_number_DT
	draw.text((x + 11*s, y), data[i][0], (0,0,0), font=header_font)
	x = x
	y = y + size_bay_number_DT
	draw.text((x, y), str(data[i][3]) + ":" + str(data[i][2]), (0,0,0), font=weight_font)
	x = x + 20*s
	#draw.text((x, y), str(data[i][2]), (0,0,0), font=weight_font)
	y +=size_weight_hu	
	x -= 2*s
	for j in range(6, len(data[i])):
		if(j == 6 + data[i][4] and data[i][4] > 0):
			draw.text((x - 15*s, y), "HEAD HEAD", (0,0,0), font=tiny_font)
			dts_font = ImageFont.truetype("../TextFormats/FreeMonoOblique.ttf", size_dts)
			y += tiny_size
	
		if(j == len(data[i]) - data[i][5]):
			draw.text((x - 15*s, y), "TAIL TAIL", (0,0,0), font=tiny_font)
			y += tiny_size
			dts_font = ImageFont.truetype("../TextFormats/FreeMonoBoldOblique.ttf", size_dts)
			
		st = str(data[i][j]).replace(" ", "")
		draw.text((x, y), st, (0,0,0), font=dts_font)
		y += size_dts
		

	c = s*5
	draw_square(-c+startx, -c+starty, c+startx + s*80, y - c + 20, 1)
	if(back):
		end_location[back_i][1] = y+20
	else:
		end_location[i] = (startx, y+20)
	if(int(end_location[i][0]) < b and i>0):# == int(end_location[0][0])):
		back = 2
	if(back == 1):
		print "back_i: ", back_i
		back_i -= 1
		print "back_i: ", back_i
	
	elif ( back == 2 and x + 2*s*80 < length):
		back_i -= 1
		
	else:
		x += s*80
		y = b
	
print end_location
for i in range (0, len(end_location)):
	display_location(end_location[i][0], end_location[i][1])
	
f = open('../Images/load_plan.png', 'wb')
image.save(f, "PNG")
print "Load_layout Complete"




























