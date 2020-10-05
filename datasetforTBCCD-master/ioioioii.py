# # import math
# # import tensorflow as tf
# # import numpy as np
# #
# # # # sess=tf.InteractiveSession()
# # # # v = tf.placeholder(tf.float32, (1,))
# # # # x = tf.constant([[[1,2],[2,4]],[[4,6],[6,8],[[22,23],[21,22]]]])
# # # # y = tf.constant([[[0,3],[3,5]],[[5,7],[7,9],[[32,33],[31,32]]]])
# # # # print(sess.run(x),x.shape)
# # # # print(sess.run(y),y.shape)
# # # # print(sess.run(tf.matmul(x,y,transpose_a=True)))
# # # # tree_tensor = tf.concat([x, y], axis=2, name='trees')
# # # # print(sess.run(tree_tensor))
# # #
# # #
# # # # s = tf.constant([1, 1, 2, 3, 4, 5, 10], dtype=tf.float32)
# # # #
# # # # sm = tf.nn.softmax(s)
# # # #
# # # # with tf.Session()as sess:
# # # #
# # # #     print(sess.run(sm))
# # # #
# # # #     print(sess.run(tf.argmax(sm)))
# # # #     print("xxxxxxxxxx")
# # #
# # # logits = np.array([0.0192789,0.02308862,0.02104643,0.02001139,0.03097417,0.02131467, 0.02253243,0.0130016,0.02337862,0.02518789,0.02378228,0.02346203,0.01319415,0.02509902 ,0.02585681 ,0.0151667 , 0.01290364 ,0.01640837,
# # #  0.01530177,0.02399971,0.02773384,0.01569085,0.01577388,0.01455136,0.01838264,0.02397613,0.01490612,0.02844491,0.0183582,0.01444157,0.02063536, 0.02812582, 0.02788781, 0.02310451 ,0.01718686 ,0.01477823,0.01186407,0.02226846, 0.00999519, 0.02087843, 0.02301938, 0.02730495,
# # #  0.0130666 , 0.01367978 ,0.01846266 ,0.02280775, 0.01662765 ,0.02389902,0.01869913, 0.01845956])
# # # label = np.array([0 for i in range(50)])
# # # label[1] = 1
# # # sess = tf.Session()
# # # print(sess.run(tf.nn.softmax_cross_entropy_with_logits(logits=logits,labels=label)))
# # # array = np.array([[1,2],[2]])
# # # ndarray=np.pad(array)
# # # print(ndarray)
# #
# # a = tf.placeholder(tf.int32, shape=(None, ), name='tree')
# # b = tf.placeholder(tf.int32, shape=(None, ), name='tree')
# # c = tf.add(a, b)
# # with tf.Session() as sess:
# #     print (sess.run(c, feed_dict = {a: 1, b: [2.0,2.0]}))
# #
# # # # c = np.random.random([10, 1])  # 随机生成一个10*1的数组
# # # # b = tf.nn.embedding_lookup(c, [1, 3])#查找数组中的序号为1和3的
# # # p = tf.Variable([[1,0,1],[0,1,0],[1,1,0]])  # 生成10*1的张量
# # # b = tf.nn.embedding_lookup(p, [[0, 2],[1,2]])  # 查找张量中的序号为1和3的
# # # labels = tf.placeholder(tf.int32,[None,])
# # #
# # # with tf.Session() as sess:
# # #     sess.run(tf.global_variables_initializer())
# # #     # m = sess.run(b)
# # #     # print(m)
# # #     # # print(c)
# # #     # print(sess.run(p))
# # #     # print(p)
# # #     # print(type(p))
# # #     m = sess.run(labels,feed_dict=)
#
# import os
# import tensorflow as tf
# import numpy as np
# import network4
# from sampleJS import get_tree
# from sampleJS import getData_finetune
# from parameters import EPOCHS, LEARN_RATE
# from sklearn.metrics import precision_score, recall_score, f1_score
#
# def train_model(infile, embeddings):
#     global threaholder
#     threaholder = 0
#     os.environ["CUDA_VISIBLE_DEVICES"] = "0"
#     num_feats = 100
#     nodes_node1, children_node1, res = network4.init_net_finetune(num_feats,embeddingg)
#     aaa = 1
#     aaaa = 1
#     # out_v = network3.out_layer(res)
#     b = tf.constant(value=1, dtype=tf.float32)
#     logits_evel = tf.multiply(res, b, name='logits_eval')
#     labels_node, loss_node, acc = network4.loss_layer(logits_evel)
#     optimizer = tf.train.GradientDescentOptimizer(LEARN_RATE)
#     train_step = optimizer.minimize(loss_node)
#     config = tf.ConfigProto()
#     config.gpu_options.allow_growth = True
#     sess = tf.Session(config=config)  # config=tf.ConfigProto(device_count={'GPU':0}))
#
#     saver = tf.train.Saver()
#     sess.run(tf.global_variables_initializer())
#
#     # labels_node, accuracy = network3.acc(res)
#
#     nodes = [[1,2],[2]]
#     childrenS = [[[1,2,3],[2,3,4]],[[1,2,3]]]
#     batch_q = [0,1]
#
#     _, err, accur, r = sess.run(
#         [train_step, loss_node, acc, res],
#         feed_dict={
#             nodes_node1: nodes,
#             children_node1: childrenS,
#             labels_node: batch_q,
#         }
#     )
#
#
#
#
#
#
#
# if __name__ == '__main__':
#     dictt = {}
#     dictta = {}
#     listta = list()
#     def _onehot(i, total):
#         return [1.0 if j == i else 0.0 for j in range(total)]
#     feature_size = 100
#     fz = open("new 1.txt", 'r')
#     line = "123"
#     listchar = []
#     while line:
#         line = fz.readline().rstrip("\n")
#         l = line.split(" ")
#         listchar.extend(list(set(l)))
#         listchar = list(set(listchar))
#         # print(1)
#     fz.close()
#     for l in listchar:
#         listta.append(np.random.normal(0, 0.1, 100).astype(np.float32))
#     embeddingg = np.asarray(listta)
#     embeddingg = tf.Variable(embeddingg)
#     for i in range(len(listchar)):
#         dictta[listchar[i]] = i
#     train_model('D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/traindata.txt', dictta)

ass = [[],[],[]]
zero_vector = [0]*5
children = [[n.append([0]* 5) for n in ass] for i in range(5)]
print(ass)