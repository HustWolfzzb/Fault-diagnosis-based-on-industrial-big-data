
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.5
 ********************* */


import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import libsvm.*;

//最外层类名
public class ZZB_JCS{
    /* *********************
     * Define the Class of Sample

     * it is about its nature and function
     ********************* */

    static class Sample{
        //attributes means 属性
        private Map<String,Object> attributes = new HashMap<String,Object>();
        //category means 类别
        private Object category;

        public Object getAttribute(String name){
            return attributes.get(name);
        }

        public void setAttribute(String name,Object value){
            attributes.put(name,value);
        }

        public void setCategory(Object category){
            this.category=category;
        }

        public String toString(){
            return attributes.toString();
        }
    }
    /* *********************
     * this is the function to read the sample

     * just like decoding the data
     ********************* */

// 此处需要改造为读取外部数据！并且能够进行分解，改造为可读取的形式
    static Map<Object,List<Sample>> readSample(String[] attribute_Names) throws IOException {
        //样本属性及其分类，暂时先在代码里面写了。后面需要数据库或者是文件读取
        ReadData data = new ReadData();
        Object[][] rawData =  data.readTrainData();
        //最终组合出一个包含所有的样本的Map
        Map<Object,List<Sample>> sample_set = new HashMap<Object,List<Sample>>();

        //读取每一排的数据
        //分解后读取样本属性及其分类，然后利用这些数据构造一个Sample对象
        //然后按照样本最后的分类划分样本集，
        for (Object[] row:rawData) {
            //新建一个Sample对象，没处理一次加入Map中，最后一起返回
            Sample sample = new Sample();
            int i=0;
            //每次处理一排数据，构成一个样本中各项属性的值
            for (int n=row.length-1; i<n; ++i) {
                sample.setAttribute(attribute_Names[i],row[i]);
            }
            //为处理完的一个样本进行分类，根据0，1来，此时i已经在最后一位
            sample.setCategory(row[i]);
            //将解析出来的一排加入整体分类后的样本中，row[i]此刻是指分类后的集合
            List<Sample> samples = sample_set.get(row[i]);
            //现在整体样本集中查询，有的话就返回value，而如果这个类别还没有样本，那么就添加一下
            if(samples == null){
                samples = new LinkedList<Sample>();
                sample_set.put(row[i],samples);
            }
            //不管是当前分类的样本集中是否为空，都要加上把现在分离出来的样本丢进去。
            //此处基本只有前几次分类没有完毕的时候才会进入if，后面各个分类都有了样本就不会为空了。
            samples.add(sample);
        }
        //最后返回的是一个每一个类别一个链表的Map，串着该类别的所有样本 (类别 --> 此类样本)
        return sample_set;
    }

    /* *********************
     * this is the class of the decision-tree

     * 决策树（非叶结点），决策树中的每个非叶结点都引导了一棵决策树

     * 每个非叶结点包含一个分支属性和多个分支，分支属性的每个值对应一个分支，该分支引导了一棵子决策树
     ********************* */

    static class Tree{

        private String attribute;
        private Map<Object,Object> children = new HashMap<Object,Object>();
        public Tree(String attribute){
            this.attribute=attribute;
        }

        public String getAttribute(){
            return attribute;
        }

        public Object getChild(Object attrValue){
            return children.get(attrValue);
        }

        public void setChild(Object attrValue,Object child){
            children.put(attrValue,child);
        }

        public Set<Object> getAttributeValues(){
            return children.keySet();
        }
    }


    /* *********************
     * this is the function to choose the Best Test Attribute ID3 Algorithm

     * it will be used in the generateDecisionTree()

     * 选取最优测试属性。最优是指如果根据选取的测试属性分支，则从各分支确定新样本

     * 的分类需要的信息熵之和最小，这等价于确定新样本的测试属性获得的信息增益最大

     * 返回数组：选取的属性下标、信息熵之和、Map(属性值->(分类->样本列表))
     ********************* */

