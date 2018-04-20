
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   4.0

 * 从数据库读取数据，并且从ReadData这个函数传出去！
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

    private Object[] Name;
    protected Mysql_Connect mysql=new Mysql_Connect();

    ReadData() throws IOException{
        File file = new File("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/New_Data.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;  //一行数据作为属性名字
        line = in.readLine();
        in.close();
        Name = line.split("\t\t");
    }

    public String getSelectQuery(Object[] Name,String table,int id){
        String select = "SELECT  ";
        for (int i=0;i<Name.length-1;++i){
            select += (Name[i]+",");
        }
        select += Name[Name.length-1];
        select += " from " + table + " where id = "+id;
        return select;
    }

    public Object[][] readFromDatabase() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from steelplate";
            String getDataQuery="";
            Object[][] DataToOut;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            DataToOut = new Object[columnCount][7];
            for (int  i = 0;i<columnCount;++i) {
                getDataQuery = getSelectQuery(Name,"steelplate",i);
                ResultSet select_ok;
                select_ok = statement.executeQuery(getDataQuery);
                select_ok.next();
                for (int j = 0; j<7;++j){
                    DataToOut[i][j]=select_ok.getObject((String) Name[j]);
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

    public Object[][] readTrainData() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from steelplate";
            String getDataQuery="";
            Object[][] DataTrain;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            DataTrain = new Object[columnCount/2][7];
            for (int  i = 0;i<columnCount/2;++i) {
                getDataQuery = getSelectQuery(Name,"steelplate",i*2);
                ResultSet select_ok;
                select_ok = statement.executeQuery(getDataQuery);
                select_ok.next();
                for (int j = 0; j<7;++j){
                    DataTrain[i][j]=select_ok.getObject((String) Name[j]);
                }
            }
            statement.close();
            mysql.Dis_Connect();
            return DataTrain;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[1][1];
    }

    public Object[][] readTestData() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from steelplate";
            Object[][] DataTest;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            DataTest = new Object[columnCount/2][7];
            for (int  i = 0 ;i<columnCount/2-1;++i) {
                String getDataQuery = getSelectQuery(Name,"steelplate",i*2+1);
                ResultSet select_ok;
                select_ok = statement.executeQuery(getDataQuery);
                select_ok.next();
                for (int j = 0; j<7;++j){
                    DataTest[i][j]=select_ok.getObject((String) Name[j]);
                }
            }
            statement.close();
            mysql.Dis_Connect();
            return DataTest;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[1][1];
    }

    public Object[][] ReadData() throws IOException {
        // ***************** 数据库读写式 **************
//        WriteData write = new WriteData();
//        write.WriteData();
        Object[][] DataToOut = readFromDatabase();
        return  DataToOut;
        // ***************** 数据库读写式 **************
    }
}
