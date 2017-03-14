import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by taphan on 09.03.2017.
 */
public class Styrke extends Resultat {

    private Integer belastning;
    private Integer repetisjon;
    private Integer sett;

    public Styrke(ArrayList styrke) {
        super((Integer) styrke.get(0), (String) styrke.get(1));
        this.belastning = (Integer)styrke.get(2);
        this.repetisjon = (Integer) styrke.get(3);
        this.sett = (Integer) styrke.get(4);
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
