import Queue
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
content = ""
def list_cmp(node_a,node_b):
    if node_a.le != node_b.le:
        return node_a.le - node_b.le
    else:
        return node_b.id - node_a.id

def node_list_cmp(x,y):
    return x-y

def bfs(Tree_list):
    q_node = Queue.Queue()
    q_node.put(0)
    q_level = Queue.Queue()
    q_level.put(1)
    list_ = [0] *10000
    maxnn = 0
    while not q_node.empty():
        node_id = q_node.get()
        node_level = q_level.get()
        length_ = len(Tree_list[node_id].node_list)
        list_[node_level] += 1
        maxnn = max(list_[node_level],maxnn)
        for i in range(length_):
            q_node.put(Tree_list[node_id].node_list[i])
            q_level.put(node_level+1)

    return maxnn
	

def run(_id,Tree_list):
    global numOfNodes
    numOfNodes += 1
    #print(_id,len(Tree_list[_id].node_list))
    Tree_list[_id].node_list.sort(cmp = node_list_cmp)
    length_ = len(Tree_list[_id].node_list)
    for i in range(length_):
        run(Tree_list[_id].node_list[i],Tree_list)

# def count_for_nest(_id,Tree_list,list_for_nest):
#     length_ = len(Tree_list[_id].node_list)
#     maxx = 0
#     for i in range(length_):
#         count_for_nest(Tree_list[_id].node_list[i],Tree_list,list_for_nest)
#         maxx = max(maxx,list_for_nest[Tree_list[_id].node_list[i]])
#     str_type_ = Tree_list[_id].type_
#     if str_type_ == 'ForStatement' or str_type_ == 'ForInStatement' or str_type_ == 'ForOfStatement' or str_type_ == 'WhileStatement' or str_type_ == 'DoStatement' or str_type_ == 'IfStatement' or str_type_ == 'SwitchStatement':
#         list_for_nest[_id] = maxx+1
#         print(_id,list_for_nest[_id])
#     else:
#         list_for_nest[_id] = maxx



def count_for_nest(_id,Tree_list,list_for_nest,list_number,set_a):
    length_ = len(Tree_list[_id].node_list)
    maxx = 0
    global z
    str_type_ = Tree_list[_id].type_
    set_a.add(str_type_)
    if str_type_ == 'ForStatement' or str_type_ == 'ForInStatement' or str_type_ == 'ForOfStatement' or str_type_ == 'WhileStatement' or str_type_ == 'DoStatement' or str_type_ == 'IfStatement' or str_type_ == 'SwitchStatement':
        z += 1
    global numOfNodes
    numOfNodes+=1

    for i in range(length_):
        count_for_nest(Tree_list[_id].node_list[i],Tree_list,list_for_nest,list_number,set_a)
        maxx = max(maxx,list_for_nest[Tree_list[_id].node_list[i]])
    if str_type_ == 'ForStatement' or str_type_ == 'ForInStatement' or str_type_ == 'ForOfStatement' or str_type_ == 'WhileStatement' or str_type_ == 'DoStatement' or str_type_ == 'IfStatement' or str_type_ == 'SwitchStatement':
        list_for_nest[_id] = maxx+1
        if z == 1:
            list_number.append(list_for_nest[_id])
        z -= 1
        #print(_id,list_for_nest[_id])
    else:
        list_for_nest[_id] = maxx

dirPath = "/home/doubi/opo_js/target_5/"
m = 0
files = os.listdir(dirPath)

for file_name in files:
    file=open(dirPath+file_name)
    print(file_name)
    text=file.read()
    list_x = text.split('\n')

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
                # print(list[i].id,list[j].id)
                break
        #print("sdsdsdsdsdsdsdsdsd")
    global numOfNodes
    numOfNodes = 0
    h = 0
    global z
    z = 0
    set_a = set()
    #run(0,Tree_list)
    list_for_nest = [0 for x in range(0,len(Tree_list))]
    list_number = []
    count_for_nest(0,Tree_list,list_for_nest,list_number,set_a)
    maxWidth = bfs(Tree_list)
    #print(len(set_a))
    avDepthOfNest = 0
    maxDepthOfNest = 0
    for il in list_number:
        maxDepthOfNest  = max(maxDepthOfNest,il)
        avDepthOfNest += il

    print(numOfNodes)
    print(len(set_a))
    print(maxDepthOfNest)
    print(avDepthOfNest)
    print(maxWidth)
    print(len(list_number))
    content += file_name + ','+ str(numOfNodes) + ',' + str(len(set_a)) + ',' + str(maxDepthOfNest) + ',' + str(avDepthOfNest)+','+str(maxWidth)+','+str(len(list_number))+'\n'
	
    print("-----------------------------------------------------------------------------------------------------")

f = open('/home/doubi/opo_js/target_2/vector_2', 'w')
f.write(content)
f.close()
