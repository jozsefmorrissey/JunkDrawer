from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab

def printCs(V, n, r, f, T):
	for i in range (0, n):
		if(math.pow(math.fabs(V[i][0]), r)):
			x = math.fabs(V[i][1])/math.pow(math.fabs(V[i][0]), r)
			if(x>0): 
				T[f][i] = x


def g1(x, n, T):
	V = np.zeros((n,2))
	for i in range (0, n):
		V[i][0] = x - 2
		x = (x*x + 2.0)/3.0
		V[i][1] = x - 2
	printCs(V, n, 1, 0, T)


def g2(x, n, T):
	V = np.zeros((n,2))
	for i in range (0, n):
		V[i][0] = x - 2
		x = math.sqrt(3.0*x-2)
		V[i][1] = x - 2
	printCs(V, n, 1, 1, T)


def g3(x, n, T):
	V = np.zeros((n,2))
	for i in range (0, n):
		V[i][0] = x - 2
		x = 3.0 - 2.0/x
		V[i][1] = x - 2
	printCs(V, n, 1, 2, T)


def g4(x, n, T):
	V = np.zeros((n,2))
	for i in range (0, n):
		V[i][0] = x - 2
		x = (x*x - 2.0)/(2.0*x - 3.0)
		V[i][1] = x - 2
	printCs(V, n, 2, 3, T)


x = 10.0
n = 20
T = np.zeros((4, 20,))
g1(x,n,T)
g2(x,n,T)
g3(x,n,T)
g4(x,n,T)

print "\n%20s %20s %20s %20s" %("Function g1", "Function g2", "Function g3", "Function g4")
for i in range (0, 20):
	if(T[0][i] and T[1][i] and T[2][i] and T[3][i]):
		print "%2d%20.10E %20.10E %20.10E %20.10E" %(i, T[0][i], T[1][i], T[2][i], T[3][i])
	elif(T[0][i] and T[1][i] and T[2][i]):
				print "%2d%20.10E %20.10E %20.10E" %(i, T[0][i], T[1][i], T[2][i])
	elif(T[1][i] and T[2][i]):
				print "%2d%20s %20.10E %20.10E" %(i, "", T[1][i], T[2][i])
print



