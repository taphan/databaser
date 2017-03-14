import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainApp extends DBController{

	public static long DAY_IN_MS = 1000 * 60 * 60 * 24;
    public MainApp() {
        connect();
    }

    /*
    * Kaller de tilsvarende metodene ut fra input til bruker
     */
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
     * Add data to database using user input step by step
     */
    private void addToDatabase() {
        // Legg inn periode -> trening -> øvelse og lagrer treningsnr og øvelsesnavn, slipper mye input for datapunkt og resultat!
        Scanner scanner = new Scanner(System.in);
        System.out.println("Velg først en periode (må være en uke) i form (PeriodeID, Fra (YYYY-MM-DD), Til (YYYY-MM-DD)");
        Periode periode = new Periode(new ArrayList<>(Arrays.asList(1,"2017-01-01", "2017-01-07")));
        periode.save(con);
        System.out.println("Legg inn grunnleggende info til treningen i form (Treningsnr, navn, dato, varighet, form, prestasjon, treningsformål, tips");
        //List trening = new ArrayList(Arrays.asList(scanner.next().split(",")));
        // Lagrer dette og send inn i uten/innendørsaktivitet konstruktør senere

        System.out.println("Velg mellom utendørsaktivitet (1) eller innendørsaktivitet (2)");
        Integer choice = scanner.nextInt();
        if(choice == 1){
            Utendørsaktivitet akt = new Utendørsaktivitet(new ArrayList<>(Arrays.asList(1,"navn","2017-01-06",4,6,6,"formål","tips",1,2.5,"væretype")));
            akt.save(con);
        } else if(choice==2) {
            Innendørsaktivitet akt = new Innendørsaktivitet(new ArrayList<>(Arrays.asList(1,"navn","2017-01-06",4,6,6,"formål","tips",1,"ventilasjon",5)));
            akt.save(con);
        }

        // Kan legge inn flere resultater fra de ulike øvelsene: while løkke her!
        //while(! scanner.next().equals("slutt")){
        System.out.println("Legg grunnleggende info om en trening i form (Øvelsesnavn, beskrivelse");
        System.out.println("Velg mellom kategoriene for å legge inn en øvelse, tast inn tall 1 eller 2:");
        System.out.println("1: Kondisjon og styrke");
        System.out.println("2: Utholdenhet");
        choice = scanner.nextInt();
        Ovelse ovelse = new Ovelse(new ArrayList<>(Arrays.asList("øvelsesnavn1","beskrivelse","lignende",choice)));
        if(choice == 1) {
            System.out.println("Legge til en styrkeøvelse i form (Belastning, antall repetisjon, antall sett");
            Styrke styrke = new Styrke(new ArrayList<>(Arrays.asList(1, "øvelsesnavn1", 50,8,4)));
            styrke.save(con);
        } else if(choice == 2) {
            System.out.println("Legge til en utholdenhetsøvelse i form (Øvelsesnavn, øvelsestype, lengde");
            Utholdenhet utholdenhet = new Utholdenhet(new ArrayList<>(Arrays.asList(1, "øvelsesnavn2",10)));
            utholdenhet.save(con);
        }

        System.out.println("Legg inn datapunkt for enkelte øvelse i form (PunktNr, tid, puls, lengdegrad, breddegrad, høyde over havet)");
        Datapunkt punkt = new Datapunkt(new ArrayList<>(Arrays.asList(1,"øvelsesnavn1", 1,20,100,"lengde","bredde",150)));
        punkt.save(con);
        scanner.close();
    }


    /**
     * Uthenting av rapport av den beste treningen den siste uka for hver type av trening. Hva som er kriteriet for «best» må dere bestemme selv.
     */
    public void viewRapport() {
/*      // Konflikt ved git pull her
        Date longDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        @SuppressWarnings("deprecation")
        Date shortDate = new Date(longDate.getYear(), longDate.getMonth(), longDate.getDay());

        try {
            Statement stmt = con.createStatement();
            // ON Trening.Øvelsesnavn =t Øvelse.Øvelsesnavn gir ikke mening, en trening kan ha flere øvelser, og Trening.Øvelsesnavn finnes derfor ikke!
            String query = ("SELECT Trening.Navn, Dato, Øvelse.Øvelsesnavn, " +
                    "Form, Prestasjon, Resultat FROM Trening " +
                    "INNER JOIN Øvelse " +
                    "ON Trening.Øvelsesnavn = Øvelse.Øvelsesnavn " +
                    "WHERE Dato >='" + shortDate + "' " +
                    "GROUP BY(Treningsnr) " +
                    "ORDER BY Sum(Form + Prestasjon) DESC; ");

            ResultSet rs = stmt.executeQuery(query);
            int nr = 1;
            while (rs.next()) {
                System.out.println("Doing");
                System.out.println("Nr" + nr++ + ": " + rs.getString("Navn") + " " + rs.getDate("Dato") + " " +
                        rs.getString("Øvelse.Øvelsesnavn") + " Form:" + rs.getInt("Form") + " Prestasjon: " +
                        rs.getInt("Prestasjon") + " Resultat: " + rs.getString("Resultat"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
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
    public void viewStatistics() {
        try {
            Statement statement = con.createStatement();
            String query = "SELECT Trening.Dato,Trening.Navn,Trening.Varighet, " +
                    "count(Resultat.Øvelsesnavn) as Antall_øvelser " +
                    "FROM Resultat JOIN Trening on Resultat.Treningsnr = Trening.Treningsnr " +
                    "JOIN Øvelse on Resultat.Øvelsesnavn = Øvelse.Øvelsesnavn " +
                    "WHERE Trening.Dato > (NOW() - INTERVAL 1 MONTH) " +
                    "GROUP BY Resultat.Treningsnr " +
                    "ORDER BY Trening.Dato;";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Dette er statistikk av treningene den siste måneden:");
            while (rs.next()) {   // Hvis det er flere rader i tabellen
                // Format the print message.
                String dato = rs.getString("Dato");  // getString(column) gets the result in column Dato
                String navn = rs.getString("Navn");
                String varighet = rs.getString("Varighet");
                String antall_ovelser = rs.getString("Antall Øvelser");
                System.out.println("[ Dato: " + dato + ", treningsnavn: " + navn + ", varighet: "
                        + varighet + ", antall øvelser: " + antall_ovelser + " ]");
            }
        } catch (Exception e) {
            System.out.println("DB Error when trying to show last month's statistics: " + e);
        }
    }


    public static void main(String[] args) {
        // Spør brukeren om å velge et tall og kall deretter chooseActivity(input fra bruker)
        // while scanner.next.....
        MainApp main = new MainApp();
        main.chooseActivity();

    }
}
