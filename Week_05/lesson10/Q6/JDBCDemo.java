package lesson10.Q6;

import java.sql.ResultSet;
import java.sql.Statement;

import static lesson10.Q6.JDBCHelper.release;
import static lesson10.Q6.JDBCHelper.getStatement;

/**
 * JDBC 基本实现
 */
public class JDBCDemo {

    public static void main(String[] args) throws Exception {
        Statement statement = getStatement();
        // select
        String sql = "select id,name from person limit 10";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("id:" + id + "\t\tname: " + name);

        }

        // UPDATA
        sql = "update person set name='test' where id=1";
        int rows = statement.executeUpdate(sql);
//        if (rows > 0) {
//            System.out.println("更新成功...");
//        }
        // insert
        sql = "insert into person (name,score) values (concat('cxq',last_insert_id()), 100);";
        boolean result = statement.execute(sql);
//        if (result) {
//            System.out.println("新增数据成功");
//        }
        // DELETE
        sql = "delete from person where id=1";
        result = statement.execute(sql);

        // 关闭资源
        release(resultSet, statement);


    }
}
