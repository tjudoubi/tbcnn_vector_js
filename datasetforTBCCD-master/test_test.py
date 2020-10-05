import os
import tensorflow as tf
import numpy as np
import network_test2
import javalang
from sampleJava import _traverse_treewithid
from sampleJava import getData_finetune
from parameters import EPOCHS, LEARN_RATE
from sklearn.metrics import precision_score, recall_score, f1_score

sess = tf.Session()
saver = tf.train.import_meta_graph("./model_o/model.ckpt.meta")

# all_vars = tf.trainable_variables()
# for v in all_vars:
#     print(v.name)
#     #print v.name,v.eval(self.sess) # v 都还未初始化，不能求值
# # 加载模型 参数变量 的 值
saver.restore(sess,tf.train.latest_checkpoint("./model_o"))
print("ModelV restored.")
# all_vars = tf.trainable_variables()
# list_v = []
# for v in all_vars:
#     list_v.append([v.name,v.eval(session=sess)])
#     print(v.name,v.eval(session=sess))



graph = tf.get_default_graph()
tensor_name_list = [tensor.name for tensor in graph.as_graph_def().node]
embedding = graph.get_tensor_by_name("Variable:0")
print(embedding.eval(session=sess))
nodes_node1 = graph.get_tensor_by_name("inputs/tree:0")
children_node1 = graph.get_tensor_by_name("inputs/children:0")
nodes_node2 = graph.get_tensor_by_name("inputs/tree_1:0")
children_node2 = graph.get_tensor_by_name("inputs/children_1:0")
y1 = graph.get_tensor_by_name("network/conv_layer/concat:0")
y2 = graph.get_tensor_by_name("network/conv_layer/concat_1:0")
y = (y1,y2)
print((y1,y2))



def train_model(infile, embeddings):
    global threaholder
    threaholder = 0
    os.environ["CUDA_VISIBLE_DEVICES"] = "0"
    num_feats = 100
    dictt = {}
    listrec = []
    f = open("flistBCB.txt", 'r')
    line = f.readline().rstrip('\t')
    l = line.split('\t')
    z = 0
    for ll in l:
        if not os.path.exists(ll):
            listrec.append(ll)
            continue
        faa = open(ll, 'r', encoding="utf-8")
        fff = faa.read()
        tree = javalang.parse.parse_member_signature(fff)
        sample, size = _traverse_treewithid(tree)
        if size > 3000 or size < 10:
            z += 1
            listrec.append(ll)
            continue
        dictt[ll] = sample
        # print(sample)
    f.close()
    print("wuxiaogeshu:", z)

    for epoch in range(1, EPOCHS + 1):
        f = open("./datasetForVariantsTBCCD/BCB/devdata.txt", 'r')
        line = "123"
        k = 0
        while line:
            line = f.readline().rstrip('\n')
            l = line.split('\t')

            if len(l) != 3:
                break
            if (l[0] in listrec) or (l[1] in listrec):
                continue
            k += 1
            nodes11,children1,nodes22,children2,batch_labels=getData_finetune(l,dictt,embeddings)
            nodes_node1 = graph.get_tensor_by_name("inputs/tree:0")
            children_node1 = graph.get_tensor_by_name("inputs/children:0")
            # nodes_node2 = graph.get_tensor_by_name("inputs/tree_1:0")
            # children_node2 = graph.get_tensor_by_name("inputs/children_1:0")
            y1 = graph.get_tensor_by_name("network/conv_layer/concat:0")
            # y2 = graph.get_tensor_by_name("network/conv_layer/concat_1:0")
            # y = (y1, y2)
            # print(batch_labels)
            print("go")
            conv1 = sess.run(
                [y1],
                feed_dict={
                    nodes_node1: nodes11,
                    children_node1: children1,
                    # nodes_node2: nodes22,
                    # children_node2: children2,
                }
            )
            print(conv1)
            conv1 = sess.run(
                [y1],
                feed_dict={
                    nodes_node1: nodes22,
                    children_node1: children2,
                    # nodes_node2: nodes22,
                    # children_node2: children2,
                }
            )
            print(conv1)
            # print("v_1.shape:",v_1.shape,",v_2.shape:",v_2.shape)



if __name__ == '__main__':
    dictt = {}
    dictta = {}
    listta = list()
    def _onehot(i, total):
        return [1.0 if j == i else 0.0 for j in range(total)]
    feature_size = 100
    fz = open("sentenceBCBwithid.txt", 'r')
    line = "123"
    listchar = []
    while line:
        line = fz.readline().rstrip("\n")
        l = line.split(" ")
        listchar.extend(list(set(l)))
        listchar = list(set(listchar))
    fz.close()
    for l in listchar:
        listta.append(np.random.normal(0, 0.1, 100).astype(np.float32))
    embeddingg = np.asarray(listta)
    embeddingg = tf.Variable(embeddingg)
    for i in range(len(listchar)):
        dictta[listchar[i]] = i
    # print(dictta)
    train_model('./datasetForVariantsTBCCD/BCB/traindata.txt', dictta)