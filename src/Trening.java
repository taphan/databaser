import org.omg.CORBA.INTERNAL;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 09.03.2017.
 * Denne klassen er avhengig av Innendørs- og Utendørsaktivitet og blir aldri instansiert in MainApp.
 */

public class Trening extends ActiveDomainObject{

    protected Integer treningsnr;
    protected String navn;
    protected String dato;
    protected Integer varighet;
    protected Integer form;
    protected Integer prestasjon;
    protected String formaal;
    protected String tips;
    protected Integer periodeid;

    protected Trening(Integer periodeid,Integer treningsnr, String navn, String dato, Integer varighet, Integer form, Integer prestasjon, String formaal, String tips) {
        this.periodeid = periodeid;
        this.treningsnr = treningsnr;
        this.navn = navn;
        this.dato = dato;
        this.varighet = varighet;
        this.form = form;
        this.prestasjon = prestasjon;
        this.formaal = formaal;
        this.tips = tips;
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
            String sql = "insert into treningsdagbok.trening values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,periodeid);
            preparedStatement.setInt(2,treningsnr);
            preparedStatement.setString(3,navn);
            preparedStatement.setString(4,dato);
            preparedStatement.setInt(5,varighet);
            preparedStatement.setInt(6,form);
            preparedStatement.setInt(7,prestasjon);
            preparedStatement.setString(8,formaal);
            preparedStatement.setString(9,tips);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
