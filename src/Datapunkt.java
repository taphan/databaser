import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by taphan on 09.03.2017.
 */
public class Datapunkt extends ActiveDomainObject{

    private Integer treningsnr;
    private String øvelsesnavn;
    private Integer punktnr;
    private Integer tid;
    private Integer puls;
    private String lengdegrad;
    private String breddegrad;
    private Integer høydeoverhavet;

    public Datapunkt(Integer treningsnr,String øvelsesnavn, Integer punktnr,Integer tid, Integer puls, String lengdegrad, String breddegrad, Integer høydeoverhavet) {
        this.treningsnr = treningsnr;
        this.øvelsesnavn = øvelsesnavn;
        this.punktnr = punktnr;
        this.tid = tid;
        this.puls = puls;
        this.lengdegrad = lengdegrad;
        this.breddegrad = breddegrad;
        this.høydeoverhavet = høydeoverhavet;
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
            String sql = "insert into treningsdagbok.øvelse values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // Parameters start with 1
            preparedStatement.setInt(1, treningsnr);
            preparedStatement.setString(2, øvelsesnavn);
            preparedStatement.setInt(3, punktnr);
            preparedStatement.setInt(4, tid);
            preparedStatement.setInt(5, puls);
            preparedStatement.setString(6, lengdegrad);
            preparedStatement.setString(7, breddegrad);
            preparedStatement.setInt(8, høydeoverhavet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
