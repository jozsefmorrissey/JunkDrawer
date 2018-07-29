from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab


def fVal(v):
	pn = np.ones((n+1))

	#print pn
	for i in range (0, len(v)) :
		pn[i] = 1/(1 + 25*v[i]*v[i])
		
	return pn

def fVals(m):
	pn = np.ones((m+1))
	c = -1
	#print pn
	for i in range (0, m+1) :
		pn[i] = 1/(1 + 25*c*c)
		c += 2/m
	print "her"
	print m
	print len(pn)
	return pn

def chVal(n):
	pn = np.zeros((n+1))
	c = -1
	for i in range (0, n+1):
		pn[i] = math.cos(math.pi*i/n)

	return pn

def fA(v, pn):
	A = np.zeros((len(v), len(v)))

	for i in range (0, len(v)):
		for j in range (0, len(v)):
			A[i][j] = pow(v[i], j)
	print len(v)
	print len(A)
	return A

def fPoints(Z, m, n):

	fx = fVals(m)
	PN = np.zeros((m + 1))
	c = -1
	x = np.zeros((m + 1))
	for i in range(0, m + 1):
		for j in range(0, n):		
			PN[i] += Z[j]*pow(c,j)
		x[i] = c
		c += 2/m

	print "it begins@@@@"
	print len(fx)
	print len(PN)
	print len(x)

	sub = plt.subplot(111)
#sub.set_xscale('log')
	sub.set_yscale('log')

	plt.plot(x, fx, x, PN)
	plt.show()


n = 40
m = 200
pn = np.ones((n+1))
A = np.zeros((n+1, n+1))
v = chVal(n)
pn = fVal(v)
A = fA(v, pn)

Z = np.linalg.solve(A,pn)

fPoints(Z, m, n)


