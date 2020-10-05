import numpy as np
import pandas as pd
import scipy.stats as stats
from scipy.stats import kstest
import seaborn as sns

from statsmodels.sandbox.stats.multicomp import multipletests
import matplotlib.pyplot as plt

dict2 = {
'a':'NOIf',
'b':'NOS',
'c':'NOV',
'd':'MDOA',
'e':'NOL',
'f':'CC',
'g':'NOD',
'h':'HD',
'i':'HV',
'j':'HE',
'k':'NM',
'l':'NOE',
'm':'NOC',
'n':'NOIter',
'o':'NONo',
'p':'NONK',
'q':'MDON',
'r':'SODON',
's':'MW',
't':'NONe',
'u':'LC',
'v':'FC',
'w':'BC'
}

# dict2 = {
# 'a':'numOfIf',
# 'b':'numOfStatement',
# 'c':'numOfvariableDeclaration',
# 'd':'maxDepthOfAST',
# 'e':'numOfLoc',
# 'f':'Cyclomatic',
# 'g':'numOfDepend',
# 'h':'Halstead_difficulty',
# 'i':'Halstead_volume',
# 'j':'Halstead_effort',
# 'k':'numOfMethod',
# 'l':'numOfExpression',
# 'm':'numOfClass',
# 'n':'numOfIteration',
# 'o':'numOfNodes',
# 'p':'numOfNodeKinds',
# 'q':'maxDepthOfNest',
# 'r':'sumOfDepthOfNest',
# 's':'maxWidth',
# 't':'numOfNest',
# 'u':'line coverage',
# 'v':'function coverage',
# 'w':'branch coverage'
# }

list = []
f = open("J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\test_data")
test_data = f.read()
sstr_list = test_data.split('\n')

int_dex = 0
for i in range(len(sstr_list)-1,-1,-1):
    if sstr_list[i] != "":
        elements = sstr_list[i].split(',')
        if int(elements[13]) != 0:
            del sstr_list[i]



dict1 = {}
for i in range(0,23):
    dict1[dict2[chr(ord('a')+i)]] = []
iiii = 0
for sstr in sstr_list:
    iiii+=1
    if sstr != "":
        elements = sstr.split(',')
        print(iiii,elements[13])
        i = 0
        for element in elements:
            dict1[dict2[chr(ord('a') + i)]].append(float(element))
            i+=1

for key in dict1.keys():
    dict1[key] = np.array(dict1[key])

print("ssssssssssssssssssssssssssssssssss")
data = pd.DataFrame(dict1)

l1 = data.corr('kendall')
l2 = data.corr('spearman')
l3 = data.corr('pearson')


l1.to_csv('kendall_1_2.csv',encoding='gbk')
l2.to_csv('spearman_1_2.csv',encoding='gbk')

fig = plt.figure()
ax = fig.add_subplot(figsize=(20,20)) #图片大小为20*20
# ax = sns.heatmap(l2[['line coverage','function coverage','branch coverage']], linewidths=0.05,vmax=1, vmin=0 ,annot=True,annot_kws={'size':15,'weight':'bold'})
# #热力图参数设置（相关系数矩阵，颜色，每个值间隔等）
# #ticks = numpy.arange(0,16,1) #生成0-16，步长为1
# plt.xticks(np.arange(3)+0.5,fontsize=16) #横坐标标注点
# plt.yticks(np.arange(23)+0.5,fontsize=16) #纵坐标标注点

ax = sns.heatmap(l2, linewidths=0.05,vmax=1, vmin=0 ,annot=True,annot_kws={'weight':'bold'})
#热力图参数设置（相关系数矩阵，颜色，每个值间隔等）
#ticks = numpy.arange(0,16,1) #生成0-16，步长为1
plt.xticks(np.arange(23)+0.5,fontsize=16) #横坐标标注点
plt.yticks(np.arange(23)+0.5,fontsize=16) #纵坐标标注点

#ax.set_xticks(ticks) #生成刻度
#ax.set_yticks(ticks)
#ax.set_xticklabels(names) #生成x轴标签
#ax.set_yticklabels(names)
ax.set_title('spearman')#标题设置
plt.savefig('cluster3.tif',dpi=300)
plt.show()

# p1 = kstest(dict1['numOfExpression'], 'norm')
# p = stats.kendalltau(x=dict1['numOfExpression'],y=dict1['function coverage'])

p_list = []
pp_list = []
for element in dict1.keys():
    ll_li = []
    l_li = []
    # # ll_li.append(element)
    # ll_li.append(stats.kendalltau(x=dict1[element],y=dict1['function coverage'])[1])
    # ll_li.append(stats.kendalltau(x=dict1[element],y=dict1['line coverage'])[1])
    # ll_li.append(stats.kendalltau(x=dict1[element],y=dict1['branch coverage'])[1])
    #
    # l_li.append(element)
    # l_li.append(stats.kendalltau(x=dict1[element], y=dict1['function coverage'])[1])
    # l_li.append(stats.kendalltau(x=dict1[element], y=dict1['line coverage'])[1])
    # l_li.append(stats.kendalltau(x=dict1[element], y=dict1['branch coverage'])[1])

    ll_li.append(stats.kendalltau(x=dict1[element], y=dict1['FC'])[1])
    ll_li.append(stats.kendalltau(x=dict1[element], y=dict1['LC'])[1])
    ll_li.append(stats.kendalltau(x=dict1[element], y=dict1['BC'])[1])

    l_li.append(element)
    l_li.append(stats.kendalltau(x=dict1[element], y=dict1['FC'])[1])
    l_li.append(stats.kendalltau(x=dict1[element], y=dict1['LC'])[1])
    l_li.append(stats.kendalltau(x=dict1[element], y=dict1['BC'])[1])

    pp_list.append(ll_li)
    p_list.append(l_li)



pp_list_numpy = np.array(pp_list)
p_adjust_line = multipletests(pp_list_numpy[:,1], alpha=0.05, method='bonferroni', is_sorted=False, returnsorted=False)
p_adjust_fucntion = multipletests(pp_list_numpy[:,0], alpha=0.05, method='bonferroni', is_sorted=False, returnsorted=False)
p_adjust_branch = multipletests(pp_list_numpy[:,2], alpha=0.05, method='bonferroni', is_sorted=False, returnsorted=False)
print(p_adjust_branch[1])
array_a = np.vstack((p_adjust_line[1],p_adjust_fucntion[1],p_adjust_branch[1]) )
array_b = np.array((p_adjust_line[0],p_adjust_fucntion[0],p_adjust_branch[0]))

array_a = array_a.T.tolist()
array_b = array_b.T.tolist()

for i in range(len(array_a)):
    array_a[i].insert(0,dict2[chr(ord('a')+i)])

for i in range(len(array_b)):
    array_b[i].insert(0, dict2[chr(ord('a') + i)])

print(p_list)