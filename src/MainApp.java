import java.sql.Array;
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
        ArrayList periodeInput = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
        Periode periode = new Periode(periodeInput);
        periode.save(con);

        System.out.println("Velg mellom utendørsaktivitet (1) eller innendørsaktivitet (2)");
        Integer choice = scanner.nextInt();
        ArrayList treningList;
        if(choice == 1){
            System.out.println("Legg inn info om utendørsaktivitet i form (Treningsnr, navn, dato, varighet, form, " +
                    "prestasjon, treningsformål, tips, temperatur, værtype)");
            String nextLine = scanner.nextLine();
            treningList = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
            treningList.add(0,periodeInput.get(0)); // Legg inn periodeID inn som parameter
            Utendørsaktivitet trening = new Utendørsaktivitet(treningList);
            trening.save(con);

        } else {
            System.out.println("Legg inn info om innendørsaktivitet i form (Treningsnr, navn, dato, varighet, form, " +
                    "prestasjon, treningsformål, tips, ventilasjon, antall tilskuere)");
            String nextLine = scanner.nextLine();
            treningList = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
            treningList.add(0, periodeInput.get(0));
            Innendørsaktivitet trening = new Innendørsaktivitet(treningList);
            trening.save(con);
        }

        System.out.println("Skriv 'start' for å begynne å legge inn øvelser, og 'slutt' for å slutte");
        while(! scanner.next().equals("slutt")) {
            String nextLine = scanner.nextLine();
            System.out.println("Legg grunnleggende info om en trening i form (Øvelsesnavn, beskrivelse, lignende øvelse)");
            ArrayList ovelseList = new ArrayList<>(Arrays.asList(scanner.nextLine().split(",")));
            System.out.println("Velg mellom kategoriene for å legge inn en øvelse: Styrke (1) eller utholdenhet (2)");
            choice = scanner.nextInt();
            ovelseList.add(choice);
            Ovelse ovelse = new Ovelse(ovelseList);
            ovelse.save(con);
            ArrayList resultatList = new ArrayList<>(Arrays.asList(treningList.get(0), ovelseList.get(0))); // treningsNr og øvelsesNavn
            nextLine = scanner.nextLine();
            if (choice == 1) {
                System.out.println("Legge til resultat av styrkeøvelsen i form (Belastning, antall repetisjon, antall sett)");
                resultatList.addAll(Arrays.asList(scanner.nextLine().split(",")));
                Styrke styrke = new Styrke(resultatList);
                styrke.save(con);
            } else if (choice == 2) {
                System.out.println("Legge til resultat av utholdenhetsøvelsen i form (Lengde)");
                resultatList.addAll(Arrays.asList(scanner.nextLine().split(",")));
                Utholdenhet utholdenhet = new Utholdenhet(resultatList);
                utholdenhet.save(con);
            }

            System.out.println("Legg inn datapunkt for enkelte øvelse i form (PunktNr, tid, puls, lengdegrad, breddegrad, høyde over havet)");
            ArrayList punktList = new ArrayList<>(Arrays.asList(treningList.get(0), ovelseList.get(0)));
            punktList.addAll(Arrays.asList(scanner.nextLine().split(",")));
            Datapunkt punkt = new Datapunkt(punktList);
            punkt.save(con);
            System.out.println("Skriv 'start' for å legge inn ny øvelse, og 'slutt' for å slutte");
        }
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
	
	    	Statement stmt = con.createStatement();
	    	String query1 = "select Trening.Treningsnr, Tips, Dato, Navn, Varighet, Form, " +  
	    					"Prestasjon, Øvelse.Øvelsesnavn, Øvelse.Øvelsestype, "+
	    					"Lengde, Belastning, AntallSet, AntallRep from Trening "+
							"inner join Resultat " +
							"on Trening.Treningsnr = Resultat.Treningsnr " +
							"inner join Øvelse " +
							"on Resultat.Øvelsesnavn = Øvelse.Øvelsesnavn " +
							"left outer join Utholdenhet " +
							"on Utholdenhet.Øvelsesnavn = Øvelse.Øvelsesnavn " +
							"and Trening.Treningsnr = Utholdenhet.Treningsnr " +
							"left join Styrke " + 
							"on Styrke.Øvelsesnavn = Øvelse.Øvelsesnavn " +
							"and Styrke.Treningsnr = Trening.Treningsnr " +
							"where Dato >= '"+ shortDate +"' " +
							"order by (Trening.Form + Trening.Prestasjon) desc, Trening.Treningsnr";

	    	
	    	ResultSet rs = stmt.executeQuery(query1);
	    	int treningsnr;
	    	int lastTreningsnr = -1;
	    	int nr = 1;
	    	while(rs.next()){
	    		treningsnr = rs.getInt("Treningsnr");
	    		if(lastTreningsnr != treningsnr){
	    		System.out.println();
	    		System.out.println("Nr"+ nr++ +": " +rs.getDate("Dato") +" "+ 
	    				rs.getString("Navn") +", Varighet: "+ rs.getInt("Varighet")+
	    				", Form:"+ rs.getInt("Form")+", Prestasjon: "+ rs.getInt("Prestasjon") + 
	    				", Tips: " + rs.getString("Tips")+".");
	    		System.out.println("----------------------------------------------------------------------");
	    		}
	    		
	    		if(rs.getInt("Øvelse.Øvelsestype") == 1){
	    			System.out.println("   | "+rs.getString("Øvelse.Øvelsesnavn") +", Belastning: "+rs.getInt("Belastning") +
	    					", Antall repitisjoner: "+rs.getInt("AntallRep") + ", Antall sett: "+rs.getInt("antallSet")+ "."  );
	    		}
	   
	    		
	    		if(rs.getInt("Øvelse.Øvelsestype") == 2){
	    			System.out.println("   | "+rs.getString("Øvelse.Øvelsesnavn") + ", Lendge: " + rs.getInt("Lengde") + ".");
	    		}
	    		
	    		lastTreningsnr = treningsnr;
	    		
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
            String query = "SELECT treningsdagbok.Trening.Dato,treningsdagbok.Trening.Navn,treningsdagbok.Trening.Varighet, " +
                    "count(treningsdagbok.Resultat.Øvelsesnavn) AS Antall_øvelser  " +
                    "FROM treningsdagbok.Resultat JOIN treningsdagbok.Trening on treningsdagbok.Resultat.Treningsnr = treningsdagbok.Trening.Treningsnr " +
                    "JOIN treningsdagbok.Øvelse on treningsdagbok.Resultat.Øvelsesnavn = treningsdagbok.Øvelse.Øvelsesnavn " +
                    "WHERE treningsdagbok.Trening.Dato > (NOW() - INTERVAL 1 MONTH) " +
                    "GROUP BY treningsdagbok.Resultat.Treningsnr " +
                    "ORDER BY treningsdagbok.Trening.Dato;";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Dette er statistikk av treningene den siste måneden:");
            while (rs.next()) {   // Hvis det er flere rader i tabellen
                // Format the print message.
                String dato = rs.getString("Dato");  // getString(column) gets the result in column Dato
                String navn = rs.getString("Navn");
                String varighet = rs.getString("Varighet");
                String antall_ovelser = rs.getString("Antall_øvelser");
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