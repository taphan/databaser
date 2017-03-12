import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by taphan on 09.03.2017.
 */
public class Utholdenhet extends Ovelse {

    Integer lengde;

    public Utholdenhet(String navn,String beskrivelse, String lignende, String resultat, Integer lengde) {
        super(navn, beskrivelse, lignende, resultat);
        this.lengde = lengde;
    }


    @Override
    public void save(Connection connection) {
        super.save(connection);
        try {
            String sql = "insert into treningsdagbok.utholdenhet values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,lengde);
            preparedStatement.setString(2, navn);
            preparedStatement.executeUpdate();

            /*Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from utholdenhet");
            writeResultSet(rs);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeResultSet(ResultSet rs) throws SQLException{
        while(rs.next()) {
            String name = rs.getString("Kategorinavn");
            Integer length = rs.getInt("Lengde");
            System.out.println("Navn: " + name);
            System.out.println("Lengde: " + length);
        }
    }
}
