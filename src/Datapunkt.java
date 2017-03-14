import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Datapunkt(ArrayList param) {
        this.treningsnr = Integer.valueOf((String)param.get(0));
        this.øvelsesnavn = (String) param.get(1);
        this.punktnr = Integer.valueOf((String)param.get(2));
        this.tid = Integer.valueOf((String)param.get(3));
        this.puls = Integer.valueOf((String)param.get(4));
        this.lengdegrad = (String) param.get(5);
        this.breddegrad = (String) param.get(6);
        this.høydeoverhavet = Integer.valueOf((String)param.get(7));
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
            String sql = "insert into treningsdagbok.datapunkt values (?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
