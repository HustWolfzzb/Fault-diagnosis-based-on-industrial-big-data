
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.3-2018.5

 * Address  :   HUST

 * Version  :   3.0
 ********************* */


/* *******************
* 这是从数据库或者是文本文件读取数据的时候用的
* 其实我觉得如果可以每一次读一条数据，然后处理一条会比较好
* 但是算了，数据量不大的话，这个样子也不会增加太多时间的！
******************* */
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {
    protected Mysql_Connect mysql=new Mysql_Connect();
    public void writeToDatabase(Object[][] Data_Array) {
        int id=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            for (int s=0;s<Data_Array.length;++s) {
                Object[] data_array = Data_Array[s];
                String INSERT = "INSERT INTO watermelon(id,色泽,根蒂,敲声,纹理,脐部,触感,category) VALUES( " + id + "  , ' " + data_array[0] + "' , ' " + data_array[1] + "' ,  ' " + data_array[2] + "' ,  ' " + data_array[3] + "' ,  ' " + data_array[4] + "' , ' " + data_array[5] + " ', ' " + data_array[6] + "' )";
                boolean insert_ok = statement.execute(INSERT);
                if (insert_ok) {
                    System.out.println("Insert Failed!");
                }
                id++;
            }
            statement.close();
            mysql.Dis_Connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object[][] readFromDatabase() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from watermelon";
            String GETDATA;
            Object[][] DataToOut;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            GETDATA = "";
            DataToOut = new Object[columnCount][7];
            for (int  i = 0;i<columnCount;++i) {
                GETDATA = "SELECT  色泽,根蒂,敲声,纹理,脐部,触感,category FROM watermelon WHERE id=" + i;
                ResultSet select_ok;
                select_ok = statement.executeQuery(GETDATA);
                if (select_ok.next()) {
                    DataToOut[i][0] = select_ok.getObject("色泽");
                    DataToOut[i][1] = select_ok.getObject("根蒂");
                    DataToOut[i][2] = select_ok.getObject("敲声");
                    DataToOut[i][3] = select_ok.getObject("纹理");
                    DataToOut[i][4] = select_ok.getObject("脐部");
                    DataToOut[i][5] = select_ok.getObject("触感");
                    DataToOut[i][6] = select_ok.getObject("category");
                }
            }
            statement.close();
            mysql.Dis_Connect();
            return DataToOut;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[1][1];
    }

    public Object[][] ReadData() throws IOException {
        Object[][] rawData = new Object [][]{
                {"青绿","蜷缩","浊响","清晰","凹陷","硬滑","是"},
                {"乌黑","蜷缩","沉闷","清晰","凹陷","硬滑","是"},
                {"乌黑","蜷缩","浊响","清晰","凹陷","硬滑","是"},
                {"青绿","蜷缩","沉闷","清晰","凹陷","硬滑","是"},
                {"浅白","蜷缩","浊响","清晰","凹陷","硬滑","是"},
                {"青绿","稍蜷","浊响","清晰","稍凹","软粘","是"},
                {"乌黑","稍蜷","浊响","稍糊","稍凹","软粘","是"},
                {"乌黑","稍蜷","浊响","清晰","稍凹","硬滑","是"},
                {"乌黑","稍蜷","沉闷","稍糊","稍凹","硬滑","否"},
                {"青绿","硬挺","清脆","清晰","平坦","软粘","否"},
                {"浅白","硬挺","清脆","模糊","平坦","硬滑","否"},
                {"浅白","蜷缩","浊响","模糊","平坦","软粘","否"},
                {"青绿","稍蜷","浊响","稍糊","凹陷","硬滑","否"},
                {"浅白","稍蜷","沉闷","稍糊","凹陷","硬滑","否"},
                {"乌黑","稍蜷","浊响","清晰","稍凹","软粘","否"},
                {"浅白","蜷缩","浊响","模糊","平坦","硬滑","否"},
                {"青绿","蜷缩","沉闷","稍糊","稍凹","硬滑","否"},


//                { "<30  ", "High  ", "No ", "Fair     ", "0" },
//                { "<30  ", "High  ", "No ", "Excellent", "0" },
//                { "30-40", "High  ", "No ", "Fair     ", "1" },
//                { ">40  ", "Medium", "No ", "Fair     ", "1" },
//                { ">40  ", "Low   ", "Yes", "Fair     ", "1" },
//                { ">40  ", "Low   ", "Yes", "Excellent", "0" },
//                { "30-40", "Low   ", "Yes", "Excellent", "1" },
//                { "<30  ", "Medium", "No ", "Fair     ", "0" },
//                { "<30  ", "Low   ", "Yes", "Fair     ", "1" },
//                { ">40  ", "Medium", "Yes", "Fair     ", "1" },
//                { "<30  ", "Medium", "Yes", "Excellent", "1" },
//                { "30-40", "Medium", "No ", "Excellent", "1" },
//                { "30-40", "High  ", "Yes", "Fair     ", "1" },
//                { "<30  "  , "Medium", "No ", "Excellent", "1" },
//                { ">40  ", "Medium", "No ", "Excellent", "0" }
        };

// ***************** 写入文件式 **************
//        File file = new File("data.txt");  //存放数组数据的文件
//
//        FileWriter DataToTXT = new FileWriter(file);  //文件写入流
//        int row=0;
//        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
//        for(int i=0;i<rawData.length;i++){
//            for(int j=0;j<rawData[0].length;j++){
//                DataToTXT.write(rawData[i][j]+"\t");
//            }
//            row++;
//            DataToTXT.write("\r\n");
//        }
//        DataToTXT.close();
//
//        BufferedReader in = new BufferedReader(new FileReader(file));
//        String line;  //一行数据
//        //逐行读取，并将每个数组放入到数组中
//        Object[][] DataToOut = new Object[row][7];
//        int readrow=0;
//        while((line = in.readLine()) != null){
//            String[] temp = line.split("\t");
//            for(int j=0;j<temp.length;j++){
//                DataToOut[readrow][j] = temp[j];
//            }
//            readrow++;
//        }
//        in.close();

        //显示读取出的数组
//        for(int i=0;i<DataToOut.length;i++){
//            for(int j=0;j<DataToOut[0].length;j++){
//                System.out.print(DataToOut[i][j]+"\t");
//            }
//            System.out.println();
//        }
// ***************** 写入文件式 **************

        // ***************** 数据库读写式 **************

        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String DELETE="delete from watermelon where id<1000";
            boolean delete_ok=statement.execute(DELETE);
            if (delete_ok){
                System.out.println("Have Fun! Boys!\n\n");
            }
            statement.close();
            mysql.Dis_Connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeToDatabase(rawData);
        Object[][] DataToOut = readFromDatabase();
        return  DataToOut;
        // ***************** 数据库读写式 **************
    }
}
