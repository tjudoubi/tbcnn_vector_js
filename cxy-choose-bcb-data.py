from itertools import combinations
import os
import random

# 选取BCB的数据，并且按照一定比例划分train、dev、test。
# 注意选取的时候要从筛选过的正确的BCB里选取（也就是经过排除非函数部分的路径）

def chooes_clone_data():
    random.seed(666)
    write_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-6-path.txt','w')
    read_file = open('/home/chengxiaoyun/our-data-bcb/choose-bcb-data/bcb-6-path.txt','r')
    count = 0
    all_data = read_file.readlines()
    data_choose =  random.sample(all_data,100)
    for i in range(len(data_choose)):
       write_file.writelines(data_choose[i])
       count = count + 1
    print("count:   ",count)


def chooes_noclone_data():
    random.seed(666)
    write_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-0-path.txt','w')
    read_file = open('/home/chengxiaoyun/our-data-bcb/choose-bcb-data/bcb-noclone-path-new.txt', 'r')
    count = 0
    all_data = read_file.readlines()
    data_choose =  random.sample(all_data, 100)
    for i in range(len(data_choose)):
       print("i:  ",i)
       write_file.writelines(data_choose[i])
       count = count + 1
    print("count:   ",count)

def split_train_dev_test_path():
    noclone_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-0-path.txt', 'r')
    clone1_file = open( '/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-1-path.txt', 'r')
    clone2_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-2-path.txt', 'r')
    clone3_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-3-path.txt', 'r')
    clone4_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-4-path.txt', 'r')
    clone5_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-5-path.txt', 'r')
    clone6_file = open('/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/bcb-6-path.txt', 'r')
    train_path = open("/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/train_path.txt", 'a+')
    test_path = open("/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/test_path.txt", 'a+')
    dev_path = open("/home/chengxiaoyun/our-data-bcb/08-05-cxy-test/dev_path.txt", 'a+')

    noclone_lines = noclone_file.readlines()
    trainlist = []
    testlist = []
    devlist = []
    trainlist, testlist, devlist = split(noclone_lines, 0)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []

    clone1_lines = clone1_file.readlines()
    clone2_lines = clone2_file.readlines()
    clone3_lines = clone3_file.readlines()
    clone4_lines = clone4_file.readlines()
    clone5_lines = clone5_file.readlines()
    clone6_lines = clone6_file.readlines()
    trainlist = []
    testlist = []
    devlist = []
    trainlist,testlist,devlist = split(clone1_lines,1)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []
    trainlist, testlist, devlist = split(clone2_lines, 2)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []
    trainlist, testlist, devlist = split(clone3_lines, 3)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []
    trainlist, testlist, devlist = split(clone4_lines, 4)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []
    trainlist, testlist, devlist = split(clone5_lines, 5)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []
    trainlist, testlist, devlist = split(clone6_lines, 6)
    for i in range(len(trainlist)):
        train_path.writelines(trainlist[i])
    trainlist = []
    for i in range(len(testlist)):
        test_path.writelines(testlist[i])
    testlist = []
    for i in range(len(devlist)):
        dev_path.writelines(devlist[i])
    devlist = []


# 3:1:1
# 如果不是3：1：1就修改 length//5 后面的被除数
def split(full_list,tag):
    length = full_list.__len__()
    trainlist = []
    testlist = []
    devlist = []
    div = length//5
    train_count = div*3
    test_count = div
    dev_count = div
    total = train_count + test_count + dev_count
    if(total!=length):
        test_count = test_count + (length-total)
    middle_count = train_count + test_count
    for i in range(len(full_list)):
        line_info = full_list[i].split(';')
        function_catalogue = line_info[0]
        tag = line_info[3]
        function1_path = line_info[1]
        cut1_path = function1_path.split(',')
        function1_sub_catalogue = cut1_path[0]
        function1_name = cut1_path[1]
        function1_start_line = int(cut1_path[2])
        function1_end_line = int(cut1_path[3])
        real1_path = '/home/chengxiaoyun/bcb_reduced/' + function_catalogue + '/' + function1_sub_catalogue + '/' + function1_name
        function2_path = line_info[2]
        cut2_path = function2_path.split(',')
        function2_sub_catalogue = cut2_path[0]
        function2_name = cut2_path[1]
        function2_start_line = int(cut2_path[2])
        function2_end_line = int(cut2_path[3])
        real2_path = '/home/chengxiaoyun/bcb_reduced/' + function_catalogue + '/' + function2_sub_catalogue + '/' + function2_name
        cur_clone_text = real1_path + '\t' + real2_path + '\t' + str(tag)
        if i < train_count:
            trainlist.append(cur_clone_text)
        if train_count <= i < middle_count:
            testlist.append(cur_clone_text)
        if middle_count <= i <= length:
            devlist.append(cur_clone_text)
    print('train_count ', train_count, ' dev_count ', dev_count, ' test_count', test_count)
    return trainlist,testlist,devlist

# def test():
#     length = 20000
#     div = length//5
#     train_count = div*3
#     test_count = div
#     dev_count = div
#     total = train_count + test_count + dev_count
#     print("total ",total)
#     if(total!=length):
#         test_count = test_count + (length-total)
#     middle_count = train_count + test_count
#     print('train_count ', train_count, ' dev_count ', dev_count, ' test_count', test_count)

def main():
    # test()
    #  chooes_clone_data()
    # chooes_noclone_data()
    split_train_dev_test_path()

if __name__ == '__main__':
    main()