    static Object[] ID3(Map<Object,List<Sample>> categoryToSamples,String[] attribute_Names){
        //最优的属性的下标！
        int minIndex = -1;
        //最小的信息熵
        double minValue = Double.MAX_VALUE;
        //最优的分支方案！
        Map<Object,Map<Object,List<Sample>>> minSplit = null;

        //对每一个属性，都要计算信息熵，选区最小的为最优，Ent(D)
        for (int attrIndex = 0;attrIndex<attribute_Names.length;++attrIndex) {
            //统计样本总数的计数器
            int allCount = 0;

            //按照当前属性构建Map，这个Map的层级关系根据下面的层次划分：属性值[Key]->(分类[Key]->样本列表[Value]) [Value]
            // curSplits就是一个某一个在当前属性下某一种选择值 所对应的所有样本集！ 所有的Dv的集合是也？？待定！
            Map<Object,Map<Object,List<Sample>>> curSplits = new HashMap<Object,Map<Object,List<Sample>>>();

    /* 这儿的整个流程画个图哈~下面是对某一个属性进行信息增益的计算了！

                   拿到一个数据对，【所属类别-->样本集】
                             |
                             V
                    解析数据对，分解出key和value
                其中key为类别，value为此类别所有的样本
                             |
                             V
               对于Value里边读出来的每个样本，分别：
      读取当前属性下的值，然后建立起来当前属性值相同的所有样本的样本集；
                             |
                             V
               此处还要将每个样本集拆分为分类样本集！
                             |
                             V
        这一轮下来，就得到关于这个属性的不同属性值对应的样本集合
                    而在这些集合集合中又有分类样本集！
          就好比，这一轮对年龄下手，最终得到了40岁以上的好人、坏人
                     30-40岁之间的好人、坏人集合
                     30岁以下的好人、坏人的集合
                     最后一共得到了6个样本集？
             只不过是已Map中键值对的形式存在，二层包装而已！
                                                                --正分类-->  一个Map
                                 ---属性值1，比如是学生  ->分类两类 |
                         某一属性（这个就是curSplits这个Map的本体）  --负分类-->  一个Map（此处画图方便重用了！）
                                 ---属性值2，比如不是学生->分类两类 |
                                                                 --正分类-->  一个Map
      */

            /*
             * Set<Map.Entry<K,V>> entrySet​()
             * Returns:  A set view of the mappings contained in this map
             * Entry 这个数据类型大致等于C++中的pair，也就是数据打包的意思
             */
            for (Entry<Object,List<Sample>> entry : categoryToSamples.entrySet()) {
                //先拿到数据的分类的名称，我们这儿就0，1
                Object category = entry.getKey();
                //再拿到这个类别！注意是类别，不是属性值！类别所对应的所有样本！
                List<Sample> samples = entry.getValue();
                //然后再慢慢的对每个样本进行操作，将其分为按照属性值划分的各种Dv，然后返回到curSplits
                for (Sample sample : samples ) {
                    // 根据当前要计算的属性，得到当前样本的关于这个属性的值
                    Object attrValue = sample.getAttribute(attribute_Names[attrIndex]);
                    // 根据前面当前样本getAttribute()所获得的属性值，来获取这个属性的值相同的所有的样本的样本集
                    Map<Object,List<Sample>> split = curSplits.get(attrValue);
                    // 考虑到一开始肯定没法得到一个完整的Map,所以需要从无到有建立起来！
                    if (split == null) {
                        //建立一个关于这个属性值的Map，层次关系为：属性值->(All Sample) 见Line156
                        split = new HashMap<Object,List<Sample>>();
                        curSplits.put(attrValue,split);
                    }
                    //建立起来之后，就可以读取这个属性值等于某个值时对应的分类样本集合了。
                    List<Sample> splitSamples = split.get(category);
                    // 如果读不到当前属性对应这个值的分类的话，那就要建立一个属性值等于当前样本的属性值，且分类相同的样本集。
                    if (splitSamples == null) {
                        splitSamples = new LinkedList<Sample>();
                        // 结合当前这个属性值，组成一个集合，放到Map--split里面去。
                        split.put(category,splitSamples);
                    }
                    // 最后再把当前的这个样本放到这个样本集中？？？！！可以直接这么搞的？%%%%%% 难道是引用传递？
                    // 是的！没有用new自然就是一个引用传递！卧槽！这都给忘了！？
                    splitSamples.add(sample);
                }
                //统计样本总数的计数器需要对当前属性下的样本的数量进行统计。
                allCount += samples.size();
            }

            // 当前属性值的信息增益寄存器
            double curValue = 0.0;
            //读取当前属性下的每一种属性值对应的样本集
            for (Map<Object,List<Sample>> splits : curSplits.values()) {
                double perSplitCount = 0;
                //读取每个属性值的样本集Dv的size，得到所有该属性为此值的样本总数，不论类别如何
                for (List<Sample> list : splits.values()) {
                    //累计当前样本的分支总数
                    perSplitCount += list.size();
                }
                //计数器，当前分支的信息熵和信息增益，这儿是按出现频率在算呢！
                double perSplitValue = 0.0;
                //计算每个属性值对应的信息熵
                for (List<Sample> list : splits.values() ) {
                    //此处完全就是ID3算法的信息熵的计算公式！也就是ENT(D) = -Sum(Pk*log2(Pk))见《机器学习》 P75
                    double p = list.size() / perSplitCount;
                    //貌似是因为p无论如何都是小于1的，所以采用p -= 实际上是加了？
                    perSplitValue -= p*(Math.log(p)/Math.log(2));
                }
                //这应该还算不上不是信息增益吧！只能算是信息熵之和了。
                curValue += (perSplitCount / allCount) * perSplitValue;
            }
            //选择最小的信息熵为最优！？
            if (minValue > curValue){
                minIndex = attrIndex;
                minValue = curValue;
                minSplit = curSplits;
            }
        }
        //所以最终返回的就是一个信息熵之和  最小的属性的列表索引 + 最小的信息熵之和  + 最小的信息熵之和所对应的子树！
        return  new Object[] {minIndex,minValue,minSplit};
    }


