import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 09.03.2017.
 */
public class Styrke extends Ovelse {

    Integer belastning;
    Integer repetisjon;
    Integer sett;

    public Styrke(String navn, String beskrivelse, String lignende, String resultat, Integer belastning, Integer repetisjon, Integer sett) {
        super(navn, beskrivelse, lignende, resultat);
        this.belastning = belastning;
        this.repetisjon = repetisjon;
        this.sett = sett;
    }

    @Override
    public void save(Connection connection) {
        super.save(connection);
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.styrke values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,navn);
            preparedStatement.setInt(2, belastning);
            preparedStatement.setInt(3, repetisjon);
            preparedStatement.setInt(4, sett);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
