from __future__ import division

import math
import numpy as np
import time as time
import matplotlib.pyplot as plt

import scipy as sc
from scipy import optimize
from pdb import set_trace as bp

def forward(h, y):
	return y + -5*h*y

def backward(h, y):
	return y/(1-h*-5)

#print forward(.5, 1)
#print backward(.5, 1)

vals = np.zeros((3, 4/.1 + 1))
vals[0][0] = 1
vals[1][0] = 1
vals[2][0] = 0
for i in range (1, 4/.1 + 1):
	vals[2][i] = .1 + vals[2][i-1]
	vals[1][i] = backward(.5, vals[1][i-1])
	vals[0][i] = forward(.5, vals[0][i-1])

#print vals

plt.xlabel('t_k')
plt.ylabel('y_k')
plt.title('Euler Forward')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
#sub.set_yscale('log')
#sub.set_xscale('log')
plt.plot(vals[2], vals[0])
plt.show()
	
plt.xlabel('t_k')
plt.ylabel('y_k')
plt.title('Euler Backward')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
#sub.set_yscale('log')
#sub.set_xscale('log')
plt.plot(vals[2], vals[1])
plt.show()
