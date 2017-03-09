import java.util.Scanner;

public class MainApp extends DBController{

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
    private void viewRapport() {

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
