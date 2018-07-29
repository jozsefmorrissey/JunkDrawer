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
		#print "a: ", a, "\t b: ", b, "\t f(xA): ", xA, "\t Count: ", count
	
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
		#print x
	return count, x, ek/ekprev

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
		#if(num + denom == num):
		#	secant(f, og/2, og2/2, tol)
		x = x - num/denom
		count+=1
		ekprev = ek
		ek = math.fabs(x - xprev)
		print ek/math.pow(ekprev, 1)
	print
		#print "x: ", x, "\t xprev: ", xprev, "\t f(x): ", fx, "\t Count: ", count
	return count, x, ek/math.pow(ekprev, 1)#(1 + math.sqrt(5)/2))



#newtons(eqA, -20, .0000000001)
#bisection(eqA, -10, 10, .0000000001)
#secant(eqA, -10, 10, .0000000001)



fmap = [[bisection, secant, newtons], [eqA, eqB, eqC, eqD]]
methods = ["Bisection", "Secant", "Newtons"]
functions = ["A", "B", "C", "D"]
tol = .0000000001

for i in range (0, 3):
	print "\n\n\t\t\t", methods[i], " METHOD! its about to go down"
	for j in range(0, 4):
		it, fval, C = fmap[0][i](fmap[1][j], 10.1, -10., tol)
		print "For ", methods[i], "method with fucntion ", functions[j], "\nIt took ", it, " itterations to converge with a tolerence of ", tol, "\nFinal Value ", fval, "\t C:", C
		print










 

