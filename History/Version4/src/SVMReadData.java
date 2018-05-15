import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SVMReadData {
    private Object[] Name;
    protected Mysql_Connect mysql=new Mysql_Connect();
    private int rate = 20;
    private static int atoi(String s) {
        return Integer.parseInt(s);
    }
    SVMReadData() throws IOException {
        File file = new File("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/New_Data.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;  //一行数据作为属性名字
        line = in.readLine();
        in.close();
        Name = line.split("\t\t");
    }

    public String readTrainData() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from steelplate";
            String getDataQuery="";
            FileWriter svmTrainData = new FileWriter("svmTrainData.txt");
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            Object[][] DataTrain = new Object[columnCount/rate*(rate-1)][7];
            int count = 0;
            for (int  i = 0;i<columnCount;++i) {
                if(i%rate != 1) {
                    getDataQuery = ReadData.getSelectQuery(Name, "steelplate", i);
                    ResultSet selectTrainOk;
                    selectTrainOk = statement.executeQuery(getDataQuery);
                    selectTrainOk.next();
                    for (int j = 0; j < 7; ++j) {
                        DataTrain[count][j] = selectTrainOk.getObject((String) Name[j]);
                    }
                    if(atoi((String) DataTrain[count][DataTrain[0].length-1])<6)
                        svmTrainData.write("1 ");
                    else
                        svmTrainData.write("0 ");
                    for (int j = 0; j<DataTrain[0].length-1; ++j ){
                        svmTrainData.write(j+":"+DataTrain[count][j]+" ");
                    }
                    svmTrainData.write("\n");
                    count++;
                }
            }
            svmTrainData.close();
            statement.close();
            mysql.Dis_Connect();
            return "svmTrainData.txt";
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "svmTrainData.txt";
    }

    public String readTestData() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from steelplate";
            Object[][] DataTest;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            DataTest = new Object[columnCount/rate][7];
            try {
                FileWriter svmTestData = new FileWriter("svmTestData.txt");
                for (int i = 0; i < columnCount; ++i) {
                    if (i % rate == 1) {
                        String getDataQuery = ReadData.getSelectQuery(Name, "steelplate", i);
                        ResultSet selectTestOk;
                        selectTestOk = statement.executeQuery(getDataQuery);
                        selectTestOk.next();
                        for (int j = 0; j < 7; ++j) {
                            DataTest[i / rate][j] = selectTestOk.getObject((String) Name[j]);
                        }
                        if(atoi((String) DataTest[i / rate][DataTest[0].length-1])<6)
                            svmTestData.write("1 ");
                        else
                            svmTestData.write("0 ");
                        for (int j = 0; j < DataTest[0].length - 1; ++j) {
                            svmTestData.write(j + ":" + DataTest[i / rate][j] + " ");
                        }
                        svmTestData.write("\n");
                    }
                }
                svmTestData.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            statement.close();
            mysql.Dis_Connect();
            return "svmTestData.txt";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "svmTestData.txt";
    }
}
