import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by taphan on 09.03.2017.
 */
public class Styrke extends Resultat {

    private Integer belastning;
    private Integer repetisjon;
    private Integer sett;

    public Styrke(Integer nr, String navn, Integer belastning, Integer repetisjon, Integer sett) {
        super(nr, navn);
        this.belastning = belastning;
        this.repetisjon = repetisjon;
        this.sett = sett;
    }

    @Override
    public void save(Connection connection) {
        super.save(connection);
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.styrke values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,treningsnr);
            preparedStatement.setString(2, ovelsesnavn);
            preparedStatement.setInt(3, belastning);
            preparedStatement.setInt(4, repetisjon);
            preparedStatement.setInt(5, sett);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
