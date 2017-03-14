import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by taphan on 13.03.2017.
 */
public class Utendørsaktivitet extends Trening {

    private Float temperatur;
    private String vaertype;

    public Utendørsaktivitet(ArrayList trening){
        super((Integer) trening.get(0), (String) trening.get(1), (String) trening.get(2), (Integer)trening.get(3),
                (Integer) trening.get(4), (Integer)trening.get(5), (String)trening.get(6), (String)trening.get(7), (Integer)trening.get(8));
        this.temperatur = (Float) trening.get(9);
        this.vaertype = (String) trening.get(10);
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
