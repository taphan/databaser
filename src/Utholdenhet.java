import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by taphan on 09.03.2017.
 */
public class Utholdenhet extends Resultat {

    Integer lengde;

    public Utholdenhet(Integer nr, String navn,  Integer lengde) {
        super(nr, navn);
        this.lengde = lengde;
    }


    @Override
    public void save(Connection connection) {
        super.save(connection);
        try {
            String sql = "insert into treningsdagbok.utholdenhet values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,treningsnr);
            preparedStatement.setString(2, ovelsesnavn);
            preparedStatement.setInt(3, lengde);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
