import matplotlib.pyplot as plt
x=[i for i in range(1500)]
y=[]
# f = open("D:/datasetforTBCCD-master/js-vuln-db-master/3.txt")
line = "123"
f = open("D:\\datasetforTBCCD-master\\inform-10 100 0.0001\\dev_acc.txt")
while line:
    line = f.readline().rstrip("\n")
    l = line.split(";")
    for eke in l:
        if eke != '':
            y.append(float(eke))
f.close()

plt.subplot(311)
plt.plot(x,y)
plt.xlabel("step")
plt.ylabel("dev_accuracy")

y.clear()
f = open("D:\\datasetforTBCCD-master\\inform-10 100 0.0001\\dev_loss.txt")
line = "123"
while line:
    line = f.readline().rstrip("\n")
    l = line.split(";")
    for eke in l:
        if eke != '':
            y.append(float(eke))


plt.subplot(312)
plt.plot(x,y)
plt.xlabel("step")
plt.ylabel("dev_loss")



y.clear()
f = open("D:\\datasetforTBCCD-master\\inform-10 100 0.0001\\test_acc.txt")
line = "123"
while line:
    line = f.readline().rstrip("\n")
    l = line.split(";")
    for eke in l:
        if eke != '':
            y.append(float(eke))


plt.subplot(313)
plt.plot(x,y)
plt.xlabel("step")
plt.ylabel("test_acc")


# y.clear()
# f = open("D:\\datasetforTBCCD-master\\inform-9 200 0.0001\\test_loss.txt")
# line = "123"
# while line:
#     line = f.readline().rstrip("\n")
#     l = line.split(";")
#     for eke in l:
#         if eke != '':
#             y.append(float(eke))
#
#
# plt.subplot(414)
# plt.plot(x,y)
# plt.xlabel("step")
# plt.ylabel("dev_loss")


plt.show()