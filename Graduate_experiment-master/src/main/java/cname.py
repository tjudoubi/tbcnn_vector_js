#coding=utf-8
 
'''
Created on 2019-2-14
@author: Zyx
'''
import os

i = 0

def copy_alter(filename):
	with open(filename,'r') as f:
    		content = f.read().splitlines()
	f.close();

	list =[]
	for temp in content:
   		if temp[0:2] != '//':
			list.append(temp+'\n')
	print(list)

	global i
	f=open(str(i)+'.js',"w")
	f.writelines(list)
	f.close()
	i+=1

if __name__ == '__main__' :
	files = os.listdir('./')
   	for temp in files :
		if temp[-3:]=='.js':
			copy_alter(temp)



