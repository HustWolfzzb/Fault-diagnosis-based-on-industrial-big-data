public class Parameter {
    private  int rate = 2;
    private  int trainNum = 2000;
    private  int testNum = trainNum/rate;
    public int getTrainNum(){
        return this.trainNum;
    }
    public int getRate(){
        return this.rate;
    }
    public int getTestNum(){
        return this.testNum;
    }
    public int getTestDistance(){
        return 2000000/this.testNum;
    }
    public int getTrainDistance(){
        return 2000000/this.trainNum;
    }
}
