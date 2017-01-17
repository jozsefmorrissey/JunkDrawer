import png
#from PIL import Image
import Image, ImageDraw, ImageFont
import math
import numpy as np
from operator import sub
import sys
from sys import argv



f = "Info/info.txt"

txt = open(f)

#print txt.read()

for line in txt:
	if "Doors" in line:
		d = int(line[7:])
		
	if "Islands" in line: 
		print line [9:]
		quantity = (len(line) - 10)/6
		i = []
		start = 0
		x = line[9:]
		for j in range(0,quantity):
			end_bay = x.index("/")
			bay = int(x[start: end_bay])
			end_location = x.index(";")
			location = x[end_bay + 1: end_location]
			end_orientation = x.index(",")
			orientation = x[end_location + 1: end_orientation]
			i.append((bay, location, orientation))	
			x = x[end_orientation + 1:]


	if "Scales" in line: 
		quantity = (len(line) - 9)/4
		s = []
		start = 0
		x = line[8:]
		for j in range(0,quantity):
			end_bay = x.index("/")
			bay = int(x[start: end_bay])
			end_location = x.index(",")
			location = x[end_bay + 1: end_location]
			s.append((bay, location))			
			x = x[end_location + 1:]
			
			
print "doors", d
print "islands", i
print "scales ", s
	
	
