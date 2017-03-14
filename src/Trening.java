import org.omg.CORBA.INTERNAL;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by taphan on 09.03.2017.
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

    protected Trening(Integer treningsnr, String navn, String dato, Integer varighet, Integer form, Integer prestasjon, String formaal, String tips, Integer periodeid) {
        this.treningsnr = treningsnr;
        this.navn = navn;
        this.dato = dato;
        this.varighet = varighet;
        this.form = form;
        this.prestasjon = prestasjon;
        this.formaal = formaal;
        this.tips = tips;
        this.periodeid = periodeid;
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
            preparedStatement.setInt(1,treningsnr);
            preparedStatement.setString(2,navn);
            preparedStatement.setString(3,dato);
            preparedStatement.setInt(4,varighet);
            preparedStatement.setInt(5,form);
            preparedStatement.setInt(6,prestasjon);
            preparedStatement.setString(7,formaal);
            preparedStatement.setString(8,tips);
            preparedStatement.setInt(9,periodeid);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
