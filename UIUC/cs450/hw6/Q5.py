from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab
import sympy as sp
from sympy import *
from pdb import set_trace as bp
from sympy import init_printing

x = sp.symbols('x')
fcheck = x**3
#f = 4/(1 + x**2)

def f(x):
	return 4/(1 + x**2)

def mid(a, b, fx):
	return (b-a)*fx((a+b)/2)

def trap(a,b,fx):
	return ((b-a)/2)*(fx(a) + fx(b))

def simp(a,b,fx):
	return ((b-a)/6)*(fx(a) + fx(b) + 4*fx((a+b)/2))

def makePoints(a, b, n):
	pts = np.zeros(((n), 2))
	size = (b-a)/n
	i = 0
	while(a + size <= b):
		pts[i][0] = a
		a = a+size
		pts[i][1] = a
		i+=1
		#print a 
	return pts


print mid(0,1,f)
print trap(0,1,f)
print simp(0,1,f)

w = 1000


realpi = math.pi
pi = 0;
error = np.zeros((2, w))
for n in range (1, w):
	p = makePoints(0,1,n)
	pi = 0
	for i in range (0, n):
		pi += mid(p[i][0], p[i][1], f)
	print i
	error[0][n] = math.fabs(realpi - pi)
	error[1][n] = (1 - 0)/n

print "Midpoint"
print error
sub = plt.subplot(111)
sub.set_yscale('log')
sub.set_xscale('log')

plt.plot(error[1], error[0], error[1], error[1])
plt.show()


realpi = math.pi
pi = 0;
error = np.zeros((2, w))
for n in range (1, w):
	p = makePoints(0,1,n)
	pi = 0
	for i in range (0, n):
		pi += trap(p[i][0], p[i][1], f)
	print i
	error[0][n] = realpi - pi
	error[1][n] = (1 - 0)/n

print "Trapizoid"

sub = plt.subplot(111)
sub.set_yscale('log')
sub.set_xscale('log')

plt.plot(error[1], error[0], error[1], error[1])
plt.show()

realpi = math.pi
pi = 0;
error = np.zeros((2, w))
for n in range (1, w):
	p = makePoints(0,1,n)
	pi = 0
	for i in range (0, n):
		pi += simp(p[i][0], p[i][1], f)
	print i
	error[0][n] = math.fabs(realpi - pi)
	error[1][n] = (1 - 0)/n

print "Simpson"
print error[0]
sub = plt.subplot(111)
sub.set_yscale('log')
sub.set_xscale('log')

plt.plot(error[1], error[0], error[1], error[1])
plt.show()
