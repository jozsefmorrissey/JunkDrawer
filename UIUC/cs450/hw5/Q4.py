from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab
import sympy as sp
from sympy import *
import scipy as sc
from scipy import optimize
from pdb import set_trace as bp
from sympy import init_printing

fml = sp.Matrix([0,0])
fyl = sp.Matrix([0,0])
x, y = sp.symbols('x y')
f = sp.Matrix([100*(y - x**2)**2 + (1 - x)**2])
gradientf = f.jacobian([x,y])
hessianf = gradientf.jacobian([x,y])

<<<<<<< HEAD
def distance(x):
	return math.sqrt((x[0] - 1)*(x[0] - 1) + (x[1] - 1)*(x[1] - 1))

def function(alf):

	lol = fml + alf[0]*fyl

	retVal = f.subs([(x, lol[0]),(y, lol[1])])[0]

	return retVal

def lineSearch(xx, s):
	global fml
	fml = xx
	global fyl
	fyl = s

	return sc.optimize.minimize(function, 0)



def newtonsLineSearch(xx, s):
	aa = 0
	a = sp.symbols('a')
	xxx = xx + a*s
	pk = f.subs([(x, xxx[0]), (y, xxx[1])])
	p = pk.jacobian([a])
	dp = p.jacobian([a])
	for i in range (0, 4):
		#pprint(pk)
		pp = p.subs([(a, aa)])[0].evalf()
		dpp = dp.subs([(a, aa)])[0].evalf()
		#print "a: ", aa, "\tp: " , pp.evalf(), "\tdp: ", dpp.evalf()

		aa = (aa - pp/dpp).evalf()

	return aa




residual = np.zeros((2,3,11))
valBank = np.array([[-1,1],[0,1],[2,1]])

for i in range (0, 3):
	xx = valBank[i].T
	it = 0
	while(it < 11):
		H = hessianf.subs([(x, xx[0]), (y, xx[1])])
		grad = gradientf.subs([(x, xx[0]), (y, xx[1])]).T
		sk = H.LUsolve(-grad)
		xx = xx + sk
		if (distance(xx) < 1e-16):
			break
		it += 1

	print "Initial Values:"
	print "x: ", valBank[i][0], "\t y:", valBank[i][1]
	print "Final Values:"
	print "x: ", xx[0].evalf(), "\t y: ", xx[1].evalf()
	print "Number of Iterations:\n", it
	print "Residual:"
	pprint((xx-sp.Matrix([1,1])).evalf())
	print



for i in range (0, 3):
	xx = valBank[i].T
	print "Round: " , i
	it = 0
	while(True):
		if(i == 1):
			break
		sk = -gradientf.subs([(x, xx[0]), (y, xx[1])]).T
		retVal = newtonsLineSearch(xx, sk)
		alpha = retVal
		xx = xx + alpha*sk
		if (distance(xx) < 1e-16):
			break
		if(it %100 == 0):
			pprint(xx)
			print it
		it += 1

	print "Initial Values:"
	print "x: ", valBank[i][0], "\t y:", valBank[i][1]
	print "Final Values:"
	print "x: ", xx[0], "\t y: ", xx[1]
	print "Number of Iterations:\n", it
	print "Residual:"
	pprint((xx-sp.Matrix([1,1])).evalf())
	print		


=======
def gradRos(x,y):
	grad = np.zeros((2,1))
	grad[0][0] = 2*(200*x*x*x - 200*x*y + x - 1)
	grad[1][0] = 200*(y - x*x)
	return grad

def jacRos(x, y):
	print x
	print y
	shac = np.zeros((2,2))
	shac[0][0] = 1200*x*x - 400*y + 2
	shac[0][1] = shac [1][0] = -400
	shac[1][1] = 200
	return shac

def distance(x):
	return (x[0][0] - 1)*(x[0][0] - 1) + (x[1][0] - 1)*(x[1][0] - 1)
	


og = np.array([[-1,0,2],[1,1,1]])
j = 0
for i in range (0,3):
	print "run: ", i
	x = np.array([[og[0][i]],[og[1][i]]])
	while(True):
		#print x
		J = jacRos(x[0][0], x[1][0])
		fx = -gradRos(x[0][0], x[1][0])
		sk = np.linalg.solve(J, fx)
		#print dx
		x = sk + x
		#print x
		j += 1
		print distance(x)
		if(distance(x) <= .0000000000000001 or j>50):
			break
	
	print
>>>>>>> ee4155aa80460c371c27e1845d304d7dd07e2d8e





