package lesson10.Q6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

import static lesson10.Q6.JDBCHelper.release;
import static lesson10.Q6.JDBCHelper.getConnection;

/**
 * JDBC Demo
 * 批处理,PreparedStatement,手动提交事务
 */
public class JDBCWithPrepareStatementAndBatchAndTxDemo {

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        String insertSql = "insert into person (name,score) values (concat('cxq',last_insert_id()), ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        for (int i = 0; i < 300; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.addBatch();
        }    
        int[] rowArray = preparedStatement.executeBatch();
        connection.commit();
        System.out.println(Arrays.toString(rowArray));
        release(null, preparedStatement);
    }
}
