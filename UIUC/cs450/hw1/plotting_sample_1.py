from __future__ import division
import matplotlib.pyplot as plt
import math

# helper functions:


def f(h):
    return (math.sin(.7+2*h)-2*math.sin(0.7+h)+math.sin(0.7))/(h*h)


def g(x):
    return -math.sin(.7+x)

def gangster(x):
	return x

def prostitute(x):
	return 4*math.pow(2, -53)/(x*x)

# setup domain:
x0 = 0.7
xmax = 1
steps = 25
h = [math.pow(2, -x) for x in range(xmax, xmax*steps)]

# function evaluations:
f_vals = [gangster(x) for x in h]
g_vals = [prostitute(x) for x in h]
difference = [abs(f(x)-g(x)) for x in h]

# plot:
# (uncomment these for log scale)

sub = plt.subplot(111)
sub.set_xscale('log')
sub.set_yscale('log')

plt.plot(h, f_vals, h, g_vals, h, difference)
plt.show()


