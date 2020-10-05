dict = {}

f = open("J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\vector_1")
vector_1 = f.read()
f.close()

f = open("J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\vector_2")
vector_2 = f.read()
f.close()

f = open("J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\coverage")
coverage = f.read()
f.close()

v_1 = vector_1.split('\n')
v_2 = vector_2.split('\n')
cov = coverage.split('\n')

for line in v_1:
    if line != "":
        element = line.split(',')
        for i in range(len(element)):
            if i == 0:
                dict[element[0][0:-4]] = str()
            elif i!=0 and i!= len(element)-1:
                dict[element[0][0:-4]] += element[i] + ","
            elif i==len(element)-1:
                dict[element[0][0:-4]] += element[i] + ","


for line in v_2:
    if line != "":
        element = line.split(',')
        for i in range(len(element)):
            if i!=0 and i!= len(element)-1:
                dict[element[0][0:-4]] += element[i] + ","
            elif i==len(element)-1:
                dict[element[0][0:-4]] += element[i] + ","

for line in cov:
    if line != "":
        element = line.split(',')
        for i in range(len(element)):
            if element[0] not in dict.keys():
                continue
            if i!=0 and i!= len(element)-1:
                dict[element[0]] += element[i] + ","
            elif i==len(element)-1:
                dict[element[0]] += element[i] + '\n'

content = ""
sstr = ""
i = 0
for key in dict.keys():
    i += 1
    print(i,key,dict[key])
    content +=  key+","+dict[key]
    sstr += dict[key]
#print(content)
f = open('J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\d_data', 'w')
f.write(content)
f.close()

f = open('J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\test_data', 'w')
f.write(sstr)
f.close()