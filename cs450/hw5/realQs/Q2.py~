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


x, y = sp.symbols('x y')
f = sp.Matrix([(x + 3)*(y**3 - 7) + 18, sp.sin(y*sp.exp(x) - 1)])
gradientf = f.jacobian([x,y])

def distance(x):
	return math.sqrt((x[0])*(x[0]) + (x[1] - 1)*(x[1] - 1))



init_printing()
#pprint(gradientf.subs([(x, -0.5),(y,1.4)]))
#pprint(gradientf.evalf())

Error = np.zeros((3,9))
xx = np.array([-0.5,1.4])
i = 0
Error[0][i] = i
Error[1][i] = distance(xx)
while (i<20):
	j = gradientf.subs([(x, xx[0]),(y,xx[1])])
	fx = f.subs([(x, xx[0]),(y,xx[1])])
	dx = j.LUsolve(-fx)
	xx = dx + xx
	Error[0][i + 1] = i + 1
	Error[1][i + 1] = distance(xx)
	if (distance(xx) < 1e-12):
		break
	i += 1

plt.xlabel('Iterations')
plt.ylabel('Magnitude')
plt.title('Newtons Method Error')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
sub.set_yscale('log')

plt.plot(Error[0], Error[1])
plt.show()	

xx = np.array([-0.5,1.4])
i = 0
Error[2][i] = distance(xx)
B = gradientf.subs([(x, xx[0]),(y,xx[1])])
while (i<20):
	fx = f.subs([(x, xx[0]),(y,xx[1])])
	sk = B.LUsolve(-fx)	
	xt = xx + sk
	yk = f.subs([(x, xt[0]),(y,xt[1])]) -f.subs([(x, xx[0]),(y,xx[1])])
	num = (yk - B*sk)*sk.T
	#pprint(sk)
	denom = sk.T*sk
	#print denom
	B = B + num/denom[0]
	Error[0][i + 1] = i + 1
	Error[2][i + 1] = distance(xt)
	xx = xt
	if (distance(xx) < 1e-12):
		break

	i += 1

print Error

plt.xlabel('Iterations')
plt.ylabel('Magnitude')
plt.title('Broydens Method Error')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
sub.set_yscale('log')

plt.plot(Error[0], Error[2])
plt.show()	





