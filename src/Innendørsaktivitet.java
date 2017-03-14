import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 13.03.2017.
 */
public class Innendørsaktivitet extends Trening {

    private String ventilasjon;
    private Integer tilskuere;

    public Innendørsaktivitet(Integer treningsnr, String navn, String dato, Integer varighet, Integer form, Integer prestasjon, String formaal, String tips, Integer periodeid, String ventilasjon, Integer tilskuere) {
        super(treningsnr, navn, dato, varighet, form, prestasjon, formaal, tips, periodeid);
        this.ventilasjon = ventilasjon;
        this.tilskuere = tilskuere;
    }

    @Override
    public void save(Connection connection) {
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.innendørsaktivitet values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ventilasjon);
            preparedStatement.setInt(2,tilskuere);
            preparedStatement.setInt(3,treningsnr);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
