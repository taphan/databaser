/**
 * Created by taphan on 08.03.2017.
 */
public class MainApp {

    public static void main(String[] args) {
        // Spør brukeren om å velge et tall og kall deretter chooseActivity(input fra bruker)
        // while scanner.next.....
        MainApp main = new MainApp();
        DBController controller = new DBController();
        controller.connect();

    }

    public void chooseActivity(Integer choice) {
        /* 1: kaller addToDatabase
        2: kaller viewRapport
        3: kaller viewStatistics*/
    }

    /**
     * Add data to database
     */
    public void addToDatabase() {

    }

    /**
     * Uthenting av rapport av den beste treningen den siste uka for hver type av trening. Hva som er kriteriet for «best» må dere bestemme selv.
     */
    public void viewRapport() {

    }

    /**
     * Uthenting av statistikk fra den siste måneden (antall økter, antall timer og annet)
     */
    public void viewStatistics(Connection connection) {
        try{
            Statement statement = conn.createStatement();
            String query = "SELECT Trening.Dato,Trening.Navn,Trening.Varighet, " +
                    "count(UtførØvelse.Øvelsesnavn) as Antall_øvelser " +
                    "FROM UtførØvelse JOIN Trening on UtførØvelse.Treningsnr = Trening.Treningsnr " +
                    "WHERE Trening.Dato > (NOW() - INTERVAL 1 MONTH) " +
                    "JOIN Øvelse on UtførØvelse.Øvelsesnavn = Øvelse.Øvelsesnavn " +
                    "GROUP BY UtførØvelse.Treningsnr " +
                    "ORDER BY Trening.Dato;";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Dette er statistikk av treningene den siste måneden:");
            if (resultSet.next()) {   // Hvis det er flere rader i tabellen
                // Format the print message.
                dato =  rs.getString("Dato");  // getString(column) gets the result in column Dato
                navn = rs.getString("Navn");
                varighet =  rs.getString("Varighet");
                antall_ovelser =  rs.getString("Antall Øvelser");
                System.out.println("[ Dato: " + dato + ", treningsnavn: " + navn + ", varighet: "
                                  + varighet + ", antall øvelser: " + antall_ovelser + " ]");
            }
        }
        catch (Exception e) {
            System.out.println("DB Error when trying to show last month's statistics: "+ e);
        }
    }
}
