import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by taphan on 13.03.2017.
 */
public class Utendørsaktivitet extends Trening {

    private Double temperatur;
    private String vaertype;

    public Utendørsaktivitet(ArrayList trening){
        super(Integer.valueOf((String)trening.get(0)),Integer.valueOf((String)trening.get(1)), (String) trening.get(2),
                (String) trening.get(3),Integer.valueOf((String)trening.get(4)),Integer.valueOf((String)trening.get(5)),
                Integer.valueOf((String)trening.get(6)), (String)trening.get(7), (String)trening.get(8));
        this.temperatur = Double.parseDouble((String) trening.get(9));
        this.vaertype = (String) trening.get(10);
    }

    @Override
    public void save(Connection connection) {
        super.save(connection);
        try {
            // Legg nye data inn i databasen
            String sql = "insert into treningsdagbok.utendørsaktivitet values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,temperatur);
            preparedStatement.setString(2,vaertype);
            preparedStatement.setInt(3,treningsnr);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
