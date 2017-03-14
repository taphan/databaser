import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 13.03.2017.
 */
public class Periode extends ActiveDomainObject {

    private Integer periodeId;
    private String fraDato;
    private String tilDato;

    public Periode(Integer periodeId, String fraDato, String tilDato) {
        this.periodeId = periodeId;
        this.fraDato = fraDato;
        this.tilDato = tilDato;
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }

    @Override
    public void save(Connection connection) {
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.periode values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,periodeId);
            preparedStatement.setString(2, fraDato);
            preparedStatement.setString(3,tilDato);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
