#coding=utf-8
import os
import time
import subprocess
'''
Created on 2019-2-14
@author: Zyx
'''

dirPath = "/home/doubi/opo_js/target_4/"
# python 使用类创建结构体
class Myclass(object):
	def __init__(self, numOfIteration,name, numOfIf, numOfStatement, numOfvariableDeclaration, maxDepthOfAST,numOfLoc,Cyclomatic,Dependency_count,Halstead_difficulty,Halstead_volume,Halstead_effort,numOfMethod,numOfSwitch,numOfExpression,numOfClass):
		self.name = name
		self.numOfIf = numOfIf
            	
	    	self.numOfStatement = numOfStatement
	    	self.numOfvariableDeclaration = numOfvariableDeclaration
	    	self.maxDepthOfAST = maxDepthOfAST
		self.numOfLoc = numOfLoc
		self.Cyclomatic = Cyclomatic
		self.numOfDepend = Dependency_count
		self.Halstead_difficulty = Halstead_difficulty
		self.Halstead_volume = Halstead_volume
		self.Halstead_effort = Halstead_effort
		self.numOfMethod = numOfMethod
		self.numOfSwitch = numOfSwitch
		self.numOfExpression = numOfExpression
		self.numOfClass= numOfClass
		self.numOfIteration = numOfIteration



class Node:
	def __init__(self,type_,L,R,id,le):
		self.type_ = type_
		self.L = L
		self.R = R
		self.id = id
		self.le = le

class Tree_node:
	def __init__(self,type_,L,R,id,le):
		self.node_list = []
		self.type_ = type_
		self.L = L
		self.R = R
		self.id = id
		self.le = le


def list_cmp(node_a,node_b):
	if node_a.le != node_b.le:
		return node_a.le - node_b.le
	else:
		return node_b.id - node_a.id

def node_list_cmp(x,y):
	return x-y

def run(_id,Tree_list,h):
	global m
	m = max(m,h)
	print(_id,len(Tree_list[_id].node_list))
	Tree_list[_id].node_list.sort(cmp = node_list_cmp)
	length_ = len(Tree_list[_id].node_list)
	for i in range(length_):
		run(Tree_list[_id].node_list[i],Tree_list,h+1)

def count_for_nest(_id,Tree_list,list_for_nest):
	length_ = len(Tree_list[_id].node_list)
	maxx = 0
	for i in range(length_):
		count_for_nest(Tree_list[_id].node_list[i],Tree_list,list_for_nest)
		maxx = max(maxx,list_for_nest[Tree_list[_id].node_list[i]])
	str_type_ = Tree_list[_id].type_
	if str_type_ == 'ForStatement' or str_type_ == 'ForInStatement' or str_type_ == 'ForOfStatement' or str_type_ == 'WhileStatement' or str_type_ == 'DoStatement' or str_type_ == 'IfStatement' or str_type_ == 'SwitchStatement':
		list_for_nest[_id] = maxx+1
		print(_id,list_for_nest[_id])
	else:
		list_for_nest[_id] = maxx



def extract(str_out):
	str2 = "Function:"
	length = len(str_out)
	numOfMethod = (length - len(str_out.replace(str2,""))) / len(str2)

	list_out = str_out.split('\n')

	numOfLoc = list_out[12].split(':')[1]
	#print(str_out)
	#print(int(numOfLoc))

	Cyclomatic = 0
	Dependency_count = 0
	Halstead_difficulty = 0
	Halstead_volume = 0
	Halstead_effort = 0

	i = 12
	while i < len(list_out):
		ss = list_out[i]
		if ss.find('Cyclomatic complexity:',0,len(ss))!=-1:
			Cyclomatic+=int(ss.split(':')[1])
		elif ss.find('Dependency count:',0,len(ss))!=-1:
			Dependency_count += int(ss.split(':')[1])
		elif ss.find('Halstead difficulty:',0,len(ss))!=-1:
			Halstead_difficulty += float(ss.split(':')[1])
		elif ss.find('Halstead volume:',0,len(ss))!=-1:
			Halstead_volume += float(ss.split(':')[1])
		elif ss.find('Halstead effort:',0,len(ss))!=-1:
			Halstead_effort += float(ss.split(':')[1])
		i+=1

	return numOfLoc,Cyclomatic,Dependency_count,Halstead_difficulty,Halstead_volume,Halstead_effort,numOfMethod

