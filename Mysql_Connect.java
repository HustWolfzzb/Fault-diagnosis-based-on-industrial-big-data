import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Connect {

    //此处查看网络才知道。要求SSL，所以就酱紫咯：https://zhidao.baidu.com/question/2056521203295428667.html
    private static String url = "jdbc:mysql://127.0.0.1:3306/Graduation_Design?useUnicode=true&characterEncoding=GBK&useSSL=true";

    private static String user = "root";

    private static String password = "zzb1184827350";

    private static String Database="192.168.2.127:3306/Graduation_Design";

    private Statement statement;

    private Connection conn;

    public void setUser(String user){
        this.user=user;
    }
    public  void setPassword(String p){
        this.password=p;
    }
    public void setDatabase(String database){
        this.Database=database;
        this.url="jdbc:mysql:/"+Database+"?useUnicode=true&characterEncoding=GBK&useSSL=true";
    }
    public void setDatabase(){
        this.url="jdbc:mysql:/192.168.2.127:3306/Shop_User?useUnicode=true&characterEncoding=GBK&useSSL=true";
    }
    public Statement getStatement() {
        return this.statement;
    }

    public void Connect() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if (!conn.isClosed()){

            }
            else {
                System.out.println("\n\nFailed to connect to the Database!");
            }
            this.statement = conn.createStatement();

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Dis_Connect() throws SQLException {
        try {
            conn.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}