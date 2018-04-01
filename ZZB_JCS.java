
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.3-2018.5

 * Address  :   HUST

 * Version  :   1.0
 ********************* */


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//最外层类名
public class ZZB_JCS{

    /* *********************
     * Define the Class of Sample

     * it is about its nature and function
     ********************* */

    static class Sample{
        //attributes means the 属性
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
    static Map<Object,List<Sample>> readSample(String[] attribute_Names){
        //样本属性及其分类，暂时先在代码里面写了。后面需要数据库或者是文件读取
        Object[][] rawData = new Object [][]{
                { "<30  ", "High  ", "No ", "Fair     ", "0" },
                { "<30  ", "High  ", "No ", "Excellent", "0" },
                { "30-40", "High  ", "No ", "Fair     ", "1" },
                { ">40  ", "Medium", "No ", "Fair     ", "1" },
                { ">40  ", "Low   ", "Yes", "Fair     ", "1" },
                { ">40  ", "Low   ", "Yes", "Excellent", "0" },
                { "30-40", "Low   ", "Yes", "Excellent", "1" },
                { "<30  ", "Medium", "No ", "Fair     ", "0" },
                { "<30  ", "Low   ", "Yes", "Fair     ", "1" },
                { ">40  ", "Medium", "Yes", "Fair     ", "1" },
                { "<30  ", "Medium", "Yes", "Excellent", "1" },
                { "30-40", "Medium", "No ", "Excellent", "1" },
                { "30-40", "High  ", "Yes", "Fair     ", "1" },
                { ">40  ", "Medium", "No ", "Excellent", "0" }
        };
        //最终组合出一个包含所有的样本的图
        Map<Object,List<Sample>> sample_set = new HashMap<Object,List<Sample>>();

        //读取每一排的数据
        //分解后读取样本属性及其分类，然后利用这些数据构造一个Sample对象
        //然后按照样本最后的0，1进行二分类划分样本集，
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
            //现在整体样本集中查询，如果这个类别还没有样本，那么就添加一下
            if(samples == null){
                samples = new LinkedList<Sample>();
                sample_set.put(row[i],samples);
            }
            //不管是当前分类的样本集中是否为空，都要加上把现在分离出来的样本丢进去。
            //此处基本只有前几次才会进入if，后面各个分类都有了样本就不会为空了。
            samples.add(sample);
        }
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
     * this is the function to choose the Best Test Attribute

     * it will be used in the generateDecisionTree()

     * 选取最优测试属性。最优是指如果根据选取的测试属性分支，则从各分支确定新样本

     * 的分类需要的信息量之和最小，这等价于确定新样本的测试属性获得的信息增益最大

     * 返回数组：选取的属性下标、信息量之和、Map(属性值->(分类->样本列表))
     ********************* */

    static Object[] chooseBestTestAttribute(Map<Object,List<Sample>> categoryToSamples,String[] attribute_Names){
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

            //按照当前属性构建Map，属性值->(分类->样本列表)
            Map<Object,Map<Object,List<Sample>>> curSplits = new HashMap<Object,Map<Object,List<Sample>>>();
            for (Entry<Object,List<Sample>> entry : categoryToSamples.entrySet()) {
                Object category = entry.getKey();
                List<Sample> samples = entry.getValue();
                for (Sample sample : samples ) {
                    Object attrValue = sample.getAttribute(attribute_Names[attrIndex]);
                    Map<Object,List<Sample>> split = curSplits.get(attrValue);
                    if (split == null) {
                        split = new HashMap<Object,List<Sample>>();
                        curSplits.put(attrValue,split);
                    }
                    List<Sample> splitSamples = split.get(category);
                    if (splitSamples == null) {
                        splitSamples = new LinkedList<Sample>();
                        split.put(category,splitSamples);
                    }
                    splitSamples.add(sample);
                }
                allCount += samples.size();
            }

            // 计算将当前属性作为测试属性的情况下在各分支确定新样本的分类需要的信息熵之和
            double curValue = 0.0;
            for (Map<Object,List<Sample>> splits : curSplits.values()) {
                double perSplitCount = 0;
                for (List<Sample> list : splits.values()) {
                    //累计当前样本的分支总数
                    perSplitCount += list.size();
                }
                //计数器，当前分支
                double perSplitValue = 0.0;
                for (List<Sample> list : splits.values() ) {
                    double p = list.size() / perSplitCount;
                    perSplitValue -= p*(Math.log(p)/Math.log(2));
                }
                curValue += (perSplitCount / allCount) * perSplitValue;
            }
            //选择最小的信息熵为最优！
            if (minValue > curValue){
                minIndex = attrIndex;
                minValue = curValue;
                minSplit = curSplits;
            }
        }
        return  new Object[] {minIndex,minValue,minSplit};
    }



    /* *********************
     * this is the function to output the Decision Tree to the Dashboard
     ********************* */

    static void outputDecisionTree(Object obj,int level, Object from){
        for (int i=0; i < level ;++i){
            System.out.print("|-----");
        }
        if (from != null){
            System.out.printf("(%s):",from);
        }
        if (obj instanceof Tree){
            Tree tree = (Tree) obj;
            String attribute_Name = tree.getAttribute();
            System.out.printf("[%s = ?]\n",attribute_Name);
            for (Object attrValue : tree.getAttributeValues()){
                Object child =tree.getChild(attrValue);
                outputDecisionTree(child,level+1,attribute_Name + "=" + attrValue);
            }
        }else {
            System.out.printf("[CATEGORY = %s]\n", obj);
        }
    }


    /* *********************
     * this is the function to generate the DecisionTree

     * use the data which read from the files to get the Decisiontree
     ********************* */
    static Object generateDecisionTree(Map<Object,List<Sample>> categoryToSamples,String[] attribute_Names){
        //如果只有一个样本，那么该样本所属分类作为新样本的分类
        if(categoryToSamples.size() == 1)
            return categoryToSamples.keySet().iterator().next();

        //如果没有提供决策的属性（也就是没有给你属性名字清单），那么样本集中具有最多样本的分类作为新样本的分类，也就是投票选举出新的分类
        if (attribute_Names.length == 0) {
            int max = 0;
            Object maxCategory = null;
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
        Object[] rst = chooseBestTestAttribute(categoryToSamples,attribute_Names);
        //决策树的根节点选取，分支的属性为选取的测试属性
        Tree tree = new Tree(attribute_Names[(Integer)rst[0]]);

        //已用过的测试属性不能再次被选择为测试属性
        String[] subA = new String[attribute_Names.length-1];
        for (int i=0,j=0;i<attribute_Names.length ;++i ) {
            if (i != (Integer)rst[0]) {
                subA[j++] = attribute_Names[i];
            }
        }

        //根据分支的属性生成新的分支
        @SuppressWarnings("unchecked")
        Map<Object,Map<Object,List<Sample>>> splits = (Map<Object,Map<Object,List<Sample>>>) rst[2];
        for (Entry<Object,Map<Object,List<Sample>>> entry : splits.entrySet()) {
            Object attrValue = entry.getKey();
            Map<Object,List<Sample>> split = entry.getValue();
            Object child = generateDecisionTree(split,subA);
            tree.setChild(attrValue,child);
        }
        return tree;
    }

    public static  void main(String[] args) throws Exception{
        String[] attribute_Names = new String[] {"AGE","INCOME","STUDENT","CREDIT_RATING"};

        //读取样本集
        Map<Object,List<Sample>> samples = readSample(attribute_Names);

        //生成决策树
        Object decisionTree = generateDecisionTree(samples,attribute_Names);

        //输出决策树
        outputDecisionTree(decisionTree,0,null);
    }
}



