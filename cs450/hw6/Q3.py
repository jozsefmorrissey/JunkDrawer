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



x = [1,-7,-9,-8,-5,-2,1,2,1,-1,-5,-7,-8,-6,-3,1]
y = [-1,8,15,22,24,24,21,16,7,-7,-15,-15,-13,-8,-4,1]
t = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]

drawpts = np.zeros((10000))
for i in range (0, 10000):
	drawpts[i] = i*15/10000
	if(drawpts[i] > 16):
		print "no fucking way"

fx = sc.interpolate.UnivariateSpline(t, x, bbox = [0,15], s = 0)
fy = sc.interpolate.UnivariateSpline(t, y, bbox = [0,15], s = 0)

setx = fx(drawpts)
sety = fy(drawpts)
plt.axis("equal")
plt.plot(setx, sety)
plt.show()
