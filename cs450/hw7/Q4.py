from __future__ import division

import math
import numpy as np
import time as time
import matplotlib.pyplot as plt

import scipy as sc
from scipy import optimize
from pdb import set_trace as bp


def k_1(z, x, y, h):
	return func(z, x, y)

def k_2(z, x, y, h, k):
	return func(z + h*k/2, x, y)

def k_3(z, x, y, h, k):
	return func(z + h*k/2, x, y)

def k_4(z, x, y, h, k):
	return func(z + h*k, x, y)

def func(z, x, y):
	r = (x**2 + y**2)**(1/2)
	return y/r**6

def runge(z, z1, z2, z3, z4, h):
	return z + h*(z1 + 2*z2 + 2*z3 + z4)/6



h = 2*math.pi/(2**15) 
cds = np.zeros((2, 2**15))
cds[0][0] = 1 - math.exp(1)
cds[1][0] = 0
for i in range (0, 2*15 - 1):
	xk1 = k_1(cds[0][i], cds[0][i], cds[1][i], h) 
	xk2 = k_2(cds[0][i], cds[0][i], cds[1][i], h, xk1)
	xk3 = k_3(cds[0][i], cds[0][i], cds[1][i], h, xk2)
	xk4 = k_4(cds[0][i], cds[0][i], cds[1][i], h, xk3)

	yk1 = k_1(cds[1][i], cds[0][i], cds[1][i], h)
	yk2 = k_2(cds[1][i], cds[0][i], cds[1][i], h, yk1)
	yk3 = k_3(cds[1][i], cds[0][i], cds[1][i], h, yk2)
	yk4 = k_4(cds[1][i], cds[0][i], cds[1][i], h, yk3)

	cds[0][i + 1] = runge(cds[0][i], xk1, xk2, xk3, xk4, h)
	cds[0][i + 1] = runge(cds[1][i], yk1, yk2, yk3, yk4, h)


plt.xlabel('Iteration')
plt.ylabel('error')
plt.title('Error for methods given: v=0.1 (log-log plot)')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
#sub.set_yscale('log')
#sub.set_xscale('log')
plt.plot(cds[0], cds[1])

#plt.legend(loc='best')
plt.show()

print cds





