import java.sql.*;
import java.util.ArrayList;

/**
 * Created by taphan on 09.03.2017.
 */
public class Ovelse extends ActiveDomainObject {

    private String navn;
    private String beskrivelse;
    private String lignende;
    private Integer ovelsestype;

    public Ovelse(ArrayList ovelse) {
        this.navn = (String) ovelse.get(0);
        this.beskrivelse = (String) ovelse.get(1);
        this.lignende = (String) ovelse.get(2);
        this.ovelsestype = (Integer) ovelse.get(3);
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }

    @Override
    public void save(Connection conn) {
        try {
            String sql = "insert into treningsdagbok.øvelse values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // Parameters start with 1
            preparedStatement.setString(1, navn);
            preparedStatement.setString(2, beskrivelse);
            preparedStatement.setString(3, lignende);
            preparedStatement.setInt(4, ovelsestype);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
