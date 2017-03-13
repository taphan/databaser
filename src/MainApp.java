import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class MainApp extends DBController{

	public static long DAY_IN_MS = 1000 * 60 * 60 * 24;
    public MainApp() {
        connect();
    }
    private void chooseActivity() {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Velg mellom alternativene ved å taste det tilhørende tallet og trykk Enter:");
    	System.out.println("1: Legg trening til database");
    	System.out.println("2: Viser statistikk de beste treningsøktene den siste måneden.");
    	System.out.println("3: Viser statistikk fra den siste måneden (antall økter, antall timer og annet)");
        while(sc.hasNext()) {
            int valg = sc.nextInt();
            if(valg == 1) {
                addToDatabase();
            } else if(valg == 2) {
                viewRapport();
            } else if(valg == 3) {
                viewStatistics();
            } else {
                System.out.println("Tast inn et tall mellom 1-3.");
            }

        }
        sc.close();
    }

    /**
     * Add data to database
     */
    private void addToDatabase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Velg mellom kategoriene for å legge inn en øvelse:");
        System.out.println("1: Kondisjon og styrke");
        System.out.println("2: Utholdenhet");
        Integer choice = scanner.nextInt();
        if(choice == 1) {
        } else if(choice == 2) {
            Utholdenhet utholdenhet = new Utholdenhet("løping", "beskrivelse", "lignende","resultat", 10);
            utholdenhet.save(con);
        }

        //Ovelse ov = new Ovelse("navn", "beskrivelse", "lignende øvelse", "resultat");
        //ov.save(con);

        scanner.close();
    }

    /**
     * Uthenting av rapport av den beste treningen den siste uka for hver type av trening. Hva som er kriteriet for «best» må dere bestemme selv.
     */

    public void viewRapport() {
    	Date longDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    	@SuppressWarnings("deprecation")
		Date shortDate =new Date(longDate.getYear(),longDate.getMonth(),longDate.getDay());
    	
	    try{
	    	String treningsnr = null;
	    	Statement stmt = con.createStatement();
	    	String query1 = "select * from Trening " + 
	    					"where Dato >='"+shortDate+"' "+
	    					"group by(Treningsnr) " + 
	    					"order by sum(Form + Prestasjon) desc;";
	    /**
	      * Rangere treningene etter beste trening, printer deretter alle øvelsene i treningsøken
	      * Stykeøvelser printes først, deretter kondisjonøvelser. 
	      */
	    	
	    	ResultSet rsBesteTrening = stmt.executeQuery(query1);
	    	int nr = 1;
	    	while(rsBesteTrening.next()){
	    		treningsnr = rsBesteTrening.getString("Treningsnr");
	    		System.out.println("Nr"+ nr++ +": " + rsBesteTrening.getString("Navn") +" "+ 
	    				rsBesteTrening.getByte("Varighet") +" "+ rsBesteTrening.getDate("Dato")+
	    				" Form:"+ rsBesteTrening.getInt("Form")+" Prestasjon: "+ rsBesteTrening.getInt("Prestasjon") + 
	    				" Tips: " + rsBesteTrening.getString("TreningsTips"));
	    		
	    		String query2 = "select Øvelse.Øvelsesnavn, Belastning, " +
	    						"AntallRep, AntallSet from Trening " +
			    				"inner join Resultat " +
			    				"on Trening.Treningsnr = Resultat.Treningsnr " + 
			    				"inner join Øvelse " + 
			    				"on Resultat.Øvelsesnavn = Øvelse.Øvelsesnavn " + 
			    				"inner join Styrke " +
			    				"on Trening.Treningsnr = Styrke.Treningsnr " +
			    				"and Øvelse.Øvelsesnavn = Styrke.Øvelsesnavn " +
			    				"where Øvelse.ØvelsesType = 1 "+
			    				"and Dato >='"+shortDate+"' "+
			    				"and Trening.Treningsnr = "+treningsnr+";";
	    		
	    		String query3 = "select Øvelse.Øvelsesnavn, Lengde from Trening " +
			    				"inner join Resultat " +
			    				"on Trening.Treningsnr = Resultat.Treningsnr " + 
			    				"inner join Øvelse " + 
			    				"on Resultat.Øvelsesnavn = Øvelse.Øvelsesnavn " + 
			    				"inner join Kondisjon " +
			    				"on Trening.Treningsnr = Kondisjon.Treningsnr " +
			    				"and Øvelse.Øvelsesnavn = Kondisjon.Øvelsesnavn " +
			    				"where Øvelse.ØvelsesType = 2 "+
			    				"and Dato >='"+shortDate+"' "+
			    				"and Trening.Treningsnr = "+treningsnr+";";
	    		
	    		
	    		ResultSet rsStyrke = stmt.executeQuery(query2);
	    		System.out.println("	Styrkeøvelser: ");
	    		while(rsStyrke.next()){
	    			System.out.println("	"+rsStyrke.getString("Øvelse.Øvelsesnavn") +" Belastning: "+rsStyrke.getInt("Belastning") +
	    					" Antall repitisjoner: "+rsStyrke.getInt("AntallRep") + " Antall sett: "+rsStyrke.getInt("antallSet")  );
	    		}
	    		ResultSet rsKondisjon = stmt.executeQuery(query3);
	    		
	    		System.out.println("	Kondisjonsøvelser: ");
	    		while(rsKondisjon.next()){
	    			System.out.println("	"+rsKondisjon.getString("Øvelse.Øvelsesnavn") + " Lendge: " + rsKondisjon.getInt("Lengde"));
	    		}
	    		
	    	}
	    	
	    }catch (Exception e) {
	    	e.printStackTrace();
				}
    }	
   

    /**
     * Uthenting av statistikk fra den siste måneden (antall økter, antall timer og annet)
     */
    private void viewStatistics() {

    }

    public static void main(String[] args) {
        // Spør brukeren om å velge et tall og kall deretter chooseActivity(input fra bruker)
        // while scanner.next.....
        MainApp main = new MainApp();
        main.chooseActivity();

    }
}
