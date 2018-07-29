from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab


def cfVal(v):
	pn = np.ones(len(v))

	#print pn
	for i in range (0, len(v)) :
		pn[i] = 1/(1 + 25*v[i]*v[i])
		
	return pn

def cfVals(m):
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

def cchVal(n):
	pn = np.zeros((n+1))
	c = -1
	for i in range (0, n+1):
		pn[i] = math.cos(math.pi*i/n)

	return pn

def cfA(v, pn):
	A = np.zeros((len(v), len(v)))

	for i in range (0, len(v)):
		for j in range (0, len(v)):
			A[i][j] = pow(v[i], j)
	print len(v)
	print len(A)
	return A

def cfPoints(Z, m, n):

	fx = cfVals(m)
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


#sub = plt.subplot(111)
#sub.set_xscale('log')
#sub.set_yscale('log')

	#plt.plot(x, fx, x, PN)
	retVal = 0
	for i in range (0, n+1):
		if(math.fabs((fx[i]-PN[i]))/fx[i] > retVal):
			retVal = (fx[i]-PN[i])/fx[i]


def cheby(n):
	m = 200
	pn = np.ones((n+1))
	A = np.zeros((n+1, n+1))
	v = cchVal(n)
	pn = cfVal(v)
	A = cfA(v, pn)

	Z = np.linalg.solve(A,pn)

	retVal = cfPoints(Z, m, n)

	return np.linalg.cond(A)






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

#sub = plt.subplot(111)
#sub.set_xscale('log')
#sub.set_yscale('log')

	#plt.plot(x, fx, x, PN)
	
	retVal = 0
	for i in range (0, n+1):
		if(math.fabs((fx[i]-PN[i]))/fx[i] > retVal):
			retVal = (fx[i]-PN[i])/fx[i]


def uni(n):
	m = 200
	pn = np.ones((n+1))
	A = np.zeros((n+1, n+1))
	pn = fVal((n))
	A = fA(n, pn)

	#print pn
	#print A

	Z = np.linalg.solve(A,pn)

	#print Z

	retVal = fPoints(Z, m, n)

	return np.linalg.cond(A)



U = np.zeros((39))
C = np.zeros((39))
N = np.zeros((39))
for n in range (10, 11):
	retC = cheby(n)
	retU = uni(n)
	U[n-2] = retU
	C[n-2] = retC
	N[n-2] = n

print U
print C
print N

sub = plt.subplot(111)
#sub.set_xscale('log')
sub.set_yscale('log')

plt.plot(N, U, N, C)
plt.show()
