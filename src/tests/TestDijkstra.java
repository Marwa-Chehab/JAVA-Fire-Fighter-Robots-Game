import io.simulation.*;
import io.classes.Robot;
import io.classes.Typerobot;

import java.util.List;
import java.util.PriorityQueue;

import io.classes.Case;
import io.evenements.Dijkstra;
import io.evenements.Evenement;
import io.evenements.VerseEau;

public class TestDijkstra {
    public static void main(String[] args) {
        try {
            // Initialisation des données de simulation avec la carte spécifiée
            DonneesSimulation donnees = new DonneesSimulation(args[0]);
            Simulateur affichage = new Simulateur(donnees);
            //Dijkstra dijkstra = new Dijkstra();

            //donnees.setSimulateur(affichage);

            //affichage.dessiner(donnees);
            
            // Choisissez un robot et une destination à tester
            //Robot robot = donnees.getRobots().get(Typerobot.DRONE).getFirst();
            //Case destination = donnees.getCarte().getCase(9, 4); // Exemples de coordonnées
            //long finDijkstra = dijkstra.execute(robot, destination, donnees, affichage.getDateCourante());
            //affichage.getGestion().ajouterBasicEvenement(new VerseEau(dijkstra.getDateFinale(), robot, donnees.getIncendie(4), robot.getReservoir(),donnees));


            // Initialisation de l'algorithme de Dijkstra
            /* Dijkstra dijkstra = new Dijkstra(donnees);
            List<Case> chemin = dijkstra.trouverChemin(robot, destination);

            // Affiche le chemin calculé
            System.out.println("Chemin calculé pour atteindre la destination :");
            for (Case c : chemin) {
                System.out.println("Case : (" + c.getLigne() + ", " + c.getColonne() + ")");
            }
            
            double coutTotal = 0;
            for (int i = 0; i < chemin.size() - 1; i++) {
                coutTotal += robot.tempsdeplacement(chemin.get(i + 1));
            }
            System.out.println("Coût total du chemin : " + coutTotal);
            
            // Génère et affiche les événements de déplacement
            PriorityQueue<Evenement> evenements = donnees.getEvenements();
            dijkstra.genererEvenements(chemin, robot, 0L, donnees);
 */


            /* System.out.println("Événements générés :");
            for (Evenement e : evenements) {
                System.out.println(e);
            } */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
