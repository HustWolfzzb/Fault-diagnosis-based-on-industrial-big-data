/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.0

 * 将两百万条记录清洗后写入到数据库中
 ********************* */
import java.io.*;
import java.text.NumberFormat;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class WriteGearData {
    protected Mysql_Connect mysql=new Mysql_Connect();


    public void Delete(int max){
        try {
            mysql.Connect();
            Statement statement = mysql.getStatement();
            String DELETE = "delete from gear where id<"+max;
            boolean delete_ok = statement.execute(DELETE);
            if (delete_ok) {
                System.out.println("Have Fun! Boys!\n\n");
            }
            statement.close();
            mysql.Dis_Connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getInsertQuery(int id,Object[] name,Object[] value){
        String insert="INSERT INTO gear(id";
        for (int i=0;i<name.length;++i){
            insert+=(","+name[i]);
        }
        insert+=") values("+id;
        for (int i=0;i<value.length;++i){
            insert+=(","+value[i]+"");
        }
        insert+=");";
        return insert;
    }

    public void WriteData() throws IOException {
//        Delete(100000);
        int hz[] = new int [] {0,10,20,30,40,50,60,70,80,90};
//        int id = 1005311;
        int id = 0;
        for (int  x : hz) {
            String filename = "/Users/zhangzhaobo/Documents/Graduation-Design/Data/BrokenTooth Data/b30hz" + x + ".txt";
            Scanner scanner=new Scanner(new FileInputStream(filename));
            Object[] Name = new String[]{"Sensor1","Sensor2","Sensor3","Sensor4","HZ","category"};
            int count = 0;
            try {
                mysql.Connect();
                Statement statement = mysql.getStatement();
                do{
                    //连续添加多条静态SQL
                    Object [] line = new Object[6];
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(1);
                    for (int i=0;i<4;++i){
                        line[i]=Float.parseFloat(nf.format(scanner.nextFloat()));
                    }
                    line[4] = x;
                    line[5] = 1;
                    String INSERT = getInsertQuery(id, Name, line);
                    statement.addBatch(INSERT);
                    id++;
                    count++;
                    //执行批量执行
                    if (count>40000) {
                        statement.executeBatch();
                        count = 0;
                    }
                }while(scanner.hasNext());
                statement.executeBatch();
                statement.close();
                mysql.Dis_Connect();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}