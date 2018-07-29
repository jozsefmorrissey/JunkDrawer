from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab


def fVal(n):
	pn = np.ones((n+1))
	c = -1
	#print pn
	for i in range (0, n+1) :
		pn[i] = 1/(1 + 25*c*c)
		c += 2/n
	return pn

def fA(n, pn):
	A = np.zeros((n+1, n+1))
	c = -1
	for i in range (0, n+1):
		for j in range (0, n+1):
			A[i][j] = pow(c, j)
		c += 2/n
	return A

def fPoints(Z, m, n):
	fx = fVal(m)
	PN = np.zeros((m + 1))
	c = -1
	x = np.zeros((m + 1))
	for i in range(0, m + 1):
		for j in range(0, n):		
			PN[i] += Z[j]*pow(c,j)
		x[i] = c
		c += 2/m

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
pn = fVal((n))
A = fA(n, pn)

#print pn
#print A

Z = np.linalg.solve(A,pn)

#print Z

fPoints(Z, m, n)



