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
import scipy as sc
import scipy.interpolate

x = sp.symbols('x')
f1 = 1/(1 + 25*x**2)
f2 = sp.exp(sp.cos(x))




def uniformPoints(lb, ub, n):
	points = np.zeros((n))
	size = ub - lb
	step = size/(n-1)
	tro = lb
	count = 0;
	while(tro <= ub):
		points[count] = tro;
		tro += step
		count += 1
	points[n-1] = ub
	return points


def chebyPoints(lb, ub, n):
	points = np.zeros((n))
	size = ub - lb
	for i in range (1, n-1):
		points[n - 1 - i] = lb + (size/2)*(1 + math.cos(((2*i - 1)*math.pi)/(2*(n - 1))))
	points[0] = lb
	points[n-1] = ub
	

	return points


def evalPoints(points, f, n):
	ys = np.zeros((n))
	for i in range (0, n):
		ys[i] = f.subs([(x, points[i])])
	return ys


n = 11
upoints = uniformPoints(0, 600, n)
cPoints = chebyPoints(0, 600, n)
print upoints
print cPoints
print
print evalPoints(upoints, f1, n)
print evalPoints(cPoints, f1, n)
print
uy = evalPoints(upoints, f2, n)
print evalPoints(cPoints, f2, n)

f = sc.interpolate.UnivariateSpline(upoints, uy, bbox=[0,600], s=0)

#f10 = sc.interpolate.UnivariateSpline(up10, uy10, bbox=[0,600], s=0)



results = np.zeros((4, 47))
limits = np.zeros((3, 3))
limits[0][0] = -1
limits[0][1] = 1
limits[1][0] = 0
limits[1][1] = 2*math.pi
limits[2][0] = -1
limits[2][1] = 1
function = [f1,f2,f1]
points = [uniformPoints, uniformPoints, chebyPoints]
count = np.zeros((47))


for i in range (0,3):
	for j in range (4, 51):
		p = points[i](limits[i][0], limits[i][1], j)

		y = evalPoints(p, function[i], j)
		if(i != 1):
			f = sc.interpolate.lagrange(p, y)
		else:
			f = sc.interpolate.UnivariateSpline(p, y, bbox=[limits[i][0], limits[i][1]], s = 0)
		p10 = points[i](limits[i][0], limits[i][1], j*10)
		aprox = f(p10)
		count[j-4] = j		
	



		real = evalPoints(p10, function[i], j*10)

		
		results[i][j-4] = np.linalg.norm(aprox - real)
		results[3][j-4] = j

	print "data"
	print aprox
	print real
	sub = plt.subplot(111)
	sub.set_yscale('log')
	#sub.set_xscale('log')
	plt.plot(p, y)
	plt.plot(p10, aprox)
	#plt.plot(p10, real)
	plt.show()	

print results

sub = plt.subplot(111)
sub.set_yscale('log')
sub.set_xscale('log')
plt.plot(results[3], results[0], results[3], results[1], results[3], results[2])
plt.show()







