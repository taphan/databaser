import java.sql.Connection;
import java.sql.DriverManager;

public class DBController {
	public Connection con;
    private static final String url = "jdbc:mysql://localhost/treningsdagbok";

    private static final String user = "admin";

    private static final String password = "database";
  
    protected void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            //System.out.println("Success");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
