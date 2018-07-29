from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab

def printCs(V, n, r):
	for i in range (0, n):
		if(math.pow(math.fabs(V[i][0]), r)):
			print math.fabs(V[i][1])/math.pow(math.fabs(V[i][0]), r)

def g1(x, n):
	V = np.zeros((n,2))
	print "Funciton g1"
	for i in range (0, n):
		V[i][0] = x - 2
		x = (x*x + 2.0)/3.0
		V[i][1] = x - 2
	printCs(V, n, 1)
	print

def g2(x, n):
	V = np.zeros((n,2))
	print "Funciton g2"
	for i in range (0, n):
		V[i][0] = x - 2
		x = math.sqrt(3.0*x-2)
		V[i][1] = x - 2
	printCs(V, n, 1)
	print

def g3(x, n):
	V = np.zeros((n,2))
	print "Funciton g3"
	for i in range (0, n):
		V[i][0] = x - 2
		x = 3.0 - 2.0/x
		V[i][1] = x - 2
	printCs(V, n, 1)
	print

def g4(x, n):
	V = np.zeros((n,2))
	print "Funciton g4"
	for i in range (0, n):
		V[i][0] = x - 2
		x = (x*x - 2.0)/(2.0*x - 3.0)
		V[i][1] = x - 2
	printCs(V, n, 2)
	print

x = 10.0
n = 20
g1(x,n)
g2(x,n)
g3(x,n)
g4(x,n)
