import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by taphan on 09.03.2017.
 */
public class DBController {
    private static final String url = "jdbc:mysql://localhost/treningsdagbok";

    private static final String user = "admin";

    private static final String password = "database";

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
