#coding=utf-8
 
'''
Created on 2019-2-14
@author: Zyx
'''
 
import os
import time
dirPath = "/home/doubi/Superion/stress"
files = os.listdir(dirPath)
i = 1
for file in files:
   os.system("rm -rf ~/jjk/")
   os.system("mkdir jjk")
   time.sleep(2)
   os.system("python dd.py")
   time.sleep(2)
   filePath = os.path.join(dirPath, file)
   comandA_1 = "/home/doubi/WebKit/WebKitBuild/Release/bin/jsc"
   os.system(comandA_1+" "+filePath+" >> log_a.log")
   comandB = "python copy.py"
   os.system(comandB)
   time.sleep(2)
   os.system("lcov -c -o /home/doubi/jjk/test.info -d jjk --rc lcov_branch_coverage=1")
   time.sleep(2)
   os.system("genhtml -o /home/doubi/jjk/results /home/doubi/jjk/test.info --rc genhtml_branch_coverage=1 >> /home/doubi/opo_lkl/"+file+".log")
   sstr = "/home/doubi/Superion/stress/"+file
   time.sleep(2)
   #os.system("rm -f "+sstr)
   print("rm -f "+sstr)
   i+=1


