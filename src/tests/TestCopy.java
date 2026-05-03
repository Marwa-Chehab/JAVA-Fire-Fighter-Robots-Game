import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.classes.Direction;
import io.classes.Typerobot;
import io.evenements.DeplacementRobot;
import io.evenements.RemplirReservoir;
import io.evenements.VerseEau;
import io.simulation.DonneesSimulation;
import io.simulation.Simulateur;

public class TestCopy {
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestCopy <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation d = new DonneesSimulation(args[0]);
            DonneesSimulation dInit = new DonneesSimulation(d);
            Simulateur affichage = new Simulateur(d);
            

            affichage.dessiner(d);

            
            //LinkedList<Robot> roues = d.getRobots().get(Typerobot.ROUES);
            for( int i=0; i<7; i++){
                d.ajouterEvenement(new DeplacementRobot((long) i, d.getRobot(Typerobot.DRONE, 0), Direction.SUD, d.getCarte()));
            }
            for(int i=7; i<10; i++){
                d.ajouterEvenement(new DeplacementRobot((long) i, d.getRobot(Typerobot.DRONE, 0), Direction.OUEST, d.getCarte()));
            }
            d.ajouterEvenement(new VerseEau((long) 13, d.getRobot(Typerobot.DRONE, 0),d.getIncendies().get(4), d ,10000,30));
            
            d.ajouterEvenement(new DeplacementRobot((long) 1, d.getRobot(Typerobot.DRONE, 0), Direction.SUD, d.getCarte()));

            d.ajouterEvenement(new DeplacementRobot((long) 2, d.getRobot(Typerobot.DRONE, 0), Direction.SUD, d.getCarte()));

            for(int i=0; i<21; i= i+2){
                d.ajouterEvenement(new DeplacementRobot((long) i, d.getRobot(Typerobot.PATTES, 2), Direction.SUD, d.getCarte()));
                d.ajouterEvenement(new DeplacementRobot((long) i+1, d.getRobot(Typerobot.PATTES, 2), Direction.EST, d.getCarte()));
            }
            d.ajouterEvenement(new RemplirReservoir((long) 21, d.getRobot(Typerobot.PATTES, 2)));











        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

        
    }
}