    /* *********************
     * this is the function to output the Decision Tree to the Dashboard
     ********************* */

    static void outputDecisionTree(FileWriter out,Object obj,int level, Object from) throws IOException {
        //这个到后面决定输出多少个|----- 也就是说是决定层级的
        for (int i=0; i < level ;++i){
//            System.out.print("|---->");
            out.write("|---->");
        }
        // 所有子节点专用？除了根节点都要吧！
        if (from != null){
//            System.out.printf("(%s):",from);
            out.write("("+from+"):");
        }
        //大概是说，如果这个东西还有子节点，那就继续递归
        if (obj instanceof Tree){
            Tree tree = (Tree) obj;
            String attribute_Name = tree.getAttribute();
//            System.out.printf("[%s = ?]\n",attribute_Name);
            out.write("["+attribute_Name+" = ?]\n");
            for (Object attrValue : tree.getAttributeValues()){
                Object child =tree.getChild(attrValue);
                outputDecisionTree(out,child,level+1,attribute_Name + " = " + attrValue);
            }
        }else {
//            System.out.printf("【* CATEGORY = %s *】\n", TestData.getFault((String) obj));
            out.write("【* CATEGORY = "+TestData.getFault((String) obj)+" *】\n");
        }
    }


    /* *********************
     * this is the function to generate the DecisionTree

     * use the data which read from the files to get the Decisiontree

     * the most important part I think!
     ********************* */
    static Object generateDecisionTree(Map<Object,List<Sample>> categoryToSamples,String[] attribute_Names){
        //如果只有一个样本，那么该样本所属分类作为新样本的分类
        if(categoryToSamples.size() == 1) {
            return categoryToSamples.keySet().iterator().next();
        }

        //如果没有提供决策的属性（也就是没有给你属性名字清单），那么样本集中具有最多样本的分类作为新样本的分类，也就是投票选举出新的分类
        if (attribute_Names.length == 0) {
            int max = 0;
            Object maxCategory = null;
            // 如果没有属性列表的话，那就直接按照分类作为K个样本集，取数量较大的那个样本集的类别作为本分类。
            for (Entry<Object,List<Sample>> entry : categoryToSamples.entrySet() ) {
                int cur = entry.getValue().size();
                if (cur > max) {
                    max = cur;
                    maxCategory = entry.getKey();
                }
            }
            return maxCategory;
        }
        //如果有属性清单的话，那么就选择测试所用的属性了。
        Object[] rst = ID3(categoryToSamples,attribute_Names);
        //决策树的根节点选取，分支的属性为选取的测试属性
        Tree tree = new Tree(attribute_Names[(Integer)rst[0]]);
        //已用过的测试属性不能再次被选择为测试属性
        String[] Attr_Not_Used = new String[attribute_Names.length-1];
        for (int i=0,j=0;i<attribute_Names.length ;++i ) {
            if (i != (Integer)rst[0]) {
                Attr_Not_Used[j++] = attribute_Names[i];
            }
        }

        //根据分支的属性生成新的分支
        @SuppressWarnings("unchecked")
        Map<Object,Map<Object,List<Sample>>> splits = (Map<Object,Map<Object,List<Sample>>>) rst[2];
        for (Entry<Object,Map<Object,List<Sample>>> entry : splits.entrySet()) {
            Object attrValue = entry.getKey();
            Map<Object,List<Sample>> split = entry.getValue();
            //又是递归调用？那我岂不是玩完？层数不能超过二十层！这是底线！
            Object child = generateDecisionTree(split,Attr_Not_Used);
            tree.setChild(attrValue,child);
        }
        return tree;
    }

