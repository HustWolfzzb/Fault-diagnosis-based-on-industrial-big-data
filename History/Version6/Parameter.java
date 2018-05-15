/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.0

 * 定义一些静态的数值，并且提供getter
 ********************* */

import java.text.NumberFormat;
import java.util.*;

class Alone_Value_Category  implements Comparable<Alone_Value_Category>{
    private float sensor;
    private float category;
    //    private float[] range = new float[2];
    Alone_Value_Category(float a, float b){
        super();
        this.sensor = a;
        this.category = b;
    }

    float getSensor(){
        return  sensor;
    }
    float getCategory(){
        return category;
    }
    //    void setRange(float a, float b){
//        range[0] = a;
//        range[1] =b;
//    }
    @Override
    public String toString() {
        return "\n[Sensor:" + sensor + ", category=" + category + "]";
    }
    @Override
    public int compareTo(Alone_Value_Category o) {
        return Float.compare(this.sensor,o.sensor);
    }
}

class Interval{
    private float top;
    private float bottom;
    public Map<Float,List<Alone_Value_Category> > sample = new HashMap<Float, List<Alone_Value_Category>>();
    Interval(){};
    Interval(Interval b){
        top = b.top;
        bottom = b.bottom;
        sample = b.sample;
    }
    Interval(float a, float b, float c, List<Alone_Value_Category> d){
        this.top = a;
        this.bottom = b;
        sample.put(c,d);
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public void setSample(Map<Float, List<Alone_Value_Category>> sample) {
        this.sample = sample;
    }

    public Interval addTmp(Interval b){
        Interval re = new Interval(b);
        if (top>b.top) re.setTop(top);
        else re.setTop(b.top);
        if (bottom<b.bottom) re.setBottom(bottom);
        else re.setBottom(b.bottom);
        re.sample.putAll(sample);
        return re;
    }
    public void merge(Interval b){
        if (top<b.top)
            top = b.top;
        if (bottom>b.bottom)
            bottom = b.bottom;
        sample.putAll(b.sample);
    }
    public int getCount(){
        int count = 0;
        for(List<Alone_Value_Category> s:sample.values()){
            count+=s.size();
        }
        return count;
    }
    @Override
    public String toString() {
        return "bottom:"+bottom+" top:"+top+" size:"+getCount();
    }
}
public class Parameter {
    private  int rate;
    private int trainNum;
    private  int testNum;
    Parameter(){
        rate = 2;
        trainNum = 10000;
        testNum = trainNum/rate;
    }
    public int getTrainNum(){
        return trainNum;
    }
    public int getTestNum(){
        return testNum;
    }
    public int getTestDistance(){
        return 2000000/testNum;
    }
    public int getTrainDistance(){
        return 2000000/trainNum;
    }
    public void setTrainNum(int t){
        trainNum = t;
        testNum = trainNum / rate;
    }
    public void setTestNum(int t){
        testNum = t;
        trainNum = testNum * rate;
    }



    void Clear(ArrayList<Interval> allInterval){
        ArrayList<Interval> del = new ArrayList<>();
        for (int s = 0;s<allInterval.size();++s) {
            if (allInterval.get(s).getCount() == 0){
                if (s>0) {
                    allInterval.get(s - 1).merge(allInterval.get(s));
                    del.add(allInterval.get(s));
                }
                continue;
            }
        }
        allInterval.removeAll(del);
    }
    double Entropy(ArrayList<Interval> set, int size){
        double shang = 0;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);
        for (Interval x:set){
            double p =(double)x.getCount()/(double)size;
            shang  -= p*(Math.log(p)/Math.log(2));
        }
        return  Double.parseDouble(nf.format(shang));
    }

    public ArrayList<List<Float>> EADC(float[][] dat) {
        ArrayList<List<Float>> re = new  ArrayList<>();
        for (int valueindex = 0; valueindex< dat[0].length-1;++valueindex) {
            ArrayList<Alone_Value_Category> LIST = new ArrayList<>();
            for (int i = 0; i < dat.length; ++i) {
                LIST.add(new Alone_Value_Category(dat[i][valueindex], dat[i][dat[valueindex].length - 1]));
                //便利旧集合没有就添加到新集合
            }
            Collections.sort(LIST);
            float len = LIST.get(LIST.size() - 1).getSensor() - LIST.get(0).getSensor();
            int k = 40;
            float gap = (len + 1) / k;
            float Lowest = LIST.get(0).getSensor() - 0.50f;
            float Highest = LIST.get(LIST.size()-1).getSensor() + 0.50f;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(1);
            List<Float> range = new LinkedList<>();
            for (int x = 0; x <= k; ++x) {
                range.add(Float.parseFloat(nf.format(Lowest + x * gap)));
            }
            ArrayList<Interval> allInterval = new ArrayList<>();
            for (int i = 0; i < k; ++i) {
                Interval newarea = new Interval();
                newarea.setBottom(range.get(i));
                newarea.setTop(range.get(i + 1));
                for (Alone_Value_Category s : LIST) {
                    if (s.getSensor() > range.get(i) && s.getSensor() < range.get(i + 1)) {
                        if (!newarea.sample.containsKey(s.getCategory())) {
                            newarea.sample.put(s.getCategory(), new LinkedList<>());
                        }
                        newarea.sample.get(s.getCategory()).add(s);
                    }
                }
                allInterval.add(newarea);
            }
            int size = 0;
            Clear(allInterval);
            for (Interval s : allInterval) {
                size += s.getCount();
            }
            k = allInterval.size();
            int k0 = k;
            double Ck0 = 0;
            boolean Loop = true;
            double Hpk_1 = 0;
            while (Loop && k >= 10) {
                double minD = 1000;
                int mergePoint = 0;
                double Hp0 = Entropy(allInterval, size);
                double Hpk;
                ArrayList<Interval> newA = new ArrayList<>();
                for (int i = 0; i < allInterval.size() - 1; ++i) {
                    newA.addAll(allInterval);
                    newA.get(i).merge(newA.get(i + 1));
                    newA.remove(i + 1);
                    Hpk = Entropy(newA, size);
                    if (Hpk - Hp0 < minD) {
                        Hpk_1 = Hpk;
                        minD = Hpk - Hp0;
                        mergePoint = i;
                    }
                    newA.clear();
                }
                allInterval.get(mergePoint).merge(allInterval.get(mergePoint + 1));
                allInterval.remove(mergePoint + 1);
                double Ck_1 = (k0 - 1) * Hpk_1 - Hp0 * (k - 2);
                if (Ck_1 > Ck0) {
                    --k;
                } else {
                    Loop = false;
                    --k;
                }
                Ck0 = Ck_1;
            }
            range.clear();
            range.add(-100f);
            for (Interval s:allInterval) {
                range.add(s.getTop());
            }
            range.add(100f);
            re.add(range);
//        long endTime=System.currentTimeMillis(); //获取结束时间
//        System.out.println("\n程序运行时间： "+(endTime-startTime)+"ms");
        }
        return re;
    }
}
