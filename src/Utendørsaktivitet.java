import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 13.03.2017.
 */
public class Utendørsaktivitet extends Trening {

    private Float temperatur;
    private String vaertype;

    public Utendørsaktivitet(Integer treningsnr, String navn, String dato, Integer varighet, Integer form, Integer prestasjon, String formaal, String tips, Integer periodeid, Float temperatur, String vaertype) {
        super(treningsnr, navn, dato, varighet, form, prestasjon, formaal, tips, periodeid);
        this.temperatur = temperatur;
        this.vaertype = vaertype;
    }

    @Override
    public void save(Connection connection) {
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.utendørsaktivitet values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1,temperatur);
            preparedStatement.setString(2,vaertype);
            preparedStatement.setInt(3,treningsnr);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
