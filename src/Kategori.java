import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 09.03.2017.
 */
public class Kategori extends ActiveDomainObject{

    String navn;
    protected Kategori(String navn) {
        this.navn = navn;
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }


    public void save(Connection connection) {
        // For å unngå constraint key error må opprette kategori først
        try {
            String sql = "insert into treningsdagbok.kategori values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, navn);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
