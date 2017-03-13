import java.sql.*;

/**
 * Created by taphan on 09.03.2017.
 */
public class Ovelse extends ActiveDomainObject {

    private String navn;
    private String beskrivelse;
    private String lignende;
    private String ovelsestype;

    public Ovelse(String navn, String beskrivelse, String lignende, String ovelsestype) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.lignende = lignende;
        this.ovelsestype = ovelsestype;
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
            preparedStatement.setString(4, ovelsestype);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
