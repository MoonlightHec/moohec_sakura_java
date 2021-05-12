package study;


import java.sql.*;

/**
 * @author 作者 lijun:
 * @Date 2021年02月08日 14:36:47
 * @ClassName JDBCConn
 * @Description TODO
 * @Version 1.0
 */
public class JDBCConn {
    private JDBCConn() {
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        System.out.println("myStmt:");
        myStmt();

        System.out.println("\nmyPreStmt:");
        myPreStmt();
    }

    /**
     * @param
     * @return void
     * @Author lijun
     * @Date 2021年02月17日 11:40:18
     * @Description 使用Statement执行sql
     */
    public static void myStmt() throws ClassNotFoundException, SQLException {
        // 1.注册一个驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2.获得一个数据库连接
        // 连接url：jdbc:mysql://主机ip地址：端口/数据库名
        String url = "jdbc:mysql://192.168.0.202:3306/plm_new";
        String user = "plm_all";
        String password = "plm_all_pw";
        Connection conn = DriverManager.getConnection(url, user, password);
        // 3.准备sql语句
        String sql = "SELECT product_code,color,goods_name FROM t_sample_color_change_info WHERE product_code = '2061242';";
        // 4.陈述对象
        Statement stmt = conn.createStatement();
        // 5.1.语句执行
        //stmt.execute(sql);
        // 5.2.查询语句要特殊点，使用executeQuery，返回的是结果集
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String product_code = rs.getString(1);
            String color = rs.getString(2);
            String goods_name = rs.getString(3);
            System.out.println(String.format("product_code:[%s] color:[%s] goods_name:[%s]", product_code, color, goods_name));
        }
        // 6.资源的关闭
        stmt.close();
        conn.close();
    }

    /**
     * @param
     * @return void
     * @Author lijun
     * @Date 2021年02月17日 11:40:58
     * @Description 使用PreparedStatement预编译
     */
    public static void myPreStmt() throws ClassNotFoundException, SQLException {
        // 1.注册一个驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2.获得一个数据库连接
        // 连接url：jdbc:mysql://主机ip地址：端口/数据库名
        String url = "jdbc:mysql://192.168.0.202:3306/plm_new";
        String user = "plm_all";
        String password = "plm_all_pw";
        Connection conn = DriverManager.getConnection(url, user, password);
        // ?占位符，先预编译，后赋值
        String site = "ep";
        String sql = "SELECT product_code,color,goods_name,'" + site + "' FROM t_sample_color_change_info WHERE product_code = ? AND color = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        //与sql语句的?对应
        stmt.setString(1, "2061242");
        stmt.setString(2, "月牙白");

        //语句执行，已经预编译了，不需要再传入sql语句了
        ResultSet rs = stmt.executeQuery();
        // 5.2.查询语句要特殊点，使用executeQuery，返回的是结果集
//        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String product_code = rs.getString(1);
            String color = rs.getString(2);
            String goods_name = rs.getString(3);
            System.out.println(String.format("product_code:[%s] color:[%s] goods_name:[%s] [%s]", product_code, color, goods_name, rs.getString(4)));
        }
        // 6.资源的关闭
        stmt.close();
        conn.close();
    }
}
