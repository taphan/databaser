import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by taphan on 08.03.2017.
 */
public class MainApp extends DBController{

	public static long DAY_IN_MS = 1000 * 60 * 60 * 24;
	
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
    	Date longDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    	@SuppressWarnings("deprecation")
		Date shortDate =new Date(longDate.getYear(),longDate.getMonth(),longDate.getDay());
    	
    try{
    	Statement stmt = con.createStatement();
    	String query = ("SELECT Trening.Navn, Dato, Øvelse.Øvelsesnavn, "+
    					"Form, Prestasjon, Resultat FROM Trening "+
    					"INNER JOIN Øvelse "+
    					"ON Trening.Øvelsesnavn = Øvelse.Øvelsesnavn "+
    					"WHERE Dato >='"+ shortDate +"' "+
    					"GROUP BY(Treningsnr) "+
    					"ORDER BY Sum(Form + Prestasjon) DESC; ");
    	
    	ResultSet rs = stmt.executeQuery(query);
    	int nr = 1;
    	while(rs.next()){
    		System.out.println("Nr"+ nr++ +": " + rs.getString("Navn") +" "+ rs.getDate("Dato") +" "+
    				rs.getString("Øvelse.Øvelsesnavn") +" Form:"+ rs.getInt("Form")+" Prestasjon: "+
    				rs.getInt("Prestasjon")+" Resultat: "+ rs.getString("Resultat"));
    	}
    	
    }
    catch (Exception e) {
		System.out.println("Error handeling dbRequest");
	}
    	
    	
    	
    }

    /**
     * Uthenting av statistikk fra den siste måneden (antall økter, antall timer og annet)
     */
    public void viewStatistics() {

    }
}
