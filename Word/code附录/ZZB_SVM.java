import java.io.IOException;
import java.text.NumberFormat;

public class ZZB_SVM {
    public static Float main() throws IOException {
        SVMReadData sr = new SVMReadData();
        Parameter par = new Parameter();
        String trainFileName = sr.readTrainData(par);
        String testFileName = sr.readTestData(par);
        //训练使用的数据以及训练得出生成的模型文件名。
        String[] trainFile = { trainFileName, "model.txt" };
        //测试数据文件，模型文件，结果存放文件
        String[] predictFile = { testFileName, "model.txt","predict.txt" };
        System.out.println("........SVM Start..........");
        long start=System.currentTimeMillis();
        svm_train.main(trainFile); //训练
        System.out.println("Usage of Time : "+(System.currentTimeMillis()-start));
        //预测
        float x = svm_predict.main(predictFile);
        return x;
    }
    public static void DataToPlot() throws IOException {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        int[] numOfTrain = new int[]{
                200, 400, 800, 1000, 2000,
                3000, 4000, 5000, 6000, 8000,
                10000, 12000, 15000, 17500, 20000,
                22500, 25000, 27500, 30000, 32500,
                35000, 37500, 40000, 42500, 45000,
        };
        float[] ACC = new float[numOfTrain.length];
        SVMReadData sr = new SVMReadData();
        Parameter par = new Parameter();
        for (int number = 0; number < numOfTrain.length; ++number) {
            par.setTrainNum(numOfTrain[number]);
            String trainFileName = sr.readTrainData(par);
            String testFileName = sr.readTestData(par);
            String[] trainFile = {trainFileName, "model.txt"};
            //测试数据文件，模型文件，结果存放文件
            String[] predictFile = {testFileName, "model.txt", "predict.txt"};
            long start = System.currentTimeMillis();
            svm_train.main(trainFile); //训练
            System.out.println("Usage of Time : " + (System.currentTimeMillis() - start));
            //预测
            ACC[number] = Float.parseFloat(nf.format(svm_predict.main(predictFile)));
        }
        System.out.print("x = [");
        for (int num:numOfTrain) System.out.print(num + ",");
        System.out.println("]");

        System.out.print("y1 = [");
        for (float num:ACC) System.out.print(num + ",");
        System.out.println("]");
    }
}
