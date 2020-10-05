# delete all the comments and empty line of a C/C++ source file

import os, sys,string,re,glob

 

# /*..*/  //...

Rule1 = "(\/\*(\s|.)*?\*\/)|(\/\/.*)"

c1=re.compile(Rule1)

 

#-------------------------------------------------------------

def usage():

    print (u'''

    help: del_comment.py <filename | dirname>

    ''')

#--------------------------------------------------------------

def deal_file(src):

    # file exist or not

    if not os.path.exists(src):

        print ('Error: file - %s doesn\'t exist.'% src)

        return False

    if os.path.islink(src):

        print( 'Error: file - %s is a link.')

        return False

    filetype = (os.path.splitext(src))[1]

    # if not filetype in ['.js']:
    #
    #     return False

    try:

        if not os.access(src, os.W_OK):

            os.chmod(src, 0o664)

    except:

        print ('Error: you can not chang %s\'s mode.'% src)

 

    inputf = open(src, 'r')
    m = os.path.splitext(src)

    # outputfilename = (os.path.splitext(src))[0] + '_no_comment'+filetype
    outputfilename = os.path.splitext(src)[0].split('/')[1]

    # outputf = open(outputfilename, 'w')
    outputf = open('D:/sd/' + outputfilename + filetype, 'w')
 

    lines=inputf.read()

    inputf.close()

    lines=re.sub(Rule1,"",lines)

    outputf.write(lines)    

    outputf.close()

    return True

 

#--------------------------------------------------------------

def deal_dir(src):

    #  dir exist or not

    if not os.path.exists(src):

        print ('Error: dir - %s is not exist.'%src)

        return False

    filelists = os.listdir(src)

    for eachfile in filelists:

        eachfile = src + '/' +eachfile

        if os.path.isdir(eachfile):

            deal_dir(eachfile)

        elif os.path.isfile(eachfile):

            deal_file(eachfile)

    return True

 

#--------------------------------------------------------------

def main():

   
    src = "D:/datasetforTBCCD-master/jsc-a"

    # get absolute dir/file path

    if os.path.isdir(src):

        dire = os.path.abspath(src)

        dirFlag = True

    elif os.path.isfile(src):

        fl = os.path.abspath(src)

        dirFlag = False

    else:

        print ('File input error')

 

    # deal

    if dirFlag:

        deal_dir(dire)

    else:

        deal_file(fl)

    print ('Successful handle file.')

 

#--------------------------------------------------------------

if __name__ == '__main__':

    main()
