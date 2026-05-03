import io.simulation.DonneesSimulation;
import io.simulation.Simulateur;
/**
 * Lance la simulation:
 */
public class Start {
    public static void main(String[] args) {
        try {
            /**
             * Initialise les données de la simulation avec la carte spécifiée en paramètres
             * Lance la simulation
             */
            DonneesSimulation donnees = new DonneesSimulation(args[0]);
            Simulateur affichage = new Simulateur(donnees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
