#coding=utf-8
 
'''
Created on 2019-2-14
@author: Zyx
'''
 
import os

 


dirPath = "/home/doubi/opo_lkl"
files = os.listdir(dirPath)
for file in files:
	sstr = "/home/doubi/Superion/stress/"+file
	command = "rm -f "+sstr[0:-4]
	print(command)
	os.system(command)
