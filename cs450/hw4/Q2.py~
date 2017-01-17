from __future__ import division
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab

def eigens(i, j, A, e):

	e[i][j][0] = A[1][1]
	e[i][j][1] = A[2][2]
	e[i][j][2] = A[0][0]
	
def eignError(e, E):
	v = np.zeros((2, 3))
	for c in range (0, 2):
		for j in range(0, 3):
			for i in range (0, 3):
				if(math.fabs(E[i] - e[2][c][j]) < .5):
					v[c][j] = math.fabs(1 - e[2][c][j]/E[i])
					break
	return v


A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])

L = np.zeros((11,1))
LL = np.zeros((11,1))
RL = np.zeros((11,1))
N = np.zeros((11,1))
eignVals = np.zeros((11,3,3))

for i in range (0,11):
	L[i] = math.fabs(A[1][0])
	LL[i] = math.fabs(A[2][0])
	N[i] = i
	RL[i] = math.fabs(A[2][1])
	Q, R = np.linalg.qr(A)
	A = np.dot(R, Q)
	eigens(i, 0, A, eignVals)


plt.xlabel('Iterations')
plt.ylabel('Magnitude')
plt.title('QR Iteration: Magnitude of lower triangle')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
sub.set_yscale('log')

plt.plot(N, L, N, LL, N, RL)
plt.show()


e = A[1][1]


A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])

for i in range (0,11):
	L[i] = math.fabs(A[1][0])
	LL[i] = math.fabs(A[2][0])
	N[i] = i
	RL[i] = math.fabs(A[2][1])
	Q, R = np.linalg.qr(A - e*np.identity((3)))
	A = np.dot(R, Q) + e*np.identity((3))
	eigens(i, 1, A, eignVals)


plt.xlabel('Iterations')
plt.ylabel('Magnitude')
plt.title('Shifted QR Iteration: Magnitude of lower triangle')
plt.text(69, .025, r'$\mu=100, \ \sigma=1$')

sub = plt.subplot(111)
sub.set_yscale('log')

plt.plot(N, L, N, LL, N, RL)
plt.show()

A = np.array([[-261.0, 209, -49], [-530, 422, -98], [-800, 631, -144]])

E, G = np.linalg.eig(A)

eignVals[0][2][0] = E[1]
eignVals[0][2][1] = E[2]
eignVals[0][2][2] = E[0]


v = eignError(eignVals, E)
print "\nError of non-shifted QR eigenvalues after 2 iterations is: "
print "\tFirst: ", v[0][0], "\tSecond: ", v[0][1], "\tThird: ", v[0][2], "\n\tTotal Error: ", v[0][0]+v[0][1]+v[0][2]

print "\nError of shifted QR eigenvalues after 2 iterations is: "
print "\tFirst: ", v[1][0], "\tSecond: ", v[1][1], "\tThird: ", v[1][2], "\n\tTotal Error: ", v[1][0]+v[1][1]+v[1][2]
print



