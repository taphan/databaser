import java.util.Scanner;

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
        main.chooseActivity();
    }

    private void chooseActivity() {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            System.out.println("Velg mellom alternativene ved å taste det tilhørende tallet og trykk Enter:");
            System.out.println("1: Legg trening til database");
            System.out.println("2: Viser statistikk de beste treningsøktene den siste måneden.");
            System.out.println("3: Viser statistikk fra den siste måneden (antall økter, antall timer og annet)");
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
}
