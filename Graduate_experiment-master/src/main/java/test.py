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

import os

dirPath = "/home/doubi/KKID_LSK/target_5/"
list_ = []
files = os.listdir(dirPath)
file_name = "ai-create-this-to-new-object-fire.js.txt"
file=open(dirPath+file_name)
text=file.read()
list_x = text.split('\n')

def list_cmp(node_a,node_b):
    if node_a.le != node_b.le:
        return node_a.le - node_b.le
    else:
        return node_b.id - node_a.id

def node_list_cmp(x,y):
	return x-y

def run(_id,Tree_list):
	print(_id)
	Tree_list[_id].node_list.sort(cmp = node_list_cmp)
	length_ = len(Tree_list[_id].node_list)
	for i in range(length_):
		run(Tree_list[_id].node_list[i],Tree_list)
	


Tree_list = []
list = []
i = 0
length = len(list_x)
while i < length-1:
    temp_ = list_x[i].split(',')
    # print(temp_)
    type_ = temp_[0]
    L = int(temp_[1])
    R = int(temp_[2])
    id = int(temp_[3])
    le = int(temp_[4])
    node = Node(type_,L,R,id,le)
    list.append(node)
    node2 = Tree_node(type_,L,R,id,le)
    Tree_list.append(node2)
    i += 1
#print(len(list))
list.sort(cmp = list_cmp)
#print(list[len(list)-1].id)
for i in range(len(list)):
    if list[i].le == -1:
        continue;
    for j in range(i+1,len(list)):
        if list[i].le <= list[j].le and list[i].L >= list[j].L and list[i].R <= list[j].R:
            Tree_list[list[j].id].node_list.append(list[i].id)
            print(list[i].id,list[j].id)
            break
    #print("sdsdsdsdsdsdsdsdsd")

run(0,Tree_list)
