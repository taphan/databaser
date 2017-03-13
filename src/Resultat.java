import java.sql.Connection;

/**
 * Created by taphan on 13.03.2017.
 */
public class Resultat extends ActiveDomainObject {

    protected Integer treningsnr;
    protected String ovelsesnavn;

    public Resultat(Integer nr, String navn) {
        treningsnr = nr;
        ovelsesnavn = navn;
    }

    @Override
    public void initialize(Connection conn) {

    }

    @Override
    public void refresh(Connection conn) {
        initialize(conn);
    }


    public void save(Connection conn) {

    }
}
