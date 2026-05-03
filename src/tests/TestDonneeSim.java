import java.io.FileNotFoundException;

import java.util.zip.DataFormatException;

import io.simulation.DonneesSimulation;


 


public class TestDonneeSim {
    public static void main(String[] args) {
        // Path to the data file (make sure this path is correct)
        //String fichierDonnees = "../cartes/carteSujet.map";
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation d = new DonneesSimulation(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }


        /*  try {
            // Create and load simulation data
            DonneesSimulation sim = DonneesSimulation.creerDonnees(args[0]);

            // Test if the data was read correctly
            System.out.println("== Carte Info ==");
            Carte carte = sim.getCarte();
            
            System.out.println("Nombre de lignes: " + carte.getNblignes());
            System.out.println("Nombre de colonnes: " + carte.getNbcolonnes());
            System.out.println("Taille des cases: " + carte.getTailleCases());

            // Display incendies
            LinkedList<Incendie> incendies = sim.getIncendies();
            System.out.println("\n== Incendies Info ==");
            for (Incendie incendie : incendies) {
                System.out.println("Incendie à (" + incendie.getPosition().getLigne() + ", " +
                                   incendie.getPosition().getColonne() + "), Intensité: " + incendie.getIntensite());
            }
            for(Incendie incendie : incendies){
                incendie.getLigne();
            }
            
            // Display robots
            HashSet<Robot> robots = sim.getRobots();
            System.out.println("\n== Robots Info ==");
            for (Robot robot : robots) {
                System.out.println("Robot " + robot.getClass().getSimpleName() + 
                                   " à (" + robot.getLigne() + ", " + robot.getColonne() + "), Vitesse: " + robot.getVitesse_   ());
            }

            for (Robot robot : robots) {
    if (robot instanceof Drone) {
        System.out.println("Drone found at (" + robot.getLigne() + ", " + robot.getColonne() + ")");
        robot.remplirReservoir();
    }
}


        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé: " + args[0]);
        } catch (DataFormatException e) {
            System.err.println("Erreur de format dans le fichier de données.");
        }*/
    }  
}
