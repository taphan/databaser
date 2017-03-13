import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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
        // Legg inn periode -> trening -> øvelses og lagrer treningsnr og øvelsesnavn, slipper mye input for datapunkt og resultat!
        Scanner scanner = new Scanner(System.in);
        System.out.println("Velg først en periode (må være en uke) i form (PeriodeID, Fra (YYYY-MM-DD), Til (YYYY-MM-DD)");


        System.out.println("Velg mellom kategoriene for å legge inn en øvelse, tast inn tall 1 eller 2:");
        System.out.println("1: Kondisjon og styrke");
        System.out.println("2: Utholdenhet");
        Integer choice = scanner.nextInt();
        if(choice == 1) {
            System.out.println("Legge til en styrkeøvelse i form (Treningsnr, øvelsesnavn, øvelsestype , belastning, antall repetisjon, antall sett");
            Styrke styrke = new Styrke(1, "øvelsesnavn1", 50,8,4);
            styrke.save(con);
        } else if(choice == 2) {
            System.out.println("Legge til en styrkeøvelse i form (Treningsnr, øvelsesnavn, øvelsestype, lengde");
            Utholdenhet utholdenhet = new Utholdenhet(1, "øvelsesnavn2",10);
            utholdenhet.save(con);
        }
        scanner.close();
    }


    /**
     * Uthenting av rapport av den beste treningen den siste uka for hver type av trening. Hva som er kriteriet for «best» må dere bestemme selv.
     */
    public void viewRapport() {
        Date longDate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        @SuppressWarnings("deprecation")
        Date shortDate = new Date(longDate.getYear(), longDate.getMonth(), longDate.getDay());

        try {
            Statement stmt = con.createStatement();
            // ON Trening.Øvelsesnavn = Øvelse.Øvelsesnavn gir ikke mening, en trening kan ha flere øvelser, og Trening.Øvelsesnavn finnes derfor ikke!
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
