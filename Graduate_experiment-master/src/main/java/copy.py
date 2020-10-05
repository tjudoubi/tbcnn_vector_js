#coding=utf-8
 
'''
Created on 2019-2-14
@author: Zyx
'''
 
import os
import shutil

 
def suffix( file, *suffixName ) :  
    array = map( file.endswith, suffixName )  
    if True in array :  
        return True  
    else :  
        return False

 
def removeDir(dirPath):
    ##print(dirPath)
    if not os.path.isdir(dirPath):
 
        return
 
    files = os.listdir(dirPath)
 
    try:
 
        for file in files:
 
            filePath = os.path.join(dirPath, file)
 
            if os.path.isfile(filePath) and suffix( file, '.gcda' , '.gcno'):
		print(filePath+' '+dirPath)
 
                shutil.copy(filePath,"/home/doubi/jjk")
 
            elif os.path.isdir(filePath):
 
                removeDir(filePath)
 
    except Exception as e:
 
        print(e)

if __name__ == '__main__' :
    removeDir("/home/doubi/WebKit/WebKitBuild")
