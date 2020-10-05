import os
import random
listfile=os.listdir("D:/datasetforTBCCD-master/jsc-common")
# ff=open("D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/traindata.txt",'w')
i = 0
list_str_w = []
for lis in listfile:
    list_str_w.append("D:/datasetforTBCCD-master/jsc-CVE/"+lis+'\t'+'0'+'\n')
    # ff.write("D:/datasetforTBCCD-master/jsc-CVE/"+lis+'\t'+'0'+'\n')
    # lisfinalfile.append("D:/datasetforTBCCD-master/jsc-CVE/"+lis)
    i += 1

listfile=os.listdir("D:/datasetforTBCCD-master/jsc-a")
for lis in listfile:
    list_str_w.append("D:/datasetforTBCCD-master/jsc-CVE/"+lis+'\t'+'1'+'\n')
    # ff.write("D:/datasetforTBCCD-master/jsc-CVE/"+lis+'\t'+'1'+'\n')
    # lisfinalfile.append("D:/datasetforTBCCD-master/jsc-CVE/"+lis)
    i += 1
# ff.close()
random.shuffle(list_str_w)
f = open("D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/traindata.txt",'w')
for str in list_str_w:
    f.write(str)
f.close()

