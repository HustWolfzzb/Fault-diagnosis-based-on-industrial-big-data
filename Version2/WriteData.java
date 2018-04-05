import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WriteData {
    protected Mysql_Connect mysql=new Mysql_Connect();

    public String getInsertQuery(int id,Object[] name,Object[] value){
        String insert="INSERT INTO plate(id";
        for (int i=0;i<name.length;++i){
            insert+=(","+name[i]);
        }
        insert+=") values("+id;
        for (int i=0;i<value.length;++i){
            insert+=(",'"+value[i]+"'");
        }
        insert+=");";
        return insert;
    }


    public void WriteData() throws IOException {
        try {
            mysql.Connect();
            Statement statement = mysql.getStatement();
            String DELETE = "delete from plate where id<100000";
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
        File file = new File("/Users/zhangzhaobo/IdeaProjects/Graduation_Design/src/Mydata.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;  //一行数据
        //逐行读取，并将每个数组放入到数组中
        line=in.readLine();
        int id=0;
        Object[] Name = line.split("\t");
        try {
            mysql.Connect();
            Statement statement=mysql.getStatement();
//            Object[] name = new Object[] {"X_Minimum","X_Maximum","Y_Minimum","Y_Maximum","Pixels_Areas","X_Perimeter","Y_Perimeter","Sum_of_Luminosity","Minimum_of_Luminosity","Maximum_of_Luminosity","Length_of_Conveyer","TypeOfSteel_A300","TypeOfSteel_A400","Steel_Plate_Thickness","Edges_Index","Empty_Index","Square_Index","Outside_X_Index","Edges_X_Index","Edges_Y_Index","Outside_Global_Index","LogOfAreas","Log_X_Index","Log_Y_Index","Orientation_Index","Luminosity_Index","SigmoidOfAreas","Pastry","Z_Scratch","K_Scatch","Stains","Dirtiness","Bumps","Other_Faults"};
//            Object[] value = new Object[]{"42","50","270900","270944","267","17","44","24220","76","108","1687","1","0","80","0.0498","0.2415","0.1818","0.0047","0.4706","1","1","2.4265","0.9031","1.6435","0.8182","-0.2913","0.5822","1","0","0","0","0","0","0"};
//            System.out.println(getInsertQuery(0,name,value));
            while((line = in.readLine()) != null) {
                Object[] Data_Array = line.split("\t");
                String INSERT = getInsertQuery(id, Name, Data_Array);
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
        in.close();
    }
}
