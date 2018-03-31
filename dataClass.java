// 信息熵：变量的不确定性越大，熵越大。熵可用下面的公式描述：
// -（p1*logp1+p2*logp2+...+pn*logpn)
// pi表示事件i发生的概率
// ID3：
// GAIN(A)=INFO(D)-INFO_A(D)
// 节点A的信息增益为不加节点A时的信息量INFO(D)-加上A后的信息量INFO_A(D)
// 算法步骤：
// 1、树以代表训练样本的某个结点开始
// 2、如果样本都在同一类，则将该节点设置为叶子，并使用该类标号
// 3、否则，算法使用熵度量每个样本的分类结点，选择可以获得最大信息的节点
// 4、所有的属性都是分类的，连续值必须离散化
// 停止条件：该节点上所有的样本都属于一个类
// 没有剩余的属性
// 没有属性时，比如已经分到第三个属性，但是没有第四个属性，这时将样本分到最多的那类
// C4.5与ID3区别在于属性度量方式的不同
// 优点：直观、便于理解、小规模数据有效
// 缺点：处理连续变量不好
// 类别较多时，错误增加比较快
// 可规模性一般

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class dataClass {
    public static void main(String[] args) {
         double [][]exerciseData = {{1,1,0,0},{1,3,1,1},{3,2,1,1},{2,2,1,1},{3,2,1,1},{2,3,0,1},{2,1,0,0},{3,2,0,1},{2,1,0,1},{1,1,1,0}};//每一列表示一个属性值，最后一列表示决策层
         
         int[] index = gainResult(exerciseData);//输出的结果表示按照决策树规则所对应的属性参考顺序
         for(int i = 0;i<index.length;i++){
         System.out.print("   "+(index[i]+1));
         }
         
    }

    private static int[] gainResult(double[][] exerciseData) {
        int dataQuantity = exerciseData.length;
         int attributeQuantity = exerciseData[0].length-1;
         int []attribute = new int[attributeQuantity];
         int []newAttribute = new int [attributeQuantity];
         double [][]newExerciseData = exerciseData ;
         double [][]maxgainIndexData = new double[dataQuantity][attributeQuantity];
         for(int i = 0;i<attributeQuantity;i++){
             attribute[i] = MaxgainIndex(newExerciseData);
             for(int j = 0;j<maxgainIndexData.length;j++){
                 maxgainIndexData[j][i] = newExerciseData[j][attribute[i]];
             }
             newExerciseData = NewData(newExerciseData,attribute[i]);
         }
         boolean flag =true;

         
         for(int i = 0;i<maxgainIndexData[0].length;i++){//寻找第i列所对应的exerciseData
             
             for(int k = 0;k<exerciseData[0].length-1;k++){
                 flag = true;
                 for(int j = 0;j<exerciseData.length;j++){
                     if(maxgainIndexData[j][i]!=exerciseData[j][k]){
                         flag = false;
                         break;
                     }
                 }
                 if(flag==true){
                     newAttribute[i] = k;
                 }
             }
         }
         return newAttribute;
    }
    
    //矩阵转置
    private static double[][] Transpose(double[][] exerciseData){
        
        int rows = exerciseData.length;
        int columns = exerciseData[0].length;
        double [][]newData = new double [columns][rows];
        for(int i = 0;i<columns;i++){
            for(int j= 0;j<rows;j++){
                newData[i][j] = exerciseData[j][i];
            }
        }
        return newData;
    }


    private static double[][] NewData(double[][] exerciseData,int maxIndex) {//删除exerciseData中maxindex列的数据，产生新数据
         double [][]newExerciseData = new double[exerciseData.length][];
         for(int i = 0;i<exerciseData.length;i++){
             newExerciseData[i] = new double[exerciseData[i].length-1];
             for(int j = 0;j<newExerciseData[i].length;j++){
                 if(j>=maxIndex){
                     newExerciseData[i][j] = exerciseData[i][j+1];
                 }else{
                     newExerciseData[i][j] = exerciseData[i][j];
                 }
             }
         }
         return newExerciseData;
    }

    
    

    private static int MaxgainIndex(double[][] exerciseData) {//获取exerciseData最大增益率所对应的一列
        double []gainRatio = gainAll(exerciseData);
         double  maxGain = gainRatio[0];//最大增益率
         int maxIndex = 0;//最大增益率所对应的索引值
         for(int i=1;i<gainRatio.length-1;i++){
             if(maxGain<gainRatio[i]){
                 maxGain = gainRatio[i];
                 maxIndex = i;
             }
         }
        return maxIndex;
    }
    
    
    public static double[] gainAll(double [][]Data){//得到Data中每一列的增益值
        int col = Data.length;//数据个数
        int vol = Data[0].length;//属性个数
        double [][]count = new double[vol][];
        double []info = new double[vol];
        double Lcount[][] = new double[vol][];//第i个属性的第j个分类的比率
        double Mcount[][] = new double[vol][];
        List <List<Map1>>listM = new ArrayList<List<Map1>>();
        List <List<Map1>>listM2 = new ArrayList<List<Map1>>();
        double []gain;
        //矩阵的属性统计
        for (int i = 0;i<vol;i++){
            
            //属性i的不重复的分类集（mapList加入了属性i以及对应的决策层的值）
            List<Map> mapList = new ArrayList<Map>();
            for(int j = 0;j<col;j++){
                    Map y = new HashMap();
                    y.put(Data[j][i],Data[j][vol-1]);
                    if(!mapList.contains(y)){
                        mapList.add(y);
                }
            }
            
            
            //属性i全部分类集（重复，listM2加入了i值以及决策层的值）
            List<Map> AllmapList = new ArrayList<Map>();
            for(int j = 0;j<col;j++){
                    Map y = new HashMap();
                    y.put(Data[j][i],Data[j][vol-1]);
                    AllmapList.add(y);
            }
            count[i] = new double[mapList.size()];
            double sum = 0;
            double num = 0;
            List<Map1>LM = new ArrayList<Map1>();
            for(int j=0;j<mapList.size();j++){
                Iterator it =((Map)(mapList.get(j))).keySet().iterator();
                num = (Double) it.next();
                for(int k = 0;k<AllmapList.size();k++){
                    if(mapList.get(j).equals(AllmapList.get(k))){
                        count[i][j] =  count[i][j]+1;
                    }
                }
                Map1 p = new Map1();
                p.setKey(count[i][j]);
                p.setValue(num);
                LM.add(p);
            }
            listM2.add(LM);
        }
        
        
        for( int k = 0;k<vol;k++){
            
            List <Double>list = new ArrayList<Double>();
            for(int i = 0;i<col;i++){
                    if(!list.contains(Data[i][k])){
                        list.add(Data[i][k]);
                    }
            }
            
            Lcount[k] = new double[list.size()];
            Mcount[k] = new double[list.size()];
            for(int j = 0;j<col;j++){
                int index = list.indexOf(Data[j][k]);
                Lcount[k][index] = Lcount[k][index]+1;
                Mcount[k][index] = Mcount[k][index]+1;
            }
            
            double LastSum = 0;
            for(int i = 0;i<Lcount[k].length;i++){
                LastSum = LastSum+Lcount[k][i];
            }
            for(int j = 0;j<Lcount[k].length;j++){
                Lcount[k][j] = Lcount[k][j]/LastSum;
            }
             List<Map1> LM = new ArrayList<Map1>();
            for(int i = 0;i<Lcount[k].length;i++){
                Map1 p = new Map1();
                p.setKey(Mcount[k][i]);
                p.setValue(list.get(i));
                LM.add(p);
            }
            listM.add(LM);
            }
        
        
        
        gain = new double[listM2.size()];
        for(int i = 0; i<listM2.size()-1;i++){
            List listi = new ArrayList();
            listi = listM.get(i);
            double sum = 0;
            for(int j=0;j<listi.size();j++){
                Map1 p = (Map1) listi.get(j);
                double key = p.getKey();
                double value = p.getValue();
                
                for(int k = 0;k<listM2.get(i).size();k++){
                    Map1 p1 = (Map1) listM2.get(i).get(k);
                    
                    if(p1.value==value){
                        sum = sum+xlog2(p1.key/p.key);
                    }
                    
                    //System.out.println(sum);
                }
                gain[i]+=sum*Lcount[i][j];
                sum = 0;
                
            }
            
        }
        for(int i = 0;i<Lcount[Lcount.length-1].length;i++){
            gain[listM2.size()-1] += -xlog2(Lcount[Lcount.length-1][i]);
        }
        for(int j = 0;j<gain.length-1;j++){
            gain[j] = gain[gain.length-1]+gain[j];
        }
        double[]Scount = new double [Lcount.length-1];
        for(int j= 0;j<Lcount.length-1;j++){
            double sum = 0;
            for(int k = 0;k<Lcount[j].length;k++){
                sum += xlog2(Lcount[j][k]);
            }
            Scount[j] = -sum;
        }
        for(int j= 0;j<Scount.length;j++){
            gain[j] = gain[j]/Scount[j];
        }
        
        return gain;
    }
    public static boolean contain(Map mapList,double key,double value){
        if(value==Double.parseDouble(mapList.get(key).toString())){
            return true;
        }else{
            return false;
        }
    }
    public static double xlog2(double x){
        return x*(Math.log(x)/Math.log((double)2));
    }
}