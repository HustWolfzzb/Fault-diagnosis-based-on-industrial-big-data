
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.3-2018.5

 * Address  :   HUST

 * Version  :   4.0
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

    public String getSelectQuery(Object[] Name,String table,int id){
        String select = "SELECT  ";
        for (int i=0;i<33;++i){
            select += (Name[i]+",");
        }
        select += Name[33];
        select += " from " + table + " where id = "+id;
        return select;
    }

    public Object[][] readFromDatabase() {
        int columnCount=0;
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            String GETCOLUMN="select max(id) from plate";
            String GETDATA="";
            File file = new File("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/Mydata.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;  //一行数据
            //逐行读取，并将每个数组放入到数组中
            line=in.readLine();
            in.close();
            Object[] Name = line.split("\t");
            Object[][] DataToOut;
            ResultSet answer = statement.executeQuery(GETCOLUMN);
            if(answer.next())
                columnCount  = answer.getInt(1);
            DataToOut = new Object[columnCount][28];
            for (int  i = 0;i<columnCount;++i) {
                GETDATA = getSelectQuery(Name,"plate",i);
                ResultSet select_ok;
                select_ok = statement.executeQuery(GETDATA);
                select_ok.next();
                for (int j = 0; j<27;++j){
                    DataToOut[i][j]=select_ok.getObject((String) Name[j]);
                }
                for (int x=0;x<7;++x){
                    if("1".equals((String) select_ok.getObject((String) Name[x + 27]))){
                        DataToOut[i][27]=x;
                    }
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
        // ***************** 数据库读写式 **************
        WriteData write = new WriteData();
        write.WriteData();
        Object[][] DataToOut = readFromDatabase();
        return  DataToOut;
        // ***************** 数据库读写式 **************
    }
}
