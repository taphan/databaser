import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by taphan on 09.03.2017.
 */
public class Utholdenhet extends Resultat {

    private Integer lengde;

    public Utholdenhet(ArrayList param) {
        super(Integer.valueOf((String )param.get(0)),(String) param.get(1));
        this.lengde = Integer.valueOf((String)param.get(2));
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
