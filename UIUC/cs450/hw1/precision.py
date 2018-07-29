from __future__ import division
import matplotlib.pyplot as plt
import math


y=.1
nprev=1
for x in range (0, 15):
	n=nprev
	while (1+n>1):
		nprev=n
		n=n/(1+y)
	y=y/10
	print (nprev)

	
