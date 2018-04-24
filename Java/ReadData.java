
/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   5.0

 * 从数据库读取数据，并且从ReadData这个函数传出去！5.0加了训练集和验证集的划分
 ********************* */


/* *******************
* 这是从数据库或者是文本文件读取数据的时候用的
* 其实我觉得如果可以每一次读一条数据，然后处理一条会比较好
* 但是算了，数据量不大的话，这个样子也不会增加太多时间的！
******************* */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

public class ReadData {
    private Parameter par = new Parameter();
    private Object[] Name;
    private Mysql_Connect mysql=new Mysql_Connect();

    ReadData() {
        Name = new Object[]{"Sensor1","Sensor2","Sensor3","Sensor4","HZ", "category"};
    }

    public static String getSelectQuery(Object[] Name,String table,int id){
        String select = "SELECT  ";
        for (int i=0;i<Name.length-1;++i){
            select += (Name[i]+",");
        }
        select += Name[Name.length-1];
        select += " from " + table + " where id = "+id;
        return select;
    }

    public Object[][] readTrainData() {
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(0);
            int columnCount = par.getTrainNum();
            Object[][] dataToTrain;
            dataToTrain = new Object[columnCount][Name.length];
            for (int  i = 0;i<columnCount;++i) {
                String getDataQuery = getSelectQuery(Name,"gear",i*par.getTrainDistance());
                ResultSet select_ok;
                select_ok = statement.executeQuery(getDataQuery);
                select_ok.next();
                for (int j = 0; j < Name.length; ++j){
                    dataToTrain[i][j]=Float.parseFloat(nf.format(select_ok.getFloat((String) Name[j])));
                }
            }
            statement.close();
            mysql.Dis_Connect();
            return dataToTrain;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Object[1][1];
    }

    public Object[][] readTestData() {
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(0);
            int columnCount = par.getTestNum();
            Object[][] dataToTest;
            dataToTest = new Object[columnCount][Name.length];
            for (int  i = 0;i<columnCount;++i) {
                String getDataQuery = getSelectQuery(Name,"gear",i*par.getTestDistance()+1);
                ResultSet select_ok;
                select_ok = statement.executeQuery(getDataQuery);
                select_ok.next();
                for (int j = 0; j < Name.length; ++j){
                    dataToTest[i][j]=Float.parseFloat(nf.format(select_ok.getFloat((String) Name[j])));
                }
            }
            statement.close();
            mysql.Dis_Connect();
            return dataToTest;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[1][1];
    }
}
