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
f = 0.5*x**2 - sp.sin(x)

a = 0
b = 4
t = (math.sqrt(5) - 1)/2
x1 = a + (1 - t)*(b-a)
f1 = f.subs([(x, x1)])
x2 = a + t*(b - a)
f2 = f.subs([(x, x2)])
it = 0
while(math.fabs(a - b) > 1e-15):
	if(f1 > f2):
		a = x1
		x1 = x2
		f1 = f2
		x2 = a + t*(b - a)
		f2 = f.subs([(x, x2)])
	else:
		b = x2
		x2 = x1
		f2 = f1
		x1 = a + (1 - t)*(b-a)
		f1 = f.subs([(x, x1)])	
	it += 1		
	
print "number of itterations: ", it
print "Error: ", math.fabs(a-b)
print "Value: ", (a+b)/2


