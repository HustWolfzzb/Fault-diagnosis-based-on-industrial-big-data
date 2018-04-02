/* *******************
* 这是从数据库或者是文本文件读取数据的时候用的
* 其实我觉得如果可以每一次读一条数据，然后处理一条会比较好
* 但是算了，数据量不大的话，这个样子也不会增加太多时间的！
******************* */
import java.io.*;

public class ReadData {
    public Object[][] ReadData() throws IOException {
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

        File file = new File("data.txt");  //存放数组数据的文件

        FileWriter DataToTXT = new FileWriter(file);  //文件写入流
        int row=0;
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for(int i=0;i<rawData.length;i++){
            for(int j=0;j<rawData[0].length;j++){
                DataToTXT.write(rawData[i][j]+"\t");
            }
            row++;
            DataToTXT.write("\r\n");
        }
        DataToTXT.close();

        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;  //一行数据
        //逐行读取，并将每个数组放入到数组中
        Object[][] DataToOut = new Object[row][5];
        int readrow=0;
        while((line = in.readLine()) != null){
            String[] temp = line.split("\t");
            for(int j=0;j<temp.length;j++){
                DataToOut[readrow][j] = temp[j];
            }
            readrow++;
        }
        in.close();

        //显示读取出的数组
        for(int i=0;i<DataToOut.length;i++){
            for(int j=0;j<DataToOut[0].length;j++){
                System.out.print(DataToOut[i][j]+"\t");
            }
            System.out.println();
        }
        return DataToOut;
    }
}
