import os
import tensorflow as tf
import numpy as np
import network4
from sampleJS import get_tree
from sampleJS import _pad_nobatch
from sampleJS import _pad_vectors
from sampleJS import getData_finetune
from sampleJS import pad_batch_nodes
from parameters import EPOCHS, LEARN_RATE,BATCH_SIZE
from sklearn.metrics import precision_score, recall_score, f1_score

def train_model(infile, embeddings):
    global threaholder
    threaholder = 0
    os.environ["CUDA_VISIBLE_DEVICES"] = "0"
    num_feats = 100
    nodes_node1, children_node1, res = network4.init_net_finetune(num_feats,embeddingg)
    aaa = 1
    aaaa = 1
    bbbb = 1
    min_val_loss = 100.0
    # out_v = network3.out_layer(res)
    b = tf.constant(value=1, dtype=tf.float32)
    logits_evel = tf.multiply(res, b, name='logits_eval')
    labels_node, loss_node, acc = network4.loss_layer(logits_evel)
    optimizer = tf.train.GradientDescentOptimizer(LEARN_RATE)
    train_step = optimizer.minimize(loss_node)
    config = tf.ConfigProto()
    config.gpu_options.allow_growth = True
    sess = tf.Session(config=config)  # config=tf.ConfigProto(device_count={'GPU':0}))

    saver = tf.train.Saver()
    sess.run(tf.global_variables_initializer())

    # labels_node, accuracy = network3.acc(res)


    dictt = {}
    listrec = []
    f = open("D:\\datasetforTBCCD-master\\test_getSentenceJS\\flist_JSC.txt", 'r')
    line = f.readline().rstrip('\t')
    l = line.split('\t')
    z = 0
    for ll in l:
        if not os.path.exists(ll):
            listrec.append(ll)
            continue
        faa = open(ll, 'r', encoding="utf-8")
        fff = faa.read()
        content = ll.split('/')
        sample, size = get_tree(content[len(content)-1])
        if size > 3000 or size < 10:
            z += 1
            listrec.append(ll)
            continue
        dictt[ll] = sample
        # print(sample)
    f.close()
    print("wuxiaogeshu:", z)
    max_ACC = 0.0
    ssss_loss = []
    ssss_acc = []
    for epoch in range(1, EPOCHS + 1):
        train_loss, train_acc, n_batch = 0, 0, 0
        f = open(infile, 'r')
        line = "123"
        k = 0
        batch_q = []
        nodes = []
        childrenS = []
        while line:
            line = f.readline().rstrip('\n')
            l = line.split('\t')
            # m = l[0] in listrec
            if len(l) != 2:
                break
            if l[0] in listrec:
                continue
            k += 1
            nodes11,children1,batch_labels=getData_finetune(l,dictt,embeddings)
            childrenS.append(children1[0])
            batch_s = int(batch_labels[0])
            # bb_labels = [batch_labels[0] for _ in range(50)]
            nodes.append(nodes11)
            batch_q.append(batch_s)
            if k%BATCH_SIZE == 0:
                childrenS = _pad_nobatch(childrenS)
                childrenS = _pad_vectors(childrenS)
                nodes = pad_batch_nodes(nodes)
                _, err, accur, r = sess.run(
                    [train_step, loss_node, acc, res],
                    feed_dict={
                        nodes_node1: nodes,
                        children_node1: childrenS,
                        labels_node: batch_q,
                    }
                )
                batch_q.clear()
                nodes.clear()
                childrenS.clear()
                # train_loss += err
                # train_acc += accur
                # n_batch += 1
            # if aaa % 500 == 0:
            #     print('Epoch:', epoch,
            #           'Step:', aaa,
            #           'Loss:', train_loss/n_batch,
            #           'ACC:',train_acc/n_batch,
            #           'R:', r,
            #           )
            #     max_ACC = max(train_acc/n_batch,max_ACC)
            #     # saver.save(sess, "./model_o/model.ckpt")

            aaa += 1
        f.close()

        dev_loss, dev_acc, dev_batch = 0, 0, 0
        dev_acc_array = []
        f = open('D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/devdata.txt', 'r')
        line = "123"
        k = 0
        dev_batch_q = []
        while line:
            line = f.readline().rstrip('\n')
            l = line.split('\t')
            # m = l[0] in listrec
            if len(l) != 2:
                break
            if l[0] in listrec:
                continue
            k += 1
            nodes11, children1, batch_labels = getData_finetune(l, dictt, embeddings)
            # print(children1)
            dev_batch_q.append([batch_labels])
            dev_batch_s = int(batch_labels[0])
            # bb_labels = [batch_labels[0] for _ in range(50)]
            err, accur, r = sess.run(
                [loss_node, acc, res],
                feed_dict={
                    nodes_node1: [nodes11],
                    children_node1: children1,
                    labels_node: [dev_batch_s],
                }
            )
            dev_loss += err
            dev_acc += accur
            dev_batch += 1


            aaaa += 1

        f.close()

        ##################################################################################33
        print('Epoch:', epoch,
              'Step:', aaaa,
              'Loss:', dev_loss / dev_batch,
              'ACC:', dev_acc / dev_batch,
              'R:', r,
              )
        step_loss = dev_loss / dev_batch

        if step_loss < min_val_loss:
            saver.save(sess, './model_o/model.ckpt')
            min_val_loss = min(min_val_loss, dev_loss)
        # ssss_acc.append(dev_acc / dev_batch)
        # ssss_loss.append(dev_loss / dev_batch)
        # max_ACC = max(train_acc / n_batch, max_ACC)
        # saver.save(sess, "./model_o/model.ckpt")
        file3 = open('dev_acc.txt', 'a')
        file4 = open('dev_loss.txt', 'a')
        file3.write(str(dev_acc / dev_batch) + ";")

        file4.write(str(dev_loss / dev_batch) + ";")
        file3.close()
        file4.close()
        #########################################################################

        test_loss, test_acc, test_batch = 0, 0, 0
        dev_acc_array = []
        f = open('D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/testdata.txt', 'r')
        line = "123"
        k = 0
        test_batch_q = []
        while line:
            line = f.readline().rstrip('\n')
            l = line.split('\t')
            # m = l[0] in listrec
            if len(l) != 2:
                break
            if l[0] in listrec:
                continue
            k += 1
            nodes11, children1, batch_labels = getData_finetune(l, dictt, embeddings)
            # print(children1)
            test_batch_q.append([batch_labels])
            test_batch_s = int(batch_labels[0])
            # bb_labels = [batch_labels[0] for _ in range(50)]
            err, accur, r = sess.run(
                [loss_node, acc, res],
                feed_dict={
                    nodes_node1: [nodes11],
                    children_node1: children1,
                    labels_node: [test_batch_s],
                }
            )
            test_loss += err
            test_acc += accur
            test_batch += 1
            # if bbbb % 282 == 0:

            bbbb += 1

        f.close()

        print('Epoch_test:', epoch,
              'Step:', bbbb,
              'Loss_test:', test_loss / test_batch,
              'ACC_test:', test_acc / test_batch,
              'R_test:', r,
              )
        # ssss_acc.append(dev_acc / dev_batch)
        # ssss_loss.append(dev_loss / dev_batch)
        # max_ACC = max(train_acc / n_batch, max_ACC)
        # saver.save(sess, "./model_o/model.ckpt")*+
        file3 = open('test_acc.txt', 'a')
        file4 = open('test_loss.txt', 'a')
        file3.write(str(test_acc / test_batch) + ";")

        file4.write(str(test_loss / test_batch) + ";")
        file3.close()
        file4.close()
        # saver.save(sess, "./model_o/model.ckpt")


if __name__ == '__main__':
    dictt = {}
    dictta = {}
    listta = list()
    def _onehot(i, total):
        return [1.0 if j == i else 0.0 for j in range(total)]
    feature_size = 100
    fz = open("new 1.txt", 'r')
    line = "123"
    listchar = []
    while line:
        line = fz.readline().rstrip("\n")
        l = line.split(" ")
        listchar.extend(list(set(l)))
        listchar = list(set(listchar))
        # print(1)
    fz.close()
    for l in listchar:
        listta.append(np.random.normal(0, 0.1, 100).astype(np.float32))
    embeddingg = np.asarray(listta)
    embeddingg = tf.Variable(embeddingg)
    for i in range(len(listchar)):
        dictta[listchar[i]] = i
    train_model('D:/datasetforTBCCD-master/test_getSentenceJS/JSC_test/traindata.txt', dictta)