    public static  void main(String[] args) throws Exception{
        long startTime=System.currentTimeMillis();   //获取开始时间
//        String[] Test_Names = new String[] {"Diff_X","Diff_Y","Pixels_Areas","Diff_Luminosity","TypeOfSteel","Steel_Plate_Thickness"};
//        String[] attribute_Names = new String[] {"Diff_X","Diff_Y","Pixels_Areas","Diff_Luminosity","TypeOfSteel","Steel_Plate_Thickness","Fault"};
//        //读取样本集
//        Map<Object,List<Sample>> samples = readSample(attribute_Names);
//        //生成决策树
//        Object decisionTree = generateDecisionTree(samples,Test_Names);
//        Object[] test = new Object[] {"0","2","11","6","0","200"};
//        //输出决策树
//        File file = new File("/Users/zhangzhaobo/Documents/Graduation-Design/Data/GUIDATA.txt");
//        FileWriter out = new FileWriter(file);
//        outputDecisionTree(out,decisionTree,0,null);
//        out.close();
//        MouseAndKeyEvent gui = new MouseAndKeyEvent();
//        BufferedReader in = new BufferedReader(new FileReader(file));
//        int linecount = 0;
//        while(in.readLine()!=null){
//            ++linecount;
//        }
//        in.close();
//        System.out.println(linecount);
//        in = new BufferedReader(new FileReader(file));
//        String[] LINES= new String[linecount];
//        for (int i=0;i<linecount;++i){
//            LINES[i]=in.readLine();
//        }
//        in.close();
//        FileWriter out1 = new FileWriter("DataToTest.txt");
//        ReadData data = new ReadData();
//        Object[][] DataToTest =  data.readTestData();
//        for (int i=0;i<DataToTest.length;++i){
//            for (int j=0;j<DataToTest[i].length;++j){
//                out1.write(DataToTest[i][j] + " ");
//            }
//            out1.write("\n");
//        }
//        out1.close();
//        MouseAndKeyEvent.updateTEXT(gui,LINES,decisionTree);
////        *****原代码有点问题！应该是给定一个没有分类的属性列表去给他！而不带有分类的属性列表，这样会把分类作为一个属性的！*****
////        String line="";
////        TestData.TestData(decisionTree, Test_Names,test,line);
        ZZB_SVM.main();
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
    }
}



