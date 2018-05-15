import java.io.IOException;

public class ZZB_SVM {
    public static void main() throws IOException {
        SVMReadData sr = new SVMReadData();
        String trainFileName = sr.readTrainData();
        String testFileName = sr.readTestData();
        String[] arg = { trainFileName, //训练集
                "model.txt" }; // 存放SVM训练模型

        String[] parg = { testFileName, //测试数据
                "model.txt", // 调用训练模型
                "predict.txt" }; //预测结果
        System.out.println("........SVM运行开始..........");
        long start=System.currentTimeMillis();
        svm_train.main(arg); //训练
        System.out.println("用时:"+(System.currentTimeMillis()-start));
        //预测
        svm_predict.main(parg);
    }
}
