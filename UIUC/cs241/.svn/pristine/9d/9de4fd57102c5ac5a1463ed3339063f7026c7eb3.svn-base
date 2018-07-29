from __future__ import division
from numpy import linalg as LA
import matplotlib.pyplot as plt
import math
import numpy as np

import time as time



def printMatrix(testMatrix):
		#for j, element in enumerate(testMatrix):
	for i in range (0,len(testMatrix)):			
		print '[%0.1f   %0.1f   %0.1f   %0.1f   %0.1f]' %(testMatrix[i][0], testMatrix[i][1], testMatrix[i][2], testMatrix[i][3], testMatrix[i][4])

	print ''

def backSub(testMatrix, n, mtb, z):
	ansVec = np.zeros(n)

	for i in range(0, n):
		ansVec[i] = testMatrix[i][n]

	for j in range(n-1, -1, -1):
		for i in range(n-1, j, -1):
			ansVec[j] -= testMatrix[j][i]*ansVec[i]
		ansVec[j] /= testMatrix[j][j]

	mtf = int(round(time.time()*1000000))
	m = mtf-mtb
	print 'Time taken my function: %d' %m

	#print ansVec		

	print 'It took my function %d times longer to solve! :(' %(m/z)
	
	return m, ansVec
	
		
def pleaseWork(n, tm, tt):
	A = np.identity(n) + 0.01 * np.random.rand(n,n)
	b = np.zeros(n)

	for i in range (0, n):
		for j in range (0, n):
			b[i] += A[i][j]

	ztb = int(round(time.time()*1000000))
	Z = np.linalg.solve(A,b)
	ztf = int(round(time.time()*1000000))
	z = ztf-ztb
	#print 'Time taken standard function: %d' %z
	

	A = np.append(A,np.zeros([len(A), 1]), 1)
	for i in range (0, n):
		A[i][n] = b[i]

	mtb = int(round(time.time()*1000000))

	for x in range(0, n - 1):
		for y in range(x + 1, n):
			scalar = -A[y][x]/A[x][x]

			for w in range(x, n+1):
				A[y][w] = A[y][w]+A[x][w]*scalar
		
	m, ansVec = backSub(A, n, mtb, z)

	if(n == 50):
		tt[0][0] = m
		tt[1][0] = z
		tt[2][0] = 50
		

	if(n == 100):
		tt[0][1] = m
		tt[1][1] = z
		tt[2][1] = 100

	if(n == 200):
		tt[0][2] = m
		tt[1][2] = z
		tt[2][2] = 200

	if(n == 400):
		tt[0][3] = m
		tt[1][3] = z
		tt[2][3] = 400

	retVal = np.zeros((3, n))
	for i in range (0, n):
		retVal[1][i] = ansVec[i]
		retVal[0][i] = Z[i]
		retVal[2][i] = b[i]

	return retVal



acRESnum = np.zeros((5,4))
tm = np.zeros((4,2))
tt = np.zeros((3,4))
ns = np.array([50, 100, 200, 400])
for q in range (0, 4):
	accMy = np.zeros((ns[q]))
	resMy = np.zeros((ns[q]))	
	resSys = np.zeros((ns[q]))
	accSys = np.zeros((ns[q]))

	retVal = pleaseWork(ns[q], tm, tt)

	for i in range(0, ns[q]):
		accMy[i] = math.fabs(retVal[1][i] - 1)/1
		resMy[i] = 1 - retVal[1][i]
		resSys[i] = 1 - retVal[1][i]
		accSys[i] = math.fabs(retVal[0][i] - 1)/1
	acRESnum[0][q] = q
	acRESnum[1][q] = LA.norm(accMy, 2)
	acRESnum[2][q] = LA.norm(resMy, 2)
	acRESnum[3][q] = LA.norm(resSys, 2)
	acRESnum[4][q] = LA.norm(accSys, 2)


#print(tm)
#print(tt)
plt.plot(acRESnum[0], acRESnum[1], acRESnum[0], acRESnum[2], acRESnum[0], acRESnum[3], acRESnum[0], acRESnum[4])
#plt.plot(tt[2], tt[0], tt[2], tt[1])
plt.show()


















