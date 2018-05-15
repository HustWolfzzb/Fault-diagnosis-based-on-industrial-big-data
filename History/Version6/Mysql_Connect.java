/* *********************
 * Author   :   HustWolf --- 张照博

 * Time     :   2018.1-2018.5

 * Address  :   HUST

 * Version  :   1.0

 * 封装数据库连接，会话，断开三个功能
 ********************* */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Connect {

    //此处查看网络才知道。要求SSL，所以就酱紫咯：https://zhidao.baidu.com/question/2056521203295428667.html
    private static String url = "jdbc:mysql://127.0.0.1:3306/Graduation_Design?useUnicode=true&characterEncoding=GBK&useSSL=true";
//    private static String url = "jdbc:mysql://159.203.250.111:3306/GD?useUnicode=true&characterEncoding=GBK&useSSL=true";

    private static String user = "root";

    private static String password = "zzb1184827350";
//    private static String password = "zzb162122";

    private Statement statement;

    private Connection conn;

    public Connection getConnection() { return this.conn;}

    public Statement getStatement() {
        try {
            this.statement = this.conn.createStatement();;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.statement;
    }

    public void Connect() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if (!conn.isClosed()){
//                System.out.println("Connect Success!");
            }
            else {
                System.out.println("\n\nFailed to connect to the Database!");
            }
//            this.statement = conn.createStatement();

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