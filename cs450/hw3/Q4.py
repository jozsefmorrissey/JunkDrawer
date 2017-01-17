from __future__ import division
from copy import copy, deepcopy
import matplotlib.pyplot as plt
import math
import numpy as np
import time as time
import pylab



def deepCopy(mat):
	n = len(mat)
	m = len(mat[0])
	cp = np.zeros((n,m))
	for i in range (0, n):
		for j in range (0, m):
			cp[i][j] = mat[i][j]
	return cp
			

def luDecomp(A):
	#A = np.append(A,np.zeros([len(A), 1]), 1)
	#for i in range (0, n):
		#A[i][n] = b[i]

	#mtb = int(round(time.time()*1000000))
	U = deepCopy(A)
	n = len(A)
	L = np.zeros((n,n))

	for x in range(0, n):
		L[x][x] = 1
		for y in range(x + 1, n):
			scalar = -U[y][x]/U[x][x]
			L[y][x] = U[y][x]/U[x][x]

			for w in range(x, n):
				U[y][w] = U[y][w]+U[x][w]*scalar

	#m = backSub(A, n, mtb, z)
	return L, U



def backSub(U, b):
	n = len(U)
	y = deepCopy(b)

	for j in range(n-1, -1, -1):
		for i in range(n-1, j, -1):
			y[j] -= U[j][i]*y[i]
		y[j] /= U[j][j]

	return y



def forwardSub(U, y):
	n = len(U)
	x = deepCopy(y)

	for j in range(0, n):
		for i in range(0, j):
			x[j] -= U[j][i]*x[i]
		x[j] /= U[j][j]
	return x



def solve(L, U, b):
	y = forwardSub(L, b)
	x = backSub(U, y)

	return y, x


def shift(L, U, u, v, b):
	q, Az = solve(L, U, u)
	q, Ay = solve(L, U, b)

	print "Variable matrix for Az=u: x"
	print Az
	print

	print "Variable matrix for Ay=b: x"
	print Ay
	print

	num = np.dot(np.transpose(v), Ay)
	denom = 1-np.dot(np.transpose(v), Az)

	shiftX = Ay + (num/denom)*Az

	print"My shifted solution"
	print shiftX
	print
	
	if(u[0]): ##to avoid solving if singular matrix is passed
		shiftMat = A - np.dot(u, np.transpose(v))
		solCheck = np.linalg.solve(shiftMat,b)

		print "Check solution vs. Std Lib"
		print solCheck
		print




A = np.array([[1,2,-4],[-7,2,6.0],[3,1,2]])
b = np.array([[-7.0],[-31],[4]])
L, U = luDecomp(A)

y, x = solve(L, U, b)

print("Matrix 'A'")
print A
print

print "Right side vector: b"
print b
print

print
print "a.) LU decomposition"
print "Lower Matrix 'L'"
print L
print

print "Upper Matrix 'U'"
print U
print

print
print "b.) Forward and Backward Substatution"
print "Variable matrix for Ly=b: y"
print y
print

print "Variable matrix for Ux=y: x"
print x
print

print
print "c & d.) My solution"
print x
print

Z = np.linalg.solve(A,b)
print "Check solution vs. Std Lib"
print Z
print


u = np.array([[1],[0],[0]])
v = np.array([[0],[2],[0]])

print
print "e & f.) Sherman-Morrison"

shift(L, U, u, v, b)

print "Solutions are the same the advantage of the Sherman-Morrison is the decrease in work n^2 for sherman compared to n^3 for LU factorization"

u = np.array([[0],[5],[2]])
v = np.array([[0],[1],[0]])

print
print "g.) Sherman-Morrison"

shift(L, U, u, v, b)

print "The sherman-Morrision equations has returned an answer expressed in infinities and nans due to the shifted matrix being singular. This is most likely caused by errors in persision, instead of returning a zero numbers become increasingly high or low."

u = np.array([[1],[1],[1]])
v = np.array([[1],[1],[1]])

print
print "h.) Sherman-Morrison"

shift(L, U, u, v, b)

print
print "i.) The Sherman-Morrison equation could be used to solve this nxn matrix. The LU decomposition of a diagonal can be constructed in O(n) time (two diagonal matrices) the identity for L and the original subtracting one from each entry for U. Then using two vectors [1,1,...,1]^t for u and v since adding thier dot product will create the desired matrix. This algorithum can arrive at the solution in O(n^2 + n) or more concisely O(n^2)"














