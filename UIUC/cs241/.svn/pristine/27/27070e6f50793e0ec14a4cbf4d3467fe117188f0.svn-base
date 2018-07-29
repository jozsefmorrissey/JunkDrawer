from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab


A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])
print A

L = np.zeros((11,1))
LL = np.zeros((11,1))
RL = np.zeros((11,1))
N = np.zeros((11,1))


for i in range (0,11):
	L[i] = math.fabs(A[1][0])
	LL[i] = math.fabs(A[2][0])
	print A
	N[i] = i
	RL[i] = math.fabs(A[2][1])
	Q, R = np.linalg.qr(A)
	A = np.dot(R, Q)

sub = plt.subplot(111)
#sub.set_xscale('log')
sub.set_yscale('log')

plt.plot(N, L, N, LL, N, RL)
plt.show()


e = A[1][1]

eignVals = np.zeros((3,3))
eignVals[0][0] = A[1][1]
eignVals[0][1] = A[2][2]
eignVals[0][2] = A[0][0]

print e
print 

A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])

for i in range (0,11):
	L[i] = math.fabs(A[1][0])
	LL[i] = math.fabs(A[2][0])
	N[i] = i
	print A
	print
	RL[i] = math.fabs(A[2][1])
	Q, R = np.linalg.qr(A - e*np.identity((3)))
	A = np.dot(R, Q) + e*np.identity((3))

sub = plt.subplot(111)
#sub.set_xscale('log')
sub.set_yscale('log')

plt.plot(N, L, N, LL, N, RL)
plt.show()

eignVals[1][0] = A[1][1]
eignVals[1][1] = A[2][2]
eignVals[1][2] = A[0][0]

A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])

E, G = np.linalg.eig(A)

eignVals[2][0] = E[1]
eignVals[2][1] = E[2]
eignVals[2][2] = E[0]

print eignVals






