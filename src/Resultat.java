import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 13.03.2017.
 */
public class Resultat extends ActiveDomainObject {

    protected Integer treningsnr;
    protected String ovelsesnavn;

    public Resultat(Integer nr, String navn) {
        treningsnr = nr;
        ovelsesnavn = navn;
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }


    public void save(Connection connection) {
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.resultat values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,treningsnr);
            preparedStatement.setString(2,ovelsesnavn);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