list_ = []
files = os.listdir(dirPath)


for file_name in files:
	#1.打开文件
	file=open(dirPath+file_name)
	#2.读取文件内容
	text=file.read()
	length = len(text)
	#print(text)

	str2 = "ifStatement "
	numOfIf = (length - len(text.replace(str2,""))) / len(str2)

#	str2 = "for "
#	numOfFor = (length - len(text.replace(str2,""))) / len(str2)
#
#	str2 = "while "
#	numOfWhile = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "(statement "
	numOfStatement = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "variableDeclaration "
	numOfvariableDeclaration = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "(switchStatement "
	numOfSwitch = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "(iterationStatement "
	numOfIteration = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "(expressionStatement "
	numOfExpression = (length - len(text.replace(str2,""))) / len(str2)

	str2 = "(classDeclaration "
	numOfClass = (length - len(text.replace(str2,""))) / len(str2)
	##########################
	######获取深度
	#########################
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

	#print(height,ans)
	maxDepthOfAST = height

	#test1 = myclass.make_struct(numOfIf, numOfWhile, numOfFor, numOfStatement,numOfvariableDeclaration,maxDepthOfAST)
	##################################################################################################################
	#################################################################################################################3
	file_name_length = len(file_name)
	command = "cr /home/doubi/opo_js/"+file_name[0:file_name_length-4]
	print(command)
	back = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE,stderr=subprocess.PIPE).communicate()
	#time.sleep(2)
	str_ll = str(back[0].decode())
	if "Fatal error [getReports]" in str_ll:
		#print("Fatal error [getReports]")
		numOfLoc,Cyclomatic,Dependency_count,Halstead_difficulty,Halstead_volume,Halstead_effort,numOfMethhod = -1,-1,-1,-1,-1,-1,-1
	else:
		numOfLoc,Cyclomatic,Dependency_count,Halstead_difficulty,Halstead_volume,Halstead_effort,numOfMethhod = extract(str_ll)

	test1 = Myclass(numOfIteration,file_name,numOfIf, numOfStatement,numOfvariableDeclaration,maxDepthOfAST,numOfLoc,Cyclomatic,Dependency_count,Halstead_difficulty,Halstead_volume,Halstead_effort,numOfMethhod,numOfSwitch,numOfExpression,numOfClass)
	list_.append(test1)
	#print("back0----", str_ll) # 注意需要进行解码操作，默认输出的是字节
	#print("back1----", back[1].decode())  # back是一个元祖，可以通过元祖取值的方式获取结果

	#3.关闭文件
	file.close()
	#print('\n')
##############################################################################################################
content = ""
for temp in list_:
	content += str(temp.name)+','+str(temp.numOfIf+temp.numOfSwitch+temp.numOfIteration)+','+str(temp.numOfStatement)+','+str(temp.numOfvariableDeclaration)+','+str(temp.maxDepthOfAST)+','+str(temp.numOfLoc)+','+str(temp.Cyclomatic)+','+str(temp.numOfDepend)+','+str(temp.Halstead_difficulty)+','+str(temp.Halstead_volume)+','+str(temp.Halstead_effort)+','+str(temp.numOfMethod)+','+str(temp.numOfExpression)+','+str(temp.numOfClass)+','+str(temp.numOfIteration)+'\n'

f = open('/home/doubi/opo_js/target_2/vector_1', 'w') #清空文件内容再写
f.write(content)
f.close()

