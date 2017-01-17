from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab


def eqA(x):
	return x*x*x - 2.*x - 5., 3*x*x - 2.

def eqB(x):
	return math.exp(-x) - x, -math.exp(-x) - 1.

def eqC(x):
	return x*math.sin(x) - 1., math.sin(x) + x*math.cos(x)

def eqD(x):
	return x*x*x - 3.*x*x + 3.*x -1., 3.*x*x - 6.*x + 3.


def bisection(f, a, b, tol):
	count = 0
	ek = tol + 1
	ekprev = ek
	while ( ek > tol):
		m = a + (b - a)/2
		xA, xpA = f(a)
		xM, xpM = f(m)
		if((xA > 0 and xM > 0) or (xA < 0 and xM < 0) or (xA == 0 and xM == 0)):
			a = m
		elif(True):
			b = m
		count+=1
		ekprev = ek
		ek = math.fabs(b-a)
	
	return count, m, ek/ekprev


def newtons(f, x, g, tol):
	xprev = 0
	count = 0
	ek = tol + 1
	ekprev = ek
	while (ek > tol):
		fx, fxp = f(x)
		xprev = x
		x = x - fx/fxp
		count+=1
		ekprev = ek
		ek = math.fabs(x - xprev)
	return count, x, ek/math.pow(ekprev, 2)


def secant(f, x, xprev, tol):
	og = x
	og2 = xprev
	count = 0
	ek = tol + 1
	ekprev = ek
	while (ek > tol):
		fx, fxp = f(x)
		fxprev, fxprevp = f(xprev)
		num = fx*(x - xprev)
		denom = fx - fxprev
		xprev = x
		x = x - num/denom
		count+=1
		ekprev = ek
		ek = math.fabs(x - xprev)

	return count, x, ek/(math.pow(ekprev, (1 + math.sqrt(5))/2))



fmap = [[bisection, secant, newtons], [eqA, eqB, eqC, eqD]]
methods = ["Bisection", "Secant", "Newtons"]
functions = ["A", "B", "C", "D"]
tol = .0000000001
it = np.zeros((3, 4))
fval = np.zeros((3, 4))
C = np.zeros((3, 4))

for i in range (0, 3):
	for j in range(0, 4):
		it[i][j], fval[i][j], C[i][j] = fmap[0][i](fmap[1][j], 10.1, -10., tol)

print "\nTolerance: %E\t Initial guess for x: %.1f\t Initial guess for x_(-1): %d\n" %(tol, 8.1, -8)
print "   %35s   %15s   %15s" %(methods[0], methods[1], methods[2])

for j in range (0, 4):
	print "Function %s:" %functions[j]
	print "%20s   %15d   %15d   %15d" %("Iterations:", it[0][j], it[1][j], it[2][j])
	print "%20s   %15f   %15f   %15f" %("Final value:", fval[0][j], fval[1][j], fval[2][j])
	print "%20s   %15f   %15E   %15f\n" %("Error Convergence:", C[0][j], C[1][j], C[2][j])






 

