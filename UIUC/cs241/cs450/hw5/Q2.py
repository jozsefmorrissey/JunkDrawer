from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab
import sympy
from sympy.mpmath import *

def func1(x, y):
	return (x+3)*(y*y*y-7)+18

def func2(x, y):
	return x*y*y*y + 3*y*y*y - 7*x -3

def dFunc1(x0, y0):
	diff(lambda x, y: (x+3)*(y*y*y-7)+18, (x0, y0))

for i in range (0, 10):
	for j in range (0, 10):
		if(func1(i, j) != func2(i,j)):
			print "your failed"
	





