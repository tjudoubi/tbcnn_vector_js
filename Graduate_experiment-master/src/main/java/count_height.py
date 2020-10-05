#coding=utf-8
 
'''
Created on 2019-2-14
@author: Zyx
'''
 
#1.打开文件
file=open("ols")
#2.读取文件内容
text=file.read()
length = len(text)
print(text)

sum_ = 0
height = 0
i = 0 
ans = 0
while i<length:
 c = text[i]
 if c=='(':
    if text[i+1]!=' ' and text[i+1]!=')':
    	sum_ += 1
    elif text[i+1]==' ' or text[i+1] == ')':
	ans += 1
		
    height = max(height,sum_)

 elif c==')':
    if text[i-1] != ' ':
    	sum_ -= 1
    elif text[i-1] == ' ':
	ans += 1
 i+=1

print(height,ans)
#3.关闭文件
file.close()

