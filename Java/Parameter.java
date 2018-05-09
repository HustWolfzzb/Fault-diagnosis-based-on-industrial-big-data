/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.0

 * 定义一些静态的数值，并且提供getter
 ********************* */

public class Parameter {
    private static int rate = 2;
    private static int trainNum = 8000;
    private static int testNum = trainNum/rate;
    public static int getTrainNum(){
        return trainNum;
    }
    public static int getRate(){
        return rate;
    }
    public static int getTestNum(){
        return testNum;
    }
    public static int getTestDistance(){
        return 2000000/testNum;
    }
    public static int getTrainDistance(){
        return 2000000/trainNum;
    }
    public static void setRate(int r){
        rate = r;
        testNum = trainNum / rate;
    }
    public static void setTrainNum(int t){
        trainNum = t;
        testNum = trainNum / rate;
    }
    public static void setTestNum(int t){
        testNum = t;
        trainNum = testNum * rate;
    }
}
