import java.sql.Connection;
import java.sql.DriverManager;

public class DBController {
	public Connection con;
    private static final String url = "jdbc:mysql://localhost/treningsdagbok";

    private static final String user = "admin";

    private static final String password = "database";
    protected Connection con;

    protected void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
<<<<<<< HEAD
            this.con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
=======
            con = DriverManager.getConnection(url, user, password);
            //System.out.println("Success");
>>>>>>> bc125b78c2391bc35e080841a4cb4d0b43e255c0

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
