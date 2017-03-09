package example; /**
 * Created by taphan on 08.03.2017.
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {
    private static final String url = "jdbc:mysql://localhost/treningsdagbok";

    private static final String user = "admin";

    private static final String password = "database";

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
