import java.sql.*;

/**
 * Created by taphan on 09.03.2017.
 */
public class Ovelse extends ActiveDomainObject {

    protected String navn;
    protected String beskrivelse;
    protected String lignende;
    protected String resultat;

    protected Ovelse(String navn, String beskrivelse, String lignende, String resultat) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.lignende = lignende;
        this.resultat = resultat;
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }

    @Override
    public void save(Connection conn){
        try {
            String sql = "insert into treningsdagbok.øvelse values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // Parameters start with 1
            preparedStatement.setString(1, navn);
            preparedStatement.setString(2, beskrivelse);
            preparedStatement.setString(3, lignende);
            preparedStatement.setString(4, resultat);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    public void printOvelse(Connection connection) throws SQLException{
        Statement stmt = connection.createStatement();
        String query = "select * from øvelse";
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Id Name    Job");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String job = rs.getString("job");
            System.out.println(id + "  " + name + "   " + job);
        }
    }*/
}
