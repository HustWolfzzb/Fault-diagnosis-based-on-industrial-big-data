import java.io.IOException;

public class ZZB_SVM {
    public static Float main() throws IOException {
        SVMReadData sr = new SVMReadData();
        String trainFileName = sr.readTrainData();
        String testFileName = sr.readTestData();
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
}
