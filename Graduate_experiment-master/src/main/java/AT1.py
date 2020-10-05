import os

class Node:
    def __init__(self, lines,functions, branches):
        self.lines = lines
        self.functions = functions
        self.branches = branches

content = ""
dirPath = "J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_lkl\\"
files = os.listdir(dirPath)
for file_name in files:
    file_path = dirPath + file_name
    file = open(file_path)
    content += file_name[:-4] + ","
    for line in file:
        if "lines......:" in line:
            a = line.find(":")
            b = line.find("%")
            content += line[a+1:b] + ","
        if "functions..:" in line:
            a = line.find(":")
            b = line.find("%")
            content += line[a + 1:b] + ","
        if "branches...:" in line:
            a = line.find(":")
            b = line.find("%")
            content += line[a + 1:b] + '\n'
    file.close()

f = open('J:\\deep learning\\untitled2\\Graduate_experiment-master\\Graduate_experiment-master\\opo_js\\target_2\\coverage', 'w')
f.write(content)
f.close()
