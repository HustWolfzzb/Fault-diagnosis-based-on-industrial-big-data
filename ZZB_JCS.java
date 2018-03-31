
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
    static Map<Object,List<Sample>> readSample(String[] attribute_Names){
        //样本属性及其分类，暂时先在代码里面写了。后面需要数据库或者是文件读取
//########### 此处需要改造为读取外部数据！并且能够进行分解，改造为可读取的形式
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
* this is the function to generate the DecisionTree

* use the data which read from the files to get the Decisiontree 
********************* */
    static Object generateDecisionTree(Map<Object,List<Sample>> categoryToSamples,String[] attribute_Names){
        //如果只有一个样本，那么该样本所属分类作为新样本的分类
        if(categoryToSamples.size() == 1)
            return categoryToSamples.keySet().iterator().next();

        //如果没有提供决策的属性，那么样本集中具有最多样本的分类作为新样本的分类，也就是投票选举出新的分类
        if (attribute_Names.length == 0) {
            int max = 0;
            Object maxCategory = null;
            for (Entry<Object,List<Sample>> entry : categoryToSamples.entrySet() ) {
                int cur = entry.getValue().size();
            }
        }
    }
}



