/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.1

 * 将两百万条记录清洗后写入到数据库中

 * 1.1 版本更正了属性名字 HZ-->Load
 ********************* */
import java.io.*;
import java.text.NumberFormat;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class WriteGearData {
    protected Mysql_Connect mysql=new Mysql_Connect();
    private Object[] Name = new String[]{"Sensor1","Sensor2","Sensor3","Sensor4","Load","category"};


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
            insert+= (",`"+(name[i]+"`"));
        }
        insert+=") values("+id;
        for (int i=0;i<value.length;++i){
            insert+=(","+value[i]+"");
        }
        insert+=");";
        return insert;
    }

    public void WriteData() throws IOException {
        Delete(3000000);
        int load[] = new int [] {0,10,20,30,40,50,60,70,80,90};
//        int id = 1005311;
        int id = 0;
        String [] FileNames = new String[]{"/Users/zhangzhaobo/Documents/Graduation-Design/Data/BrokenTooth Data/b30hz","/Users/zhangzhaobo/Documents/Graduation-Design/Data/Healthy Data/h30hz"};
        for(int fileindex = 0;fileindex <FileNames.length;++fileindex ) {
            for (int x : load) {
                String filename = FileNames[fileindex] + x + ".txt";
                Scanner scanner = new Scanner(new FileInputStream(filename));
                int count = 0;
                try {
                    mysql.Connect();
                    Statement statement = mysql.getStatement();
                    do {
                        //连续添加多条静态SQL
                        Object[] line = new Object[6];
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMaximumFractionDigits(4);
                        for (int i = 0; i < 4; ++i) {
                            line[i] = Float.parseFloat(nf.format(scanner.nextFloat()));
                        }
                        line[4] = x;
                        line[5] = fileindex;
                        String INSERT = getInsertQuery(id, Name, line);
                        statement.addBatch(INSERT);
                        id++;
                        count++;
                        //执行批量执行
                        if (count > 40000) {
                            statement.executeBatch();
                            count = 0;
                            System.out.println("写入了一次40000条");
                        }
                    } while (scanner.hasNext());
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